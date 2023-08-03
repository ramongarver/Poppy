package com.ramongarver.poppy.api.dto.volunteer;

import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VolunteerUpdateDto extends UserUpdateDto {

    private LocalDate startDate;

    private LocalDate endDate;

}
