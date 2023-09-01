package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityCreateDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityReadDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityUpdateDto;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import com.ramongarver.poppy.api.enums.PrintOption;
import com.ramongarver.poppy.api.mapper.ActivityPackageMapper;
import com.ramongarver.poppy.api.mapper.VolunteerAvailabilityMapper;
import com.ramongarver.poppy.api.service.ActivityAssignmentService;
import com.ramongarver.poppy.api.service.ActivityPackageService;
import com.ramongarver.poppy.api.service.PdfReportService;
import com.ramongarver.poppy.api.service.VolunteerAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.ACTIVITY_PACKAGES_ROUTE)
@Tag(name = "Activity packages", description = "Activity packages management resource, availabilities and assignments")
@SecurityRequirement(name = "JWT Bearer Authentication")
public class ActivityPackageController {

    private final ActivityPackageMapper activityPackageMapper;
    private final VolunteerAvailabilityMapper volunteerAvailabilityMapper;

    private final ActivityAssignmentService activityAssignmentService;
    private final ActivityPackageService activityPackageService;
    private final PdfReportService pdfReportService;
    private final VolunteerAvailabilityService volunteerAvailabilityService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("{activityPackageId}")
    @Operation(summary = "Get an activity package by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityPackageReadDto.class)))
            }
    )
    public ResponseEntity<ActivityPackageReadDto> getActivityPackageById(@PathVariable("activityPackageId") Long activityPackageId) {
        final ActivityPackage activityPackage = activityPackageService.getActivityPackageById(activityPackageId);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(activityPackage), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(summary = "Get all activity packages",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public ResponseEntity<List<ActivityPackageReadDto>> getAllActivityPackages() {
        final List<ActivityPackage> activities = activityPackageService.getAllActivityPackages();
        return new ResponseEntity<>(activityPackageMapper.toListReadDto(activities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping
    @Operation(summary = "Create an activity package",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ActivityPackageReadDto.class)))
            }
    )
    public ResponseEntity<ActivityPackageReadDto> createActivityPackage(@Valid @RequestBody ActivityPackageCreateDto activityPackageCreateDto) {
        final ActivityPackage savedActivityPackage = activityPackageService.createActivityPackage(activityPackageCreateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(savedActivityPackage), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PutMapping("{activityPackageId}")
    @Operation(summary = "Update an activity package by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityPackageReadDto.class)))
            }
    )
    public ResponseEntity<ActivityPackageReadDto> updateActivityPackage(@PathVariable("activityPackageId") Long activityId,
                                                                        @RequestBody ActivityPackageUpdateDto activityPackageUpdateDto) {
        final ActivityPackage updatedActivityPackage = activityPackageService.updateActivityPackage(activityId, activityPackageUpdateDto);
        return new ResponseEntity<>(activityPackageMapper.toReadDto(updatedActivityPackage), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{activityPackageId}")
    @Operation(summary = "Delete an activity package by id",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> deleteActivity(@PathVariable("activityPackageId") Long activityPackageId) {
        activityPackageService.deleteActivityPackage(activityPackageId);
        return new ResponseEntity<>("Activity successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Get volunteer availability by activity package id and volunteer id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VolunteerAvailabilityReadDto.class)))
            }
    )
    public ResponseEntity<VolunteerAvailabilityReadDto> getVolunteerAvailabilityByActivityPackageByIdAndVolunteerId(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                                                    @PathVariable("volunteerId") Long volunteerId) {
        final VolunteerAvailability volunteerAvailability =
                volunteerAvailabilityService.getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(activityPackageId, volunteerId);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(volunteerAvailability), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @Operation(summary = "Get volunteers and activities for which each volunteer is available",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + ControllerConstants.VOLUNTEERS_RESOURCE)
    public ResponseEntity<List<VolunteerAvailabilityReadDto>> getAllVolunteersAndActivityAvailabilities(@PathVariable("activityPackageId") Long activityPackageId) {
        final List<VolunteerAvailability> volunteerAvailabilities =
                volunteerAvailabilityService.getAllVolunteerAvailabilitiesByActivityPackageId(activityPackageId);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toListReadDto(volunteerAvailabilities), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE)
    @Operation(summary = "Get all volunteer availabilities for each activity",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto.class)))
            }
    )
    public ResponseEntity<ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto> getAllVolunteerAvailabilities(@PathVariable("activityPackageId") Long activityPackageId) {
        final ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto activityPackageVolunteerAvailabilitiesDto =
                volunteerAvailabilityService.getActivityPackageVolunteerAvailabilities(activityPackageId);
        return new ResponseEntity<>(activityPackageVolunteerAvailabilitiesDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @GetMapping(
            value = "{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + ControllerConstants.PDF_TYPE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @Operation(summary = "Get all volunteer availabilities for each activity in PDF",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public void getAllVolunteerAvailabilitiesPdf(@PathVariable("activityPackageId") Long activityPackageId,
                                                 HttpServletResponse response) throws IOException {
        // Set some response headers
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=volunteerAvailabilities.pdf");

        // Generate the data for the report
        final ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto activityPackageVolunteerAvailabilitiesDto =
                volunteerAvailabilityService.getActivityPackageVolunteerAvailabilities(activityPackageId);

        // Generate and return the PDF
        pdfReportService.createAssignmentReport(activityPackageVolunteerAvailabilitiesDto, PrintOption.AVAILABILITIES, response.getOutputStream());
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @PostMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Create a volunteer availability",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = VolunteerAvailabilityReadDto.class)))
            }
    )
    public ResponseEntity<VolunteerAvailabilityReadDto> createVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                    @PathVariable("volunteerId") Long volunteerId,
                                                                                    @Valid @RequestBody VolunteerAvailabilityCreateDto volunteerAvailabilityCreateDto) {
        final VolunteerAvailability savedVolunteerAvailability = volunteerAvailabilityService
                .createActivityPackageVolunteerAvailability(activityPackageId, volunteerId, volunteerAvailabilityCreateDto);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(savedVolunteerAvailability), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @PutMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Update a volunteer availability by id",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = VolunteerAvailabilityReadDto.class)))
            }
    )
    public ResponseEntity<VolunteerAvailabilityReadDto> updateVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                                                    @PathVariable("volunteerId") Long volunteerId,
                                                                                    @RequestBody VolunteerAvailabilityUpdateDto volunteerAvailabilityUpdateDto) {
        final VolunteerAvailability updatedVolunteerAvailability = volunteerAvailabilityService
                .updateVolunteerAvailability(activityPackageId, volunteerId, volunteerAvailabilityUpdateDto);
        return new ResponseEntity<>(volunteerAvailabilityMapper.toReadDto(updatedVolunteerAvailability), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR #volunteerId == principal.id")
    @DeleteMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_AVAILABILITIES_RESOURCE + "/{volunteerId}")
    @Operation(summary = "Delete a volunteer availability for an activity package",
            responses = {
                    @ApiResponse(responseCode = "204", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<String> deleteVolunteerAvailability(@PathVariable("activityPackageId") Long activityPackageId,
                                                              @PathVariable("volunteerId") Long volunteerId) {
        volunteerAvailabilityService.deleteVolunteerAvailability(activityPackageId, volunteerId);
        return new ResponseEntity<>("Volunteer availability successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_ASSIGNMENTS_RESOURCE)
    @Operation(summary = "Automatically assign activities to volunteers",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    public ResponseEntity<Void> assignVolunteers(@PathVariable("activityPackageId") Long activityPackageId) {
        activityAssignmentService.assignVolunteersToActivities(activityPackageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @GetMapping("{activityPackageId}" + ControllerConstants.VOLUNTEER_ASSIGNMENTS_RESOURCE)
    @Operation(summary = "Get all volunteer assignments",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto.class)))
            }
    )
    public ResponseEntity<ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto> getAllVolunteerAssignments(@PathVariable("activityPackageId") Long activityPackageId) {
        final ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto activityPackageVolunteerAssignmentsDto =
                activityPackageService.getActivityPackageVolunteerAssignments(activityPackageId);
        return new ResponseEntity<>(activityPackageVolunteerAssignmentsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @GetMapping(
            value = "{activityPackageId}" + ControllerConstants.VOLUNTEER_ASSIGNMENTS_RESOURCE + ControllerConstants.PDF_TYPE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    @Operation(summary = "Get all volunteer assignments in PDF",
            responses = {
                    @ApiResponse(responseCode = "200")
            }
    )
    public void getAllVolunteerAssignmentsPdf(@PathVariable("activityPackageId") Long activityPackageId,
                                              HttpServletResponse response) throws IOException {
        // Set some response headers
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=volunteerAssignments.pdf");

        // Generate the data for the report
        final ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto activityPackageVolunteerAssignmentsDto =
                activityPackageService.getActivityPackageVolunteerAssignments(activityPackageId);

        // Generate and return the PDF
        pdfReportService.createAssignmentReport(activityPackageVolunteerAssignmentsDto, PrintOption.ASSIGNMENTS, response.getOutputStream());
    }

}
