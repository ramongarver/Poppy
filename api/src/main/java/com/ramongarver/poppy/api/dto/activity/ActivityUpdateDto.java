package com.ramongarver.poppy.api.dto.activity;

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
public class ActivityUpdateDto {

    private String name;

    private String description;

    private LocalDateTime localDateTime;

    private String place;

}
