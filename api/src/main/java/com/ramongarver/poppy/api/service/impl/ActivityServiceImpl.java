package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.repository.ActivityRepository;
import com.ramongarver.poppy.api.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityMapper activityMapper;

    private final ActivityRepository activityRepository;

    @Override
    public Activity createActivity(ActivityCreateDto activityCreateDto) {
        final Activity activity = activityMapper.fromCreateDto(activityCreateDto);
        return activityRepository.save(activity);
    }

    @Override
    public Activity getActivityById(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException(Activity.class.getSimpleName(), "id", activityId));
    }

    @Override
    public List<Activity> getActivitiesByIds(List<Long> activities) {
        return activityRepository.findByIdIn(activities);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity updateActivity(Long activityId, ActivityUpdateDto activityUpdateDto) {
        final Activity existingActivity = getActivityById(activityId);
        activityMapper.fromUpdateDto(existingActivity, activityUpdateDto);
        return activityRepository.save(existingActivity);
    }

    @Override
    public void deleteActivity(Long activityId) {
        doesActivityExist(activityId);
        activityRepository.deleteById(activityId);
    }

    public void doesActivityExist(Long activityId) {
        if (!activityRepository.existsById(activityId)) {
            throw new ResourceNotFoundException(Activity.class.getSimpleName(), "id", activityId);
        }
    }

}
