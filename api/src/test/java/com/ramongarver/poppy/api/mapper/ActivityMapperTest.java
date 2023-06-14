package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityMapperTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private ActivityMapper activityMapper;

    private final Volunteer volunteer = Volunteer.builder()
            .id(1L)
            .build();
    private final Activity activity = Activity.builder()
            .id(1L)
            .name("Test Activity")
            .description("Test Description")
            .localDateTime(LocalDateTime.now())
            .volunteers(Collections.singletonList(volunteer))
            .build();
    private final ActivityReadDto activityDto = ActivityReadDto.builder()
            .id(1L)
            .name("Test Activity")
            .description("Test Description")
            .localDateTime(LocalDateTime.now())
            .volunteersIds(List.of(1L))
            .build();
    ;

    @BeforeEach
    public void setup() {

    }

    @Test
    void testToDto() {
        final ActivityReadDto result = activityMapper.toReadDto(activity);

        assertEquals(activity.getId(), result.getId());
        assertEquals(activity.getName(), result.getName());
        assertEquals(activity.getDescription(), result.getDescription());
        assertEquals(activity.getLocalDateTime(), result.getLocalDateTime());
        assertEquals(activity.getVolunteers().get(0).getId(), result.getVolunteersIds().get(0));
    }

    @Test
    void testToListDto() {
        final List<ActivityReadDto> results = activityMapper.toListReadDto(Collections.singletonList(activity));

        assertEquals(1, results.size());
        assertEquals(activity.getId(), results.get(0).getId());
    }

    @Test
    void testFromDto() {
        when(volunteerService.getVolunteersByIds(List.of(1L))).thenReturn(Collections.singletonList(volunteer));

        final Activity result = activityMapper.fromReadDto(activityDto);

        assertEquals(activityDto.getId(), result.getId());
        assertEquals(activityDto.getName(), result.getName());
        assertEquals(activityDto.getDescription(), result.getDescription());
        assertEquals(activityDto.getLocalDateTime(), result.getLocalDateTime());
        assertEquals(activityDto.getVolunteersIds().get(0), result.getVolunteers().get(0).getId());
    }

}
