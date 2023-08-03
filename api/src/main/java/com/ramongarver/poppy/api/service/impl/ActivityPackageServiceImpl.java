package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.repository.ActivityPackageRepository;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActivityPackageServiceImpl implements ActivityPackageService {

    private final ActivityPackageMapper activityPackageMapper;

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
        doesActivityPackageExist(activityPackageId);
        activityPackageRepository.deleteById(activityPackageId);
    }

    private void doesActivityPackageExist(Long activityPackageId) {
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

    public Map<Activity, List<Volunteer>> getActivityPackageAssignments(Long activityPackageId) {
        final ActivityPackage activityPackage = getActivityPackageById(activityPackageId);
        final List<Activity> activities = activityPackage.getActivities();

        final Map<Activity, List<Volunteer>> activityPackageAssignments = new HashMap<>();

        for (Activity activity : activities) {
            activityPackageAssignments.put(activity, activity.getVolunteers());
        }

        return activityPackageAssignments;
    }

}
