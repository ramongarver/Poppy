package com.ramongarver.poppy.api.dto.workgroup;

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
public class WorkGroupUpdateDto {

    private String name;

    private String shortName;

    private String description;

}
