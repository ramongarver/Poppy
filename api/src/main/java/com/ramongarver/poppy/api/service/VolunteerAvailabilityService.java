package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityCreateDto;
import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityUpdateDto;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;

import java.util.List;

public interface VolunteerAvailabilityService {

    VolunteerAvailability createActivityPackageVolunteerAvailability(Long activityPackageId, Long volunteerId, VolunteerAvailabilityCreateDto volunteerAvailabilityCreateDto);

    VolunteerAvailability getVolunteerAvailabilityByActivityPackageIdAndVolunteerId(Long activityPackageId, Long volunteerId);

    List<VolunteerAvailability> getAllVolunteerAvailabilitiesByActivityPackageId(Long activityPackageId);

    VolunteerAvailability updateVolunteerAvailability(Long activityPackageId, Long volunteerId, VolunteerAvailabilityUpdateDto volunteerAvailabilityUpdateDto);

    void deleteVolunteerAvailability(Long activityPackageId, Long volunteerId);

    ActivityPackageVolunteerAvailabilitiesAndAssignmentsDto getActivityPackageVolunteerAvailabilities(Long activityPackageId);

}
