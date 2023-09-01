package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerIdsDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(ControllerConstants.ACTIVITIES_ROUTE)
@Tag(name = "Activities", description = "Activities management resource")
@SecurityRequirement(name = "JWT Bearer Authentication")
public class ActivityController {

    private final ActivityMapper activityMapper;

    private final ActivityService activityService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{activityId}")
    @Operation(summary = "Get an activity by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityReadDto.class)))
            }
    )
    public ResponseEntity<ActivityReadDto> getActivityById(@PathVariable("activityId") Long activityId) {
        final Activity activity = activityService.getActivityById(activityId);
        return new ResponseEntity<>(activityMapper.toReadDto(activity), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Get all activities",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<List<ActivityReadDto>> getAllActivities() {
        final List<Activity> activities = activityService.getAllActivities();
        return new ResponseEntity<>(activityMapper.toListReadDto(activities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping
    @Operation(summary = "Create an activity",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ActivityReadDto.class)))
            }
    )
    public ResponseEntity<ActivityReadDto> createActivity(@Valid @RequestBody ActivityCreateDto activityCreateDto) {
        final Activity savedActivity = activityService.createActivity(activityCreateDto);
        return new ResponseEntity<>(activityMapper.toReadDto(savedActivity), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PutMapping("{activityId}")
    @Operation(summary = "Update an activity by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityReadDto.class)))
            }
    )
    public ResponseEntity<ActivityReadDto> updateActivity(@PathVariable("activityId") Long activityId,
                                                          @RequestBody ActivityUpdateDto activityUpdateDto) {
        final Activity updatedActivity = activityService.updateActivity(activityId, activityUpdateDto);
        return new ResponseEntity<>(activityMapper.toReadDto(updatedActivity), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{activityId}")
    @Operation(summary = "Delete an activity by id",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> deleteActivity(@PathVariable("activityId") Long activityId) {
        activityService.deleteActivity(activityId);
        return new ResponseEntity<>("Activity successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{activityId}" + ControllerConstants.VOLUNTEERS_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Assign a volunteer to a specific activity",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<Void> assignVolunteerToActivity(@PathVariable("activityId") Long activityId,
                                                          @PathVariable("volunteerId") Long volunteerId) {
        activityService.assignVolunteerToActivity(activityId, volunteerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{activityId}" + ControllerConstants.VOLUNTEERS_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Unassign a volunteer from a specific activity",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<Void> removeVolunteerFromActivity(@PathVariable("activityId") Long activityId,
                                                            @PathVariable("volunteerId") Long volunteerId) {
        activityService.removeVolunteerFromActivity(activityId, volunteerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{activityId}" + ControllerConstants.VOLUNTEERS_RESOURCE)
    @Operation(summary = "Assign volunteers to a specific activity (in bulk)",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<Void> assignVolunteersToActivity(@PathVariable("activityId") Long activityId,
                                                           @RequestBody VolunteerIdsDto volunteerIdsDto) {
        activityService.assignVolunteersToActivity(activityId, volunteerIdsDto.getVolunteerIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{activityId}" + ControllerConstants.VOLUNTEERS_RESOURCE)
    @Operation(summary = "Unassign volunteers from a specific activity (in bulk)",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<Void> removeVolunteersFromActivity(@PathVariable("activityId") Long activityId,
                                                             @RequestBody VolunteerIdsDto volunteerIdsDto) {
        activityService.removeVolunteersFromActivity(activityId, volunteerIdsDto.getVolunteerIds());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
