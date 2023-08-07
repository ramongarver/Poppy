package com.ramongarver.poppy.api.dto.volunteeravailability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerAvailabilityReadDto {

    private Long volunteerId;

    private List<Long> activityIds;

}
