package com.ramongarver.poppy.api.dto.activitypackage;

import com.ramongarver.poppy.api.enums.ActivityPackageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityPackageCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @NotNull
    private ActivityPackageType type;

    @NotNull
    private LocalDate availabilityStartDate;

    @NotNull
    private LocalDate availabilityEndDate;

    private Integer maxActivitiesPerVolunteer;

    private Integer minCoordinatorsToIgnoreLimit;

    private Boolean isVisible;

}
