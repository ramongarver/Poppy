package com.ramongarver.poppy.api.dto.activitypackage;

import com.ramongarver.poppy.api.dto.volunteeravailability.VolunteerAvailabilityReadDto;
import com.ramongarver.poppy.api.enums.ActivityPackageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityPackageReadDto {

    private Long id;

    private String name;

    private String description;

    private ActivityPackageType type;

    private LocalDate availabilityStartDate;

    private LocalDate availabilityEndDate;

    private boolean isVisible;

    private List<Long> activityIds;

    private boolean areVolunteersAssigned;

    private List<VolunteerAvailabilityReadDto> volunteerAvailabilities;

}
