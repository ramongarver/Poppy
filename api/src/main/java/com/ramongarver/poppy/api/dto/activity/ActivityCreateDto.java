package com.ramongarver.poppy.api.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime localDateTime;

}
