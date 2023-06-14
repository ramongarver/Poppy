package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.ACTIVITY_ROUTE)
public class ActivityController {

    private final ActivityMapper activityMapper;

    private final ActivityService activityService;

    @GetMapping("{id}")
    public ResponseEntity<ActivityReadDto> getActivityById(@PathVariable("id") Long activityId) {
        final Activity activity = activityService.getActivityById(activityId);
        return new ResponseEntity<>(activityMapper.toReadDto(activity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActivityReadDto>> getAllActivities() {
        final List<Activity> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activityMapper.toListReadDto(activities), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActivityReadDto> createActivity(@Valid @RequestBody ActivityCreateDto activityCreateDto) {
        final Activity savedActivity = activityService.createActivity(activityCreateDto);
        return new ResponseEntity<>(activityMapper.toReadDto(savedActivity), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ActivityReadDto> updateActivity(@PathVariable("id") Long activityId,
                                                          @RequestBody ActivityUpdateDto activityUpdateDto) {
        final Activity updatedActivity = activityService.updateActivity(activityId, activityUpdateDto);
        return new ResponseEntity<>(activityMapper.toReadDto(updatedActivity), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable("id") Long activityId) {
        activityService.deleteActivity(activityId);
        return new ResponseEntity<>("Activity successfully deleted!", HttpStatus.NO_CONTENT);
    }

}
