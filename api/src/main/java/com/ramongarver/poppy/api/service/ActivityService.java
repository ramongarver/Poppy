package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;

import java.util.List;

public interface ActivityService {

    Activity createActivity(ActivityCreateDto activityCreateDto);

    Activity getActivityById(Long activityId);

    List<Activity> getActivitiesByIds(List<Long> activitiesIds);

    List<Activity> getAllActivities();

    Activity updateActivity(Long activityId, ActivityUpdateDto activityUpdateDto);

    void deleteActivity(Long activityId);

}
