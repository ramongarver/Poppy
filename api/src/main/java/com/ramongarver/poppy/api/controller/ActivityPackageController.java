package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.service.ActivityAssignmentService;
import com.ramongarver.poppy.api.service.ActivityPackageService;
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

    private final ActivityPackageService activityPackageService;
    private final ActivityAssignmentService activityAssignmentService;

    @GetMapping("{id}")
    public ResponseEntity<ActivityPackageReadDto> getActivityById(@PathVariable("id") Long activityPackageId) {
        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(activityPackage), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActivityPackageReadDto>> getAllActivities() {
        final List<ActivityPackage> activities = activityPackageService.getAllActivityPackages();
        return new ResponseEntity<>(activityPackageMapper.toListReadDto(activities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ActivityPackageReadDto> createActivity(@Valid @RequestBody ActivityPackageCreateDto activityPackageCreateDto) {
        final ActivityPackage savedActivityPackage = activityPackageService.createActivityPackage(activityPackageCreateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(savedActivityPackage), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ActivityPackageReadDto> updateActivity(@PathVariable("id") Long activityId,
                                                                 @RequestBody ActivityPackageUpdateDto activityPackageUpdateDto) {
        final ActivityPackage updatedActivityPackage = activityPackageService.updateActivityPackage(activityId, activityPackageUpdateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(updatedActivityPackage), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteActivity(@PathVariable("id") Long activityPackageId) {
        activityPackageService.deleteActivityPackage(activityPackageId);
        return new ResponseEntity<>("Activity successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PostMapping("{id}/assign-volunteers")
    public ResponseEntity<Void> assignVolunteers(@PathVariable("id") Long activityPackageId) {
        activityAssignmentService.assignVolunteersToActivities(activityPackageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
