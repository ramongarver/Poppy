package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReducedReadDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.ActivityPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ActivityPackageMapper {

    private static final Integer DEFAULT_MAX_ACTIVITIES_PER_VOLUNTEER = 2;
    private static final Integer DEFAULT_MIN_COORDINATORS_TO_IGNORE_LIMIT = 2;
    private static final Boolean DEFAULT_IS_VISIBLE = Boolean.TRUE;

    public ActivityPackageReadDto toReadDto(ActivityPackage activityPackage) {
        return ActivityPackageReadDto.builder()
                .id(activityPackage.getId())
                .name(activityPackage.getName())
                .description(activityPackage.getDescription())
                .type(activityPackage.getType())
                .availabilityStartDate(activityPackage.getAvailabilityStartDate())
                .availabilityEndDate(activityPackage.getAvailabilityEndDate())
                .maxActivitiesPerVolunteer(activityPackage.getMaxActivitiesPerVolunteer())
                .minCoordinatorsToIgnoreLimit(activityPackage.getMinCoordinatorsToIgnoreLimit())
                .isVisible(activityPackage.getIsVisible())
                .activityIds(activityPackage.getActivities() != null
                        ? activityPackage.getActivities().stream().map(Activity::getId).toList()
                        : List.of())
                .areVolunteersAssigned(activityPackage.getAreVolunteersAssigned())
                .build();
    }

    public List<ActivityPackageReadDto> toListReadDto(List<ActivityPackage> activityPackages) {
        return activityPackages.stream()
                .map(this::toReadDto)
                .toList();
    }

    public ActivityPackage fromCreateDto(ActivityPackageCreateDto activityPackageCreateDto) {
        return ActivityPackage.builder()
                .name(activityPackageCreateDto.getName())
                .description(activityPackageCreateDto.getDescription())
                .type(activityPackageCreateDto.getType())
                .availabilityStartDate(activityPackageCreateDto.getAvailabilityStartDate())
                .availabilityEndDate(activityPackageCreateDto.getAvailabilityEndDate())
                .maxActivitiesPerVolunteer(activityPackageCreateDto.getMaxActivitiesPerVolunteer() != null
                        ? activityPackageCreateDto.getMaxActivitiesPerVolunteer()
                        : DEFAULT_MAX_ACTIVITIES_PER_VOLUNTEER)
                .minCoordinatorsToIgnoreLimit(activityPackageCreateDto.getMinCoordinatorsToIgnoreLimit() != null
                        ? activityPackageCreateDto.getMinCoordinatorsToIgnoreLimit()
                        : DEFAULT_MIN_COORDINATORS_TO_IGNORE_LIMIT)
                .isVisible(activityPackageCreateDto.getIsVisible() != null
                        ? activityPackageCreateDto.getIsVisible()
                        : DEFAULT_IS_VISIBLE)
                .areVolunteersAssigned(Boolean.FALSE)
                .build();
    }

    public void fromUpdateDto(ActivityPackage existingActivityPackage, ActivityPackageUpdateDto activityUpdateDto) {
        existingActivityPackage.setName(activityUpdateDto.getName() != null
                ? activityUpdateDto.getName() : existingActivityPackage.getName());
        existingActivityPackage.setDescription(activityUpdateDto.getDescription() != null
                ? activityUpdateDto.getDescription() : existingActivityPackage.getDescription());
        existingActivityPackage.setType(activityUpdateDto.getType() != null
                ? activityUpdateDto.getType() : existingActivityPackage.getType());
        existingActivityPackage.setAvailabilityStartDate(activityUpdateDto.getAvailabilityStartDate() != null
                ? activityUpdateDto.getAvailabilityStartDate() : existingActivityPackage.getAvailabilityStartDate());
        existingActivityPackage.setAvailabilityEndDate(activityUpdateDto.getAvailabilityEndDate() != null
                ? activityUpdateDto.getAvailabilityEndDate() : existingActivityPackage.getAvailabilityEndDate());
        existingActivityPackage.setMaxActivitiesPerVolunteer(activityUpdateDto.getMaxActivitiesPerVolunteer() != null
                ? activityUpdateDto.getMaxActivitiesPerVolunteer() : existingActivityPackage.getMaxActivitiesPerVolunteer());
        existingActivityPackage.setMinCoordinatorsToIgnoreLimit(activityUpdateDto.getMinCoordinatorsToIgnoreLimit() != null
                ? activityUpdateDto.getMinCoordinatorsToIgnoreLimit() : existingActivityPackage.getMinCoordinatorsToIgnoreLimit());
        existingActivityPackage.setIsVisible(activityUpdateDto.getIsVisible() != null
                ? activityUpdateDto.getIsVisible() : existingActivityPackage.getIsVisible());
    }

    public ActivityPackageReducedReadDto toReducedReadDto(ActivityPackage activityPackage) {
        return ActivityPackageReducedReadDto.builder()
                .id(activityPackage.getId())
                .name(activityPackage.getName())
                .description(activityPackage.getDescription())
                .type(activityPackage.getType())
                .build();
    }
}
