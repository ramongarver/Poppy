package com.ramongarver.poppy.api.dto.workgroup;

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
public class WorkGroupReadDto {

    private Long id;

    private String name;

    private String shortName;

    private String description;

    private List<Long> volunteerIds;


}
