package com.ramongarver.poppy.api.dto.user;


import com.ramongarver.poppy.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

}
