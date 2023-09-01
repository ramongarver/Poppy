package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.entity.ActivityPackage;

import java.util.List;

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

    ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto getActivityPackageVolunteerAssignments(Long activityPackageId);

}
