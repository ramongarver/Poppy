package com.ramongarver.poppy.api.mapper;


import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReadDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolunteerMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private VolunteerMapper volunteerMapper;

    private final LocalDate START_DATE = LocalDate.of(2022, 9, 1);
    private final LocalDate END_DATE = LocalDate.of(2023, 7, 1);

    private final String TEST_PASSWORD = "TestPassword";
    private final String TEST_PASSWORD_ENCODED = "TestPasswordEncoded";

    private final Activity activity = Activity.builder()
            .id(1L)
            .build();

    private final WorkGroup workGroup = WorkGroup.builder()
            .id(1L)
            .build();

    private final Volunteer volunteer = Volunteer.builder()
            .id(1L)
            .firstName("Test Volunteer FirstName")
            .lastName("Test Volunteer LastName")
            .email("test@example.com")
            .password(TEST_PASSWORD_ENCODED)
            .role(Role.USER)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .workGroups(List.of(workGroup))
            .activities(List.of(activity))
            .build();

    private final VolunteerCreateDto volunteerCreateDto = VolunteerCreateDto.builder()
            .firstName("Test Volunteer FirstName")
            .lastName("Test Volunteer LastName")
            .email("test@example.com")
            .password(TEST_PASSWORD)
            .role(Role.USER)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .build();
    private final VolunteerUpdateDto volunteerUpdateDto = VolunteerUpdateDto.builder()
            .firstName("Updated Volunteer FirstName")
            .lastName("Updated Volunteer LastName")
            .email("updated@example.com")
            .role(Role.ADMIN)
            .startDate(START_DATE)
            .endDate(END_DATE.plusWeeks(4))
            .build();

    @BeforeEach
    void setup() {

    }

    @Test
    void testToReadDto() {
        final VolunteerReadDto result = volunteerMapper.toReadDto(volunteer);

        assertAll(
                () -> assertEquals(volunteer.getId(), result.getId()),
                () -> assertEquals(volunteer.getFirstName(), result.getFirstName()),
                () -> assertEquals(volunteer.getLastName(), result.getLastName()),
                () -> assertEquals(volunteer.getEmail(), result.getEmail()),
                () -> assertEquals(volunteer.getStartDate(), result.getStartDate()),
                () -> assertEquals(volunteer.getEndDate(), result.getEndDate()),
                () -> assertEquals(volunteer.getWorkGroups().get(0).getId(), result.getWorkGroupIds().get(0)),
                () -> assertEquals(volunteer.getActivities().get(0).getId(), result.getActivityIds().get(0))
        );
    }

    @Test
    void testToListReadDto() {
        final List<VolunteerReadDto> result = volunteerMapper.toListReadDto(List.of(volunteer));

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(volunteer.getId(), result.get(0).getId()),
                () -> assertEquals(volunteer.getFirstName(), result.get(0).getFirstName()),
                () -> assertEquals(volunteer.getLastName(), result.get(0).getLastName()),
                () -> assertEquals(volunteer.getEmail(), result.get(0).getEmail()),
                () -> assertEquals(volunteer.getStartDate(), result.get(0).getStartDate()),
                () -> assertEquals(volunteer.getEndDate(), result.get(0).getEndDate()),
                () -> assertEquals(volunteer.getWorkGroups().get(0).getId(), result.get(0).getWorkGroupIds().get(0)),
                () -> assertEquals(volunteer.getActivities().get(0).getId(), result.get(0).getActivityIds().get(0))
        );
    }

    @Test
    void testFromCreateDto() {
        when(passwordEncoder.encode(anyString())).thenReturn(TEST_PASSWORD_ENCODED);

        final Volunteer result = volunteerMapper.fromCreateDto(volunteerCreateDto);

        assertAll(
                () -> assertEquals(volunteerCreateDto.getFirstName(), result.getFirstName()),
                () -> assertEquals(volunteerCreateDto.getLastName(), result.getLastName()),
                () -> assertEquals(volunteerCreateDto.getEmail(), result.getEmail()),
                () -> assertEquals(TEST_PASSWORD_ENCODED, result.getPassword()),
                () -> assertEquals(volunteerCreateDto.getRole(), result.getRole()),
                () -> assertEquals(volunteerCreateDto.getStartDate(), result.getStartDate()),
                () -> assertEquals(volunteerCreateDto.getEndDate(), result.getEndDate())
        );
    }

    @Test
    void testFromUpdateDto() {
        doAnswer(invocation -> {
            final Volunteer vol = invocation.getArgument(0);
            vol.setFirstName(volunteerUpdateDto.getFirstName());
            vol.setLastName(volunteerUpdateDto.getLastName());
            vol.setEmail(volunteerUpdateDto.getEmail());
            vol.setRole(volunteerUpdateDto.getRole());
            return null;
        }).when(userMapper).fromUpdateDto(any(Volunteer.class), any(VolunteerUpdateDto.class));

        volunteerMapper.fromUpdateDto(volunteer, volunteerUpdateDto);

        verify(userMapper, times(1)).fromUpdateDto(volunteer, volunteerUpdateDto);
        assertAll(
                () -> assertEquals(volunteerUpdateDto.getFirstName(), volunteer.getFirstName()),
                () -> assertEquals(volunteerUpdateDto.getLastName(), volunteer.getLastName()),
                () -> assertEquals(volunteerUpdateDto.getEmail(), volunteer.getEmail()),
                () -> assertEquals(volunteerUpdateDto.getRole(), volunteer.getRole()),
                () -> assertEquals(volunteerUpdateDto.getStartDate(), volunteer.getStartDate()),
                () -> assertEquals(volunteerUpdateDto.getEndDate(), volunteer.getEndDate())
        );
    }
}