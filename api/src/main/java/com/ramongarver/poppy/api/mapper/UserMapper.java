package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import com.ramongarver.poppy.api.dto.user.UserReadDto;
import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import com.ramongarver.poppy.api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserReadDto toReadDto(User user) {
        return UserReadDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public List<UserReadDto> toListReadDto(List<User> users) {
        return users.stream()
                .map(this::toReadDto)
                .toList();
    }

    public User fromCreateDto(UserCreateDto userCreateDto) {
        return User.builder()
                .firstName(userCreateDto.getFirstName())
                .lastName(userCreateDto.getLastName())
                .email(userCreateDto.getEmail())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .role(userCreateDto.getRole())
                .build();
    }

    public void fromUpdateDto(User existingUser, UserUpdateDto userUpdateDto) {
        existingUser.setFirstName(userUpdateDto.getFirstName() != null
                ? userUpdateDto.getFirstName() : existingUser.getFirstName());
        existingUser.setLastName(userUpdateDto.getLastName() != null
                ? userUpdateDto.getLastName() : existingUser.getLastName());
        existingUser.setEmail(userUpdateDto.getEmail() != null
                ? userUpdateDto.getEmail() : existingUser.getEmail());
        existingUser.setRole(userUpdateDto.getRole() != null
                ? userUpdateDto.getRole() : existingUser.getRole());
    }

}
