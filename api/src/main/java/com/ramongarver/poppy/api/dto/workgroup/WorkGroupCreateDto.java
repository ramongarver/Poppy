package com.ramongarver.poppy.api.dto.workgroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class WorkGroupCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private String shortName;

    @NotNull
    private String description;

}
