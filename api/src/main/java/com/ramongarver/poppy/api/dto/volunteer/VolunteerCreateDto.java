package com.ramongarver.poppy.api.dto.volunteer;

import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import jakarta.validation.constraints.NotNull;
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
public class VolunteerCreateDto extends UserCreateDto {

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

}
