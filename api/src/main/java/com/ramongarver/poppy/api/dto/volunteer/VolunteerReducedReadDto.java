package com.ramongarver.poppy.api.dto.volunteer;

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
public class VolunteerReducedReadDto {

    private Long id;

    private String firstName;

    private String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName).trim();
    }

    public String getFullNameWithLastNameInitial() {
        return String.format("%s %s", firstName, lastName.substring(0, 1).toUpperCase() + ".").trim();
    }

}
