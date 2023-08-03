package com.ramongarver.poppy.api.dto.volunteer;

import com.ramongarver.poppy.api.dto.user.UserReadDto;
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
public class VolunteerReadDto extends UserReadDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> workGroupIds;

    private List<Long> activityIds;

}
