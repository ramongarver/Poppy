package com.ramongarver.poppy.api.controller;


import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.VOLUNTEERS_ROUTE)
public class VolunteerController {

    private final ActivityMapper activityMapper;

    private final VolunteerService volunteerService;

    @GetMapping("{id}" + ControllerConstants.ACTIVITIES_RESOURCE)
    public ResponseEntity<List<ActivityReadDto>> getVolunteerActivities(
            @PathVariable("id") Long volunteerId,
            @RequestParam(value = "showOnlyFutureActivities", required = false) Boolean showOnlyFutureActivities) {
        final Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);
        List<Activity> activities = volunteer.getActivities();

        if (Boolean.TRUE.equals(showOnlyFutureActivities)) {
            activities = activities.stream()
                    .filter(activity -> activity.getLocalDateTime().isAfter(LocalDateTime.now()))
                    .toList();
        }

        return new ResponseEntity<>(activityMapper.toListReadDto(activities), HttpStatus.OK);
    }

}
