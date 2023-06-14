package com.ramongarver.poppy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VolunteerDto extends UserDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> workGroupsIds;

    private List<Long> activitiesIds;

}
