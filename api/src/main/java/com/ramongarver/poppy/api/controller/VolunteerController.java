package com.ramongarver.poppy.api.controller;


import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReadDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.service.VolunteerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.VOLUNTEERS_ROUTE)
public class VolunteerController {

    private final ActivityMapper activityMapper;
    private final VolunteerMapper volunteerMapper;

    private final VolunteerService volunteerService;

    @GetMapping("{id}")
    public ResponseEntity<VolunteerReadDto> getVolunteerById(@PathVariable("id") Long volunteerId) {
        final Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);
        return new ResponseEntity<>(volunteerMapper.toReadDto(volunteer), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VolunteerReadDto>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        return new ResponseEntity<>(volunteerMapper.toListReadDto(volunteers), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VolunteerReadDto> createVolunteer(@RequestBody VolunteerCreateDto volunteerCreateDto) {
        final Volunteer savedVolunteer = volunteerService.createVolunteer(volunteerCreateDto);
        return new ResponseEntity<>(volunteerMapper.toReadDto(savedVolunteer), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<VolunteerReadDto> updateVolunteer(@PathVariable("id") Long volunteerId,
                                                   @RequestBody VolunteerUpdateDto volunteerUpdateDto) {
        final Volunteer updatedVolunteer = volunteerService.updateVolunteer(volunteerId, volunteerUpdateDto);
        return new ResponseEntity<>(volunteerMapper.toReadDto(updatedVolunteer), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteVolunteer(@PathVariable("id") Long volunteerId) {
        volunteerService.deleteVolunteer(volunteerId);
        return new ResponseEntity<>("Volunteer successfully deleted!", HttpStatus.NO_CONTENT);
    }
    
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
