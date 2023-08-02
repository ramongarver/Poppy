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
public class ActivityPackageUpdateDto {

    private String name;

    private String description;

    private ActivityPackageType type;

    private LocalDate availabilityStartDate;

    private LocalDate availabilityEndDate;

    private boolean isVisible;

}
