package com.ramongarver.poppy.api.mapper;


import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import com.ramongarver.poppy.api.enums.ActivityPackageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ActivityPackageMapperTest {

    @InjectMocks
    private ActivityPackageMapper activityPackageMapper;

    private final Activity activity = Activity.builder().id(1L).build();

    private ActivityPackage activityPackage;
    private final ActivityPackage existingActivityPackage = ActivityPackage.builder()
            .id(1L)
            .name("Test ActivityPackage Name")
            .description("Test ActivityPackage Description")
            .type(ActivityPackageType.GENERAL)
            .availabilityStartDate(LocalDate.of(2023, 6, 26))
            .availabilityEndDate(LocalDate.of(2023, 6, 30))
            .maxActivitiesPerVolunteer(2)
            .minCoordinatorsToIgnoreLimit(3)
            .isVisible(false)
            .activities(Collections.singletonList(activity))
            .areVolunteersAssigned(false)
            .build();

    private final ActivityPackageCreateDto activityPackageCreateDto = ActivityPackageCreateDto.builder()
            .name("Test ActivityPackage Name")
            .description("Test ActivityPackage Description")
            .type(ActivityPackageType.GENERAL)
            .availabilityStartDate(LocalDate.of(2023, 6, 26))
            .availabilityEndDate(LocalDate.of(2023, 6, 30))
            .maxActivitiesPerVolunteer(2)
            .minCoordinatorsToIgnoreLimit(3)
            .isVisible(false)
            .build();
    private final ActivityPackageUpdateDto activityPackageUpdateDto = ActivityPackageUpdateDto.builder()
            .name("Test ActivityPackage Name Update")
            .description("Test ActivityPackage Description Update")
            .type(ActivityPackageType.GENERAL)
            .availabilityStartDate(LocalDate.of(2024, 1, 26))
            .availabilityEndDate(LocalDate.of(2024, 6, 30))
            .maxActivitiesPerVolunteer(2)
            .minCoordinatorsToIgnoreLimit(3)
            .isVisible(true)
            .build();
    private final ActivityPackageUpdateDto activityPackageUpdateDtoWithNullAttributes = ActivityPackageUpdateDto.builder()
            .name(null)
            .description(null)
            .type(null)
            .availabilityStartDate(null)
            .availabilityEndDate(null)
            .maxActivitiesPerVolunteer(null)
            .minCoordinatorsToIgnoreLimit(null)
            .isVisible(false)
            .build();

    @BeforeEach
    public void setup() {
        activityPackage = ActivityPackage.builder()
                .id(1L)
                .name("Test ActivityPackage Name")
                .description("Test ActivityPackage Description")
                .type(ActivityPackageType.GENERAL)
                .availabilityStartDate(LocalDate.of(2023, 6, 26))
                .availabilityEndDate(LocalDate.of(2023, 6, 30))
                .maxActivitiesPerVolunteer(2)
                .minCoordinatorsToIgnoreLimit(3)
                .isVisible(false)
                .activities(Collections.singletonList(activity))
                .areVolunteersAssigned(false)
                .build();
    }

    @Test
    void testToReadDto() {
        final ActivityPackageReadDto result = activityPackageMapper.toReadDto(activityPackage);

        assertAll(
                () -> assertEquals(activityPackage.getId(), result.getId()),
                () -> assertEquals(activityPackage.getName(), result.getName()),
                () -> assertEquals(activityPackage.getDescription(), result.getDescription()),
                () -> assertEquals(activityPackage.getType(), result.getType()),
                () -> assertEquals(activityPackage.getAvailabilityStartDate(), result.getAvailabilityStartDate()),
                () -> assertEquals(activityPackage.getAvailabilityEndDate(), result.getAvailabilityEndDate()),
                () -> assertEquals(activityPackage.getIsVisible(), result.getIsVisible()),
                () -> assertEquals(activityPackage.getActivities().get(0).getId(), result.getActivityIds().get(0)),
                () -> assertEquals(activityPackage.getAreVolunteersAssigned(), result.getAreVolunteersAssigned())
        );
    }

    @Test
    void testToReadDtoWhenActivitiesIsNull() {
        activityPackage.setActivities(null);

        final ActivityPackageReadDto result = activityPackageMapper.toReadDto(activityPackage);

        assertAll(
                () -> assertEquals(activityPackage.getId(), result.getId()),
                () -> assertEquals(activityPackage.getName(), result.getName()),
                () -> assertEquals(activityPackage.getDescription(), result.getDescription()),
                () -> assertEquals(activityPackage.getType(), result.getType()),
                () -> assertEquals(activityPackage.getAvailabilityStartDate(), result.getAvailabilityStartDate()),
                () -> assertEquals(activityPackage.getAvailabilityEndDate(), result.getAvailabilityEndDate()),
                () -> assertEquals(activityPackage.getIsVisible(), result.getIsVisible()),
                () -> assertTrue(result.getActivityIds().isEmpty()),
                () -> assertEquals(activityPackage.getAreVolunteersAssigned(), result.getAreVolunteersAssigned())
        );
    }

    @Test
    void testToListReadDto() {
        final List<ActivityPackageReadDto> results = activityPackageMapper.toListReadDto(Collections.singletonList(activityPackage));

        assertAll(
                () -> assertEquals(1, results.size()),
                () -> assertEquals(activityPackage.getId(), results.get(0).getId())
        );
    }

    @Test
    void testFromCreateDto() {
        final ActivityPackage result = activityPackageMapper.fromCreateDto(activityPackageCreateDto);

        assertAll(
                () -> assertEquals(activityPackageCreateDto.getName(), result.getName()),
                () -> assertEquals(activityPackageCreateDto.getDescription(), result.getDescription()),
                () -> assertEquals(activityPackageCreateDto.getType(), result.getType()),
                () -> assertEquals(activityPackageCreateDto.getAvailabilityStartDate(), result.getAvailabilityStartDate()),
                () -> assertEquals(activityPackageCreateDto.getAvailabilityEndDate(), result.getAvailabilityEndDate()),
                () -> assertEquals(activityPackageCreateDto.getMaxActivitiesPerVolunteer(), result.getMaxActivitiesPerVolunteer()),
                () -> assertEquals(activityPackageCreateDto.getMinCoordinatorsToIgnoreLimit(), result.getMinCoordinatorsToIgnoreLimit()),
                () -> assertEquals(activityPackageCreateDto.getIsVisible(), result.getIsVisible()),
                () -> assertEquals(Boolean.FALSE, result.getAreVolunteersAssigned())
        );
    }

    @Test
    void testFromUpdateDto() {
        activityPackageMapper.fromUpdateDto(existingActivityPackage, activityPackageUpdateDto);

        assertAll(
                () -> assertEquals(activityPackage.getId(), existingActivityPackage.getId()),
                () -> assertEquals(activityPackageUpdateDto.getName(), existingActivityPackage.getName()),
                () -> assertEquals(activityPackageUpdateDto.getDescription(), existingActivityPackage.getDescription()),
                () -> assertEquals(activityPackageUpdateDto.getType(), existingActivityPackage.getType()),
                () -> assertEquals(activityPackageUpdateDto.getAvailabilityStartDate(), existingActivityPackage.getAvailabilityStartDate()),
                () -> assertEquals(activityPackageUpdateDto.getAvailabilityEndDate(), existingActivityPackage.getAvailabilityEndDate()),
                () -> assertEquals(activityPackageCreateDto.getMaxActivitiesPerVolunteer(), existingActivityPackage.getMaxActivitiesPerVolunteer()),
                () -> assertEquals(activityPackageCreateDto.getMinCoordinatorsToIgnoreLimit(), existingActivityPackage.getMinCoordinatorsToIgnoreLimit()),
                () -> assertEquals(activityPackageUpdateDto.getIsVisible(), existingActivityPackage.getIsVisible())
        );
    }

    @Test
    void testFromUpdateDtoWithNullAttributes() {
        activityPackageMapper.fromUpdateDto(existingActivityPackage, activityPackageUpdateDtoWithNullAttributes);

        assertAll(
                () -> assertEquals(activityPackage.getId(), existingActivityPackage.getId()),
                () -> assertEquals(activityPackage.getName(), existingActivityPackage.getName()),
                () -> assertEquals(activityPackage.getDescription(), existingActivityPackage.getDescription()),
                () -> assertEquals(activityPackage.getType(), existingActivityPackage.getType()),
                () -> assertEquals(activityPackage.getAvailabilityStartDate(), existingActivityPackage.getAvailabilityStartDate()),
                () -> assertEquals(activityPackage.getAvailabilityEndDate(), existingActivityPackage.getAvailabilityEndDate()),
                () -> assertEquals(activityPackageCreateDto.getMaxActivitiesPerVolunteer(), existingActivityPackage.getMaxActivitiesPerVolunteer()),
                () -> assertEquals(activityPackageCreateDto.getMinCoordinatorsToIgnoreLimit(), existingActivityPackage.getMinCoordinatorsToIgnoreLimit()),
                () -> assertEquals(activityPackageUpdateDtoWithNullAttributes.getIsVisible(), existingActivityPackage.getIsVisible())
        );
    }

}
