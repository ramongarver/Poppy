package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityReadDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class VolunteerAvailabilityMapper {

    public VolunteerAvailabilityReadDto toReadDto(VolunteerAvailability volunteerAvailability) {
        return VolunteerAvailabilityReadDto.builder()
                .volunteerId(volunteerAvailability.getVolunteer().getId())
                .activityIds(volunteerAvailability.getActivities() != null
                        ? volunteerAvailability.getActivities().stream().map(Activity::getId).toList()
                        : List.of())
                .build();
    }

    public List<VolunteerAvailabilityReadDto> toListReadDto(List<VolunteerAvailability> volunteerAvailabilities) {
        return volunteerAvailabilities.stream()
                .map(this::toReadDto)
                .toList();
    }

}
