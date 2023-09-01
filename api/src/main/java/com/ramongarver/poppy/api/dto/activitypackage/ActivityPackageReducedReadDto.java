package com.ramongarver.poppy.api.dto.activitypackage;

import com.ramongarver.poppy.api.enums.ActivityPackageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityPackageReducedReadDto {

    private Long id;

    private String name;

    private String description;

    private ActivityPackageType type;

}
