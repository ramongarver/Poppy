package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.user.UserDto;
import com.ramongarver.poppy.api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public List<UserDto> toListDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .toList();
    }

    // Note: password is not included, we might need to handle this separately for security reasons
    public User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .build();
    }

}
