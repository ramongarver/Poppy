package com.ramongarver.poppy.api.dto.activity;

import com.ramongarver.poppy.api.dto.volunteer.VolunteerReducedReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityVolunteerAvailabilitiesAndAssignmentsReadDto {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime localDateTime;

    private String place;

    private int numberOfCoordinators;

    private List<VolunteerReducedReadDto> availabilities;

    private List<VolunteerReducedReadDto> assignments;

}
