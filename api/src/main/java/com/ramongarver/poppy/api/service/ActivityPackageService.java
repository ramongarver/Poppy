package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.Volunteer;

import java.util.List;
import java.util.Map;

public interface ActivityPackageService {

    ActivityPackage createActivityPackage(ActivityPackageCreateDto activityPackageCreateDto);

    ActivityPackage getActivityPackageById(Long activityPackageId);

    List<ActivityPackage> getActivityPackagesByIds(List<Long> activityPackagesIds);

    List<ActivityPackage> getAllActivityPackages();

    ActivityPackage updateActivityPackage(Long activityPackageId, ActivityPackageUpdateDto activityPackageUpdateDto);

    void deleteActivityPackage(Long activityPackageId);

    void verifyActivityPackageExists(Long activityPackageId);

    ActivityPackage assignActivitiesToActivityPackage(Long activityPackageId, List<Long> activityIds);

    ActivityPackage assignActivityToActivityPackage(Long activityPackageId, Long activityId);

    ActivityPackage unassignActivitiesFromActivityPackage(Long activityPackageId, List<Long> activityIds);

    ActivityPackage unassignActivityToActivityPackage(Long activityPackageId, Long activityId);

    Map<Activity, List<Volunteer>> getActivityPackageAssignments(Long activityPackageId);

}
