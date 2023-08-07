package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityCreateDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityReadDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityUpdateDto;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.mapper.VolunteerAvailabilityMapper;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.VolunteerAvailabilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(ControllerConstants.ACTIVITY_PACKAGES_ROUTE)
public class ActivityPackageController {

    private final ActivityPackageMapper activityPackageMapper;
    private final VolunteerAvailabilityMapper volunteerAvailabilityMapper;

    private final ActivityPackageService activityPackageService;
    private final VolunteerAvailabilityService volunteerAvailabilityService;

    @GetMapping("{activityPackageId}")
    public ResponseEntity<ActivityPackageReadDto> getActivityPackageById(@PathVariable("activityPackageId") Long activityPackageId) {
        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(activityPackage), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActivityPackageReadDto>> getAllActivities() {
        final List<ActivityPackage> activities = activityPackageService.getAllActivityPackages();
        return new ResponseEntity<>(activityPackageMapper.toListReadDto(activities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ActivityPackageReadDto> createActivity(@Valid @RequestBody ActivityPackageCreateDto activityPackageCreateDto) {
        final ActivityPackage savedActivityPackage = activityPackageService.createActivityPackage(activityPackageCreateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(savedActivityPackage), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PutMapping("{activityPackageId}")
    public ResponseEntity<ActivityPackageReadDto> updateActivity(@PathVariable("activityPackageId") Long activityId,
                                                                 @RequestBody ActivityPackageUpdateDto activityPackageUpdateDto) {
        final ActivityPackage updatedActivityPackage = activityPackageService.updateActivityPackage(activityId, activityPackageUpdateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(updatedActivityPackage), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{activityPackageId}")
    public ResponseEntity<String> deleteActivity(@PathVariable("activityPackageId") Long activityPackageId) {
        activityPackageService.deleteActivityPackage(activityPackageId);
        return new ResponseEntity<>("Activity successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    public ResponseEntity<VolunteerAvailabilityReadDto> getVolunteerAvailabilityByActivityPackageByIdAndVolunteerId(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                                                    @PathVariable("volunteerId") Long volunteerId) {
        final VolunteerAvailability volunteerAvailability =
                volunteerAvailabilityService.getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(volunteerAvailability), HttpStatus.OK);
    }

    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE)
    public ResponseEntity<List<VolunteerAvailabilityReadDto>> getAllVolunteerAvailabilities(@PathVariable("activityPackageId") Long activityPackageId) {
        final List<VolunteerAvailability> volunteerAvailabilities =
                volunteerAvailabilityService.getAllVolunteerAvailabilitiesByActivityPackageId(activityPackageId);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toListReadDto(volunteerAvailabilities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @PostMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    public ResponseEntity<VolunteerAvailabilityReadDto> createVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                    @PathVariable("volunteerId") Long volunteerId,
                                                                                    @Valid @RequestBody VolunteerAvailabilityCreateDto volunteerAvailabilityCreateDto) {
        final VolunteerAvailability savedVolunteerAvailability = volunteerAvailabilityService
                .createActivityPackageVolunteerAvailability(activityPackageId, volunteerId, volunteerAvailabilityCreateDto);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(savedVolunteerAvailability), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @PutMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    public ResponseEntity<VolunteerAvailabilityReadDto> updateVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                    @PathVariable("volunteerId") Long volunteerId,
                                                                                    @RequestBody VolunteerAvailabilityUpdateDto volunteerAvailabilityUpdateDto) {
        final VolunteerAvailability updatedVolunteerAvailability = volunteerAvailabilityService
                .updateVolunteerAvailability(activityPackageId, volunteerId, volunteerAvailabilityUpdateDto);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(updatedVolunteerAvailability), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @DeleteMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    public ResponseEntity<String> deleteVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                              @PathVariable("volunteerId") Long volunteerId) {
        volunteerAvailabilityService.deleteVolunteerAvailability(activityPackageId, volunteerId);
        return new ResponseEntity<>("Volunteer availability successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{activityPackageId}/assign-volunteers")
    public ResponseEntity<Void> assignVolunteers(@PathVariable("activityPackageId") Long activityPackageId) {
        // activityAssignmentService.assignVolunteersToActivities(activityPackageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
