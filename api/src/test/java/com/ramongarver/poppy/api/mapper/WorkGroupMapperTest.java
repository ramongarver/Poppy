package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupReadDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.service.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkGroupMapperTest {

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private WorkGroupMapper workGroupMapper;

    private final Volunteer volunteer = Volunteer.builder()
            .id(1L)
            .build();

    private final WorkGroup workGroup = WorkGroup.builder()
            .id(1L)
            .name("Test WorkGroup Name")
            .shortName("Test WorkGroup ShortName")
            .description("Test WorkGroup Description")
            .volunteers(Collections.singletonList(volunteer))
            .build();
    private final WorkGroup existingWorkGroup = WorkGroup.builder()
            .id(1L)
            .name("Test WorkGroup Name")
            .shortName("Test WorkGroup ShortName")
            .description("Test WorkGroup Description")
            .volunteers(Collections.singletonList(volunteer))
            .build();

    private final WorkGroupReadDto workGroupReadDto = WorkGroupReadDto.builder()
            .id(1L)
            .name("Test WorkGroup Name")
            .shortName("Test WorkGroup ShortName")
            .description("Test WorkGroup Description")
            .volunteerIds(List.of(1L))
            .build();
    private final WorkGroupCreateDto workGroupCreateDto = WorkGroupCreateDto.builder()
            .name("Test WorkGroup Name")
            .shortName("Test WorkGroup ShortName")
            .description("Test WorkGroup Description")
            .build();
    private final WorkGroupUpdateDto workGroupUpdateDto = WorkGroupUpdateDto.builder()
            .name("Test WorkGroup Name Update")
            .shortName("Test WorkGroup ShortName Update")
            .description("Test WorkGroup Description Update")
            .build();
    private final WorkGroupUpdateDto workGroupUpdateDtoWithNullAttributes = WorkGroupUpdateDto.builder()
            .name(null)
            .shortName(null)
            .description(null)
            .build();

    @BeforeEach
    public void setup() {

    }

    @Test
    void testToReadDto() {
        final WorkGroupReadDto result = workGroupMapper.toReadDto(workGroup);

        assertAll(
                () -> assertEquals(workGroup.getId(), result.getId()),
                () -> assertEquals(workGroup.getName(), result.getName()),
                () -> assertEquals(workGroup.getShortName(), result.getShortName()),
                () -> assertEquals(workGroup.getDescription(), result.getDescription()),
                () -> assertEquals(workGroup.getVolunteers().get(0).getId(), result.getVolunteerIds().get(0))
        );
    }

    @Test
    void testToListReadDto() {
        final List<WorkGroupReadDto> results = workGroupMapper.toListReadDto(Collections.singletonList(workGroup));

        assertAll(
                () -> assertEquals(1, results.size()),
                () -> assertEquals(workGroup.getId(), results.get(0).getId())
        );
    }


    @Test
    void fromReadDto() {
        when(volunteerService.getVolunteersByIds(List.of(1L))).thenReturn(Collections.singletonList(volunteer));

        final WorkGroup result = workGroupMapper.fromReadDto(workGroupReadDto);

        assertAll(
                () -> assertEquals(workGroupReadDto.getId(), result.getId()),
                () -> assertEquals(workGroupReadDto.getName(), result.getName()),
                () -> assertEquals(workGroupReadDto.getShortName(), result.getShortName()),
                () -> assertEquals(workGroupReadDto.getDescription(), result.getDescription()),
                () -> assertEquals(workGroupReadDto.getVolunteerIds().get(0), result.getVolunteers().get(0).getId())
        );
    }

    @Test
    void testFromCreateDto() {
        final WorkGroup result = workGroupMapper.fromCreateDto(workGroupCreateDto);

        assertAll(
                () -> assertEquals(workGroupCreateDto.getName(), result.getName()),
                () -> assertEquals(workGroupCreateDto.getShortName(), result.getShortName()),
                () -> assertEquals(workGroupCreateDto.getDescription(), result.getDescription())
        );
    }

    @Test
    void testFromUpdateDto() {
        workGroupMapper.fromUpdateDto(existingWorkGroup, workGroupUpdateDto);

        assertAll(
                () -> assertEquals(workGroup.getId(), existingWorkGroup.getId()),
                () -> assertEquals(workGroupUpdateDto.getName(), existingWorkGroup.getName()),
                () -> assertEquals(workGroupUpdateDto.getShortName(), existingWorkGroup.getShortName()),
                () -> assertEquals(workGroupUpdateDto.getDescription(), existingWorkGroup.getDescription()),
                () -> assertEquals(workGroup.getVolunteers().get(0).getId(), existingWorkGroup.getVolunteers().get(0).getId())
        );
    }

    @Test
    void testFromUpdateDtoWithNullAttributes() {
        workGroupMapper.fromUpdateDto(existingWorkGroup, workGroupUpdateDtoWithNullAttributes);

        assertAll(
                () -> assertEquals(workGroup.getId(), existingWorkGroup.getId()),
                () -> assertEquals(workGroup.getName(), existingWorkGroup.getName()),
                () -> assertEquals(workGroup.getShortName(), existingWorkGroup.getShortName()),
                () -> assertEquals(workGroup.getDescription(), existingWorkGroup.getDescription()),
                () -> assertEquals(workGroup.getVolunteers().get(0).getId(), existingWorkGroup.getVolunteers().get(0).getId())
        );
    }

}
