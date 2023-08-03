package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ActivityMapperTest {

    @InjectMocks
    private ActivityMapper activityMapper;

    private static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(2023, 10, 1, 19, 0, 0);

    private final Volunteer volunteer = Volunteer.builder()
            .id(1L)
            .build();

    private final Activity activity = Activity.builder()
            .id(1L)
            .name("Test Activity Name")
            .description("Test Activity Description")
            .localDateTime(FIXED_DATE_TIME)
            .place("Test Activity Place")
            .volunteers(Collections.singletonList(volunteer))
            .build();
    private final Activity existingActivity = Activity.builder()
            .id(1L)
            .name("Test Activity Name")
            .description("Test Activity Description")
            .localDateTime(FIXED_DATE_TIME)
            .place("Test Activity Place")
            .volunteers(Collections.singletonList(volunteer))
            .build();

    private final ActivityCreateDto activityCreateDto = ActivityCreateDto.builder()
            .name("Test Activity Name")
            .description("Test Activity Description")
            .localDateTime(FIXED_DATE_TIME)
            .place("Test Activity Place")
            .build();
    private final ActivityUpdateDto activityUpdateDto = ActivityUpdateDto.builder()
            .name("Test Activity Name Update")
            .description("Test Activity Description Update")
            .localDateTime(FIXED_DATE_TIME)
            .place("Test Activity Place Update")
            .build();
    private final ActivityUpdateDto activityUpdateDtoWithNullAttributes = ActivityUpdateDto.builder()
            .name(null)
            .description(null)
            .localDateTime(null)
            .place(null)
            .build();

    @BeforeEach
    public void setup() {

    }

    @Test
    void testToReadDto() {
        final ActivityReadDto result = activityMapper.toReadDto(activity);

        assertAll(
                () -> assertEquals(activity.getId(), result.getId()),
                () -> assertEquals(activity.getName(), result.getName()),
                () -> assertEquals(activity.getDescription(), result.getDescription()),
                () -> assertEquals(activity.getLocalDateTime(), result.getLocalDateTime()),
                () -> assertEquals(activity.getPlace(), result.getPlace()),
                () -> assertEquals(activity.getVolunteers().get(0).getId(), result.getVolunteerIds().get(0))
        );
    }

    @Test
    void testToListReadDto() {
        final List<ActivityReadDto> results = activityMapper.toListReadDto(Collections.singletonList(activity));

        assertAll(
                () -> assertEquals(1, results.size()),
                () -> assertEquals(activity.getId(), results.get(0).getId())
        );
    }

    @Test
    void testFromCreateDto() {
        final Activity result = activityMapper.fromCreateDto(activityCreateDto);

        assertAll(
                () -> assertEquals(activityCreateDto.getName(), result.getName()),
                () -> assertEquals(activityCreateDto.getDescription(), result.getDescription()),
                () -> assertEquals(activityCreateDto.getLocalDateTime(), result.getLocalDateTime()),
                () -> assertEquals(activityCreateDto.getPlace(), result.getPlace())
        );
    }

    @Test
    void testFromUpdateDto() {
        activityMapper.fromUpdateDto(existingActivity, activityUpdateDto);

        assertAll(
                () -> assertEquals(activity.getId(), existingActivity.getId()),
                () -> assertEquals(activityUpdateDto.getName(), existingActivity.getName()),
                () -> assertEquals(activityUpdateDto.getDescription(), existingActivity.getDescription()),
                () -> assertEquals(activityUpdateDto.getLocalDateTime(), existingActivity.getLocalDateTime()),
                () -> assertEquals(activityUpdateDto.getPlace(), existingActivity.getPlace()),
                () -> assertEquals(activity.getVolunteers().get(0).getId(), existingActivity.getVolunteers().get(0).getId())
        );
    }

    @Test
    void testFromUpdateDtoWithNullAttributes() {
        activityMapper.fromUpdateDto(existingActivity, activityUpdateDtoWithNullAttributes);

        assertAll(
                () -> assertEquals(activity.getId(), existingActivity.getId()),
                () -> assertEquals(activity.getName(), existingActivity.getName()),
                () -> assertEquals(activity.getDescription(), existingActivity.getDescription()),
                () -> assertEquals(activity.getLocalDateTime(), existingActivity.getLocalDateTime()),
                () -> assertEquals(activity.getPlace(), existingActivity.getPlace()),
                () -> assertEquals(activity.getVolunteers().get(0).getId(), existingActivity.getVolunteers().get(0).getId())
        );
    }

}
