package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.activity.ActivityVolunteerAvailabilitiesAndAssignmentsReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReducedReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReducedReadDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityCreateDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import com.ramongarver.poppy.api.exception.ActivityNotInActivityPackageException;
import com.ramongarver.poppy.api.exception.OutVolunteerAvailabilitySubmissionPeriodException;
import com.ramongarver.poppy.api.exception.ResourceAlreadyExistsException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.repository.VolunteerAvailabilityRepository;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.ActivityService;
import com.ramongarver.poppy.api.service.VolunteerAvailabilityService;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class VolunteerAvailabilityServiceImpl implements VolunteerAvailabilityService {

    private final ActivityPackageMapper activityPackageMapper;
    private final VolunteerMapper volunteerMapper;

    private final VolunteerAvailabilityRepository volunteerAvailabilityRepository;

    private final ActivityService activityService;
    private final ActivityPackageService activityPackageService;
    private final VolunteerService volunteerService;

    @Override
    public VolunteerAvailability createActivityPackageVolunteerAvailability(Long activityPackageId, Long volunteerId, VolunteerAvailabilityCreateDto volunteerAvailabilityCreateDto) {
        verifyVolunteerAvailabilityNotExists(activityPackageId, volunteerId);
        final Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        final List<Long> volunteerAvailabilityActivityIds = volunteerAvailabilityCreateDto.getActivityIds();
        final List<Activity> activities = getActivitiesForAvailabilitySubmissionInPeriod(activityPackage, volunteerAvailabilityActivityIds);

        final VolunteerAvailability volunteerAvailability = VolunteerAvailability.builder()
                .volunteer(volunteer)
                .activityPackage(activityPackage)
                .activities(activities)
                .build();

        return volunteerAvailabilityRepository.save(volunteerAvailability);
    }

    @Override
    public VolunteerAvailability getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(Long activityPackageId, Long volunteerId) {
        activityPackageService.verifyActivityPackageExists(activityPackageId);
        volunteerService.verifyVolunteerExists(volunteerId);
        return volunteerAvailabilityRepository.findByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId)
                .orElseThrow(() -> new ResourceNotFoundException(VolunteerAvailability.class.getSimpleName(), "activityPackageId-volunteerId", activityPackageId + "-" + volunteerId));
    }

    @Override
    public List<VolunteerAvailability> getAllVolunteerAvailabilitiesByActivityPackageId(Long activityPackageId) {
        activityPackageService.verifyActivityPackageExists(activityPackageId);
        return volunteerAvailabilityRepository.findAllByActivityPackageId(activityPackageId);
    }

    @Override
    public VolunteerAvailability updateVolunteerAvailability(Long activityPackageId, Long volunteerId, VolunteerAvailabilityUpdateDto volunteerAvailabilityUpdateDto) {
        final VolunteerAvailability existingVolunteerAvailability = getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId);

        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        final List<Long> volunteerAvailabilityActivityIds = volunteerAvailabilityUpdateDto.getActivityIds();
        final List<Activity> activities = getActivitiesForAvailabilitySubmissionInPeriod(activityPackage, volunteerAvailabilityActivityIds);

        existingVolunteerAvailability.setActivities(activities != null ? activities : List.of());

        return volunteerAvailabilityRepository.save(existingVolunteerAvailability);
    }

    @Override
    public void deleteVolunteerAvailability(Long activityPackageId, Long volunteerId) {
        final VolunteerAvailability volunteerAvailability = getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId);
        volunteerAvailabilityRepository.delete(volunteerAvailability);
    }

    private void verifyVolunteerAvailabilityNotExists(Long activityPackageId, Long volunteerId) {
        if (volunteerAvailabilityRepository.findByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId).isPresent()) {
            throw new ResourceAlreadyExistsException(VolunteerAvailability.class.getSimpleName(), "activityPackageId-volunteerId", activityPackageId + "-" + volunteerId);
        }
    }

    private void verifyIsInVolunteerAvailabilitySubmissionPeriod(Long activityPackageId, LocalDate startDate, LocalDate endDate) {
        final LocalDate now = LocalDate.now();
        if (now.isBefore(startDate) || now.isAfter(endDate)) {
            throw new OutVolunteerAvailabilitySubmissionPeriodException(activityPackageId, startDate, endDate);
        }
    }

    private void verifyActivityIdsInActivityPackage(List<Long> activityPackageActivityIds, List<Long> volunteerAvailabilityActivityIds, Long activityPackageId) {
        final List<Long> missingActivityIds = volunteerAvailabilityActivityIds.stream()
                .filter(activityId -> !activityPackageActivityIds.contains(activityId))
                .toList();

        if (!missingActivityIds.isEmpty()) {
            throw new ActivityNotInActivityPackageException(activityPackageId, missingActivityIds);
        }
    }

    private List<Activity> getActivitiesForAvailabilitySubmissionInPeriod(ActivityPackage activityPackage, List<Long> volunteerAvailabilityActivityIds) {
        final Long activityPackageId = activityPackage.getId();
        verifyIsInVolunteerAvailabilitySubmissionPeriod(activityPackageId, activityPackage.getAvailabilityStartDate(), activityPackage.getAvailabilityEndDate());

        final List<Long> activityPackageActivityIds = activityPackage.getActivities().stream().map(Activity::getId).toList();
        verifyActivityIdsInActivityPackage(activityPackageActivityIds, volunteerAvailabilityActivityIds, activityPackageId);

        return activityService.getActivitiesByIds(volunteerAvailabilityActivityIds);
    }

    @Override
    public ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto getActivityPackageVolunteerAvailabilities(Long activityPackageId) {
        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        final ActivityPackageReducedReadDto activityPackageReducedReadDto = activityPackageMapper.toReducedReadDto(activityPackage);

        final List<VolunteerAvailability> volunteerAvailabilities = getAllVolunteerAvailabilitiesByActivityPackageId(activityPackageId);

        final List<Activity> activities = activityPackage.getActivities();
        activities.sort(Comparator.comparing(Activity::getLocalDateTime));

        final List<ActivityVolunteerAvailabilitiesAndAssignmentsReadDto> activitiesDto = new ArrayList<>();

        for (Activity activity : activities) {
            final List<VolunteerReducedReadDto> availabilities = new ArrayList<>();
            final List<VolunteerReducedReadDto> assignments = new ArrayList<>();

            for (VolunteerAvailability availability : volunteerAvailabilities) {
                for (Activity availableActivity : availability.getActivities()) {
                    if (availableActivity.equals(activity)) {
                        final VolunteerReducedReadDto volunteerDto = volunteerMapper.toReducedReadDto(availability.getVolunteer());
                        availabilities.add(volunteerDto);
                    }
                }
            }

            final ActivityVolunteerAvailabilitiesAndAssignmentsReadDto activityDto = ActivityVolunteerAvailabilitiesAndAssignmentsReadDto.builder()
                    .id(activity.getId())
                    .name(activity.getName())
                    .description(activity.getDescription())
                    .localDateTime(activity.getLocalDateTime())
                    .place(activity.getPlace())
                    .numberOfCoordinators(activity.getNumberOfCoordinators())
                    .availabilities(availabilities)
                    .assignments(assignments)
                    .build();

            activitiesDto.add(activityDto);
        }

        return ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto.builder()
                .activityPackage(activityPackageReducedReadDto)
                .activities(activitiesDto)
                .build();
    }

}
