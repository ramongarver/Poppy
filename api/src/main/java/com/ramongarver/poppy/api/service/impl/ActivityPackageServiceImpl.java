package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.activity.ActivityVolunteerAvailabilitiesAndAssignmentsReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReducedReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReducedReadDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.repository.ActivityPackageRepository;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityPackageServiceImpl implements ActivityPackageService {

    private final ActivityPackageMapper activityPackageMapper;
    private final VolunteerMapper volunteerMapper;

    private final ActivityPackageRepository activityPackageRepository;

    private final ActivityService activityService;

    @Override
    public ActivityPackage createActivityPackage(ActivityPackageCreateDto activityPackageCreateDto) {
        final ActivityPackage activityPackage = activityPackageMapper.fromCreateDto(activityPackageCreateDto);
        return activityPackageRepository.save(activityPackage);
    }

    @Override
    public ActivityPackage getActivityPackageById(Long activityPackageId) {
        return activityPackageRepository.findById(activityPackageId)
                .orElseThrow(() -> new ResourceNotFoundException(ActivityPackage.class.getSimpleName(), "id", activityPackageId));
    }

    @Override
    public List<ActivityPackage> getActivityPackagesByIds(List<Long> activities) {
        return activityPackageRepository.findByIdIn(activities);
    }

    @Override
    public List<ActivityPackage> getAllActivityPackages() {
        return activityPackageRepository.findAll();
    }

    @Override
    public ActivityPackage updateActivityPackage(Long activityPackageId, ActivityPackageUpdateDto activityPackageUpdateDto) {
        final ActivityPackage existingActivityPackage = getActivityPackageById(activityPackageId);
        activityPackageMapper.fromUpdateDto(existingActivityPackage, activityPackageUpdateDto);
        return activityPackageRepository.save(existingActivityPackage);
    }

    @Override
    public void deleteActivityPackage(Long activityPackageId) {
        verifyActivityPackageExists(activityPackageId);
        activityPackageRepository.deleteById(activityPackageId);
    }

    @Override
    public void verifyActivityPackageExists(Long activityPackageId) {
        if (!activityPackageRepository.existsById(activityPackageId)) {
            throw new ResourceNotFoundException(ActivityPackage.class.getSimpleName(), "id", activityPackageId);
        }
    }

    @Transactional
    public ActivityPackage assignActivitiesToActivityPackage(Long activityPackageId, List<Long> activityIds) {
        final ActivityPackage activityPackage = getActivityPackageById(activityPackageId);
        final List<Activity> activitiesToAssign = activityService.getActivitiesByIds(activityIds);

        activityPackage.getActivities().addAll(activitiesToAssign);

        return activityPackageRepository.save(activityPackage);
    }

    @Transactional
    public ActivityPackage assignActivityToActivityPackage(Long activityPackageId, Long activityId) {
        return assignActivitiesToActivityPackage(activityPackageId, List.of(activityId));
    }

    @Transactional
    public ActivityPackage unassignActivitiesFromActivityPackage(Long activityPackageId, List<Long> activityIds) {
        final ActivityPackage activityPackage = getActivityPackageById(activityPackageId);
        final List<Activity> activitiesToUnassign = activityService.getActivitiesByIds(activityIds);

        activityPackage.getActivities().removeAll(activitiesToUnassign);

        return activityPackageRepository.save(activityPackage);
    }

    public ActivityPackage unassignActivityToActivityPackage(Long activityPackageId, Long activityId) {
        return unassignActivitiesFromActivityPackage(activityPackageId, List.of(activityId));
    }

    @Override
    public ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto getActivityPackageVolunteerAssignments(Long activityPackageId) {
        final ActivityPackage activityPackage = getActivityPackageById(activityPackageId);
        final ActivityPackageReducedReadDto activityPackageReducedReadDto = activityPackageMapper.toReducedReadDto(activityPackage);

        final List<Activity> activities = activityPackage.getActivities();
        activities.sort(Comparator.comparing(Activity::getLocalDateTime));

        final List<ActivityVolunteerAvailabilitiesAndAssignmentsReadDto> activitiesDto = new ArrayList<>();

        for (Activity activity : activities) {
            final List<VolunteerReducedReadDto> availabilities = new ArrayList<>();
            final List<VolunteerReducedReadDto> assignments = new ArrayList<>();

            for (Volunteer volunteer : activity.getVolunteers()) {
                final VolunteerReducedReadDto volunteerDto = volunteerMapper.toReducedReadDto(volunteer);
                assignments.add(volunteerDto);
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
