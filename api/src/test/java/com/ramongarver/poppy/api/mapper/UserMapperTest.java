package com.ramongarver.poppy.api.mapper;


import com.ramongarver.poppy.api.dto.user.UserCreateDto;
import com.ramongarver.poppy.api.dto.user.UserReadDto;
import com.ramongarver.poppy.api.dto.user.UserUpdateDto;
import com.ramongarver.poppy.api.entity.User;
import com.ramongarver.poppy.api.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserMapper userMapper;

    private final String TEST_PASSWORD = "TestPassword";
    private final String TEST_PASSWORD_ENCODED = "TestPasswordEncoded";

    private final User user = User.builder()
            .id(1L)
            .firstName("Test User FirstName")
            .lastName("Test User LastName")
            .email("test@example.com")
            .password(TEST_PASSWORD_ENCODED)
            .role(Role.USER)
            .build();

    private final UserCreateDto userCreateDto = UserCreateDto.builder()
            .firstName("Test User FirstName")
            .lastName("Test User LastName")
            .email("test@example.com")
            .password(TEST_PASSWORD)
            .role(Role.USER)
            .build();
    private final UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .firstName("Updated User FirstName")
            .lastName("Updated User LastName")
            .email("updated@example.com")
            .role(Role.ADMIN)
            .build();

    @BeforeEach
    void setup() {

    }

    @Test
    void testToReadDto() {
        final UserReadDto result = userMapper.toReadDto(user);

        assertAll(
                () -> assertEquals(user.getId(), result.getId()),
                () -> assertEquals(user.getFirstName(), result.getFirstName()),
                () -> assertEquals(user.getLastName(), result.getLastName()),
                () -> assertEquals(user.getEmail(), result.getEmail()),
                () -> assertEquals(user.getRole(), result.getRole())
        );
    }

    @Test
    void testToListReadDto() {
        final List<UserReadDto> result = userMapper.toListReadDto(List.of(user));

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(user.getId(), result.get(0).getId()),
                () -> assertEquals(user.getFirstName(), result.get(0).getFirstName()),
                () -> assertEquals(user.getLastName(), result.get(0).getLastName()),
                () -> assertEquals(user.getEmail(), result.get(0).getEmail()),
                () -> assertEquals(user.getRole(), result.get(0).getRole())
        );
    }

    @Test
    void testFromCreateDto() {
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_PASSWORD_ENCODED);

        final User result = userMapper.fromCreateDto(userCreateDto);

        assertAll(
                () -> assertEquals(userCreateDto.getFirstName(), result.getFirstName()),
                () -> assertEquals(userCreateDto.getLastName(), result.getLastName()),
                () -> assertEquals(userCreateDto.getEmail(), result.getEmail()),
                () -> assertEquals(TEST_PASSWORD_ENCODED, result.getPassword()),
                () -> assertEquals(userCreateDto.getRole(), result.getRole())
        );
    }

    @Test
    void testFromUpdateDto() {
        userMapper.fromUpdateDto(user, userUpdateDto);

        assertAll(
                () -> assertEquals(userUpdateDto.getFirstName(), user.getFirstName()),
                () -> assertEquals(userUpdateDto.getLastName(), user.getLastName()),
                () -> assertEquals(userUpdateDto.getEmail(), user.getEmail()),
                () -> assertEquals(userUpdateDto.getRole(), user.getRole())
        );
    }

}
