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

    public ActivityPackageReadDto toReadDto(ActivityPackage activityPackage) {
        return ActivityPackageReadDto.builder()
                .id(activityPackage.getId())
                .name(activityPackage.getName())
                .description(activityPackage.getDescription())
                .type(activityPackage.getType())
                .availabilityStartDate(activityPackage.getAvailabilityStartDate())
                .availabilityEndDate(activityPackage.getAvailabilityEndDate())
                .isVisible(activityPackage.isVisible())
                .activityIds(activityPackage.getActivities() != null
                        ? activityPackage.getActivities().stream().map(Activity::getId).toList()
                        : List.of())
                .areVolunteersAssigned(activityPackage.isAreVolunteersAssigned())
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
                .isVisible(activityPackageCreateDto.isVisible())
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
        existingActivityPackage.setVisible(activityUpdateDto.isVisible());
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
