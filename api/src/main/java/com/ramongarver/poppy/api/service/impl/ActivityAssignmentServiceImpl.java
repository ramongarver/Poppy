package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import com.ramongarver.poppy.api.exception.VolunteersAlreadyAssignedToActivitiesInActivityPackageException;
import com.ramongarver.poppy.api.service.ActivityAssignmentService;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.ActivityService;
import com.ramongarver.poppy.api.service.VolunteerAvailabilityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityAssignmentServiceImpl implements ActivityAssignmentService {

    private static final int MAX_ACTIVITIES_PER_VOLUNTEER = 2;
    private static final int MIN_COORDINATORS_TO_IGNORE_LIMIT = 3;

    private final ActivityPackageService activityPackageService;

    private final ActivityService activityService;

    private final VolunteerAvailabilityService volunteerAvailabilityService;

    @Transactional
    public void assignVolunteersToActivities(Long activityPackageId) {
        // 1. Retrieve the activity package and availabilities
        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        verifyVolunteersNotAlreadyAssignedToActivitiesInActivityPackage(activityPackage);
        final List<VolunteerAvailability> volunteerAvailabilities = volunteerAvailabilityService.getAllVolunteerAvailabilitiesByActivityPackageId(activityPackageId);

        // 2. Create and order the data structure
        // - This map contains for each activity id the ids of the volunteers who have made themselves available
        // - The activities with the fewest volunteers are before
        final Map<Long, List<Long>> activityVolunteersMap = createActivityVolunteersMap(volunteerAvailabilities);
        // - Map to track how many activities have been assigned to each volunteer
        final Map<Long, Integer> volunteerActivityCount = new HashMap<>();

        // 3. Go through the activities and assign the volunteers
        for (Map.Entry<Long, List<Long>> activityVolunteers : activityVolunteersMap.entrySet()) {
            final Long activityId = activityVolunteers.getKey();
            final List<Long> availableVolunteerIds = activityVolunteers.getValue();


            int numberOfCoordinatorsNeeded = activityService.getActivityById(activityId).getNumberOfCoordinators();
            final List<Long> assignedVolunteerIds = new ArrayList<>();
            for (Long availableVolunteerId : availableVolunteerIds) {
                // Check how many activities this volunteer already has assigned
                final Integer currentCount = volunteerActivityCount.getOrDefault(availableVolunteerId, 0);

                // Check if this volunteer can be assigned to another activity for this activity package
                if (assignedVolunteerIds.size() < numberOfCoordinatorsNeeded
                        && (currentCount < MAX_ACTIVITIES_PER_VOLUNTEER || numberOfCoordinatorsNeeded >= MIN_COORDINATORS_TO_IGNORE_LIMIT)) {
                    assignedVolunteerIds.add(availableVolunteerId);

                    if (numberOfCoordinatorsNeeded < MIN_COORDINATORS_TO_IGNORE_LIMIT) {
                        volunteerActivityCount.put(availableVolunteerId, currentCount + 1);
                    }
                }
            }
            // Assign volunteers to the activity
            activityService.assignVolunteersToActivity(activityId, assignedVolunteerIds);
        }

        // - Make sure every activity has at least one coordinator
        for (Map.Entry<Long, List<Long>> activityVolunteers : activityVolunteersMap.entrySet()) {
            final Long activityId = activityVolunteers.getKey();

            final boolean isCoordinatorMissing = activityService.getActivityById(activityId).getVolunteers().isEmpty();
            if (isCoordinatorMissing) {  // Replace with actual logic to check if a coordinator is missing
                final List<Long> availableVolunteerIds = activityVolunteers.getValue();
                final Long volunteerIdWithLeastActivities = availableVolunteerIds.stream()
                        .min(Comparator.comparingInt(volunteerActivityCount::get))
                        .orElse(null);

                // Update the volunteerActivityCount map
                int currentCount = volunteerActivityCount.get(volunteerIdWithLeastActivities);
                volunteerActivityCount.put(volunteerIdWithLeastActivities, currentCount + 1);

                activityService.assignVolunteerToActivity(activityId, volunteerIdWithLeastActivities);
            }
        }
        activityPackage.setAreVolunteersAssigned(true);

        // 4. Save the updated entities in the database
        // As we are using Spring Data JPA with @Transactional, there is no need to explicitly call a save method
        // All changes to entities will be saved automatically at the end of the transaction
    }

    private Map<Long, List<Long>> createActivityVolunteersMap(List<VolunteerAvailability> volunteerAvailabilities) {
        // Initialize a TreeMap to store Activity ID and corresponding Volunteer IDs
        // TreeMap is used to maintain the order of the activities
        final Map<Long, List<Long>> activityVolunteerMap = new TreeMap<>();

        // Count the number of availabilities for each volunteer
        final Map<Long, Integer> volunteerAvailabilityCount = new HashMap<>();
        for (VolunteerAvailability volunteerAvailability : volunteerAvailabilities) {
            final Long volunteerId = volunteerAvailability.getVolunteer().getId();
            final Integer numberOfActivities = volunteerAvailability.getActivities().size();
            volunteerAvailabilityCount.put(volunteerId, numberOfActivities);
        }

        // Iterate through each volunteerAvailability in the given list
        for (VolunteerAvailability volunteerAvailability : volunteerAvailabilities) {
            for (Activity activity : volunteerAvailability.getActivities()) {
                activityVolunteerMap
                        .computeIfAbsent(activity.getId(), id -> new ArrayList<>())
                        .add(volunteerAvailability.getVolunteer().getId());
            }
        }

        // Sort the volunteer IDs for each activity based on their availability count
        for (Map.Entry<Long, List<Long>> entry : activityVolunteerMap.entrySet()) {
            final List<Long> sortedVolunteerIds = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(volunteerAvailabilityCount::get))
                    .toList();
            entry.setValue(sortedVolunteerIds);
        }

        // Sort the map entries based on the size of the list of volunteers (ascending)
        // Activities with fewer volunteers will be at the beginning of the map
        return activityVolunteerMap.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void verifyVolunteersNotAlreadyAssignedToActivitiesInActivityPackage(ActivityPackage activityPackage) {
        if (activityPackage.isAreVolunteersAssigned()) {
            throw new VolunteersAlreadyAssignedToActivitiesInActivityPackageException(activityPackage.getId());
        }
    }
}
