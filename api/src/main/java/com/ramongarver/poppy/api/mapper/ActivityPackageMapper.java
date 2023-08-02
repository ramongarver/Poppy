package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageCreateDto;
import com.ramongarver.poppy.api.dto.activitypackage.ActivityPackageReadDto;
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

    public void fromUpdateDto(ActivityPackage activityPackage, ActivityPackageUpdateDto activityUpdateDto) {
        activityPackage.setName(activityUpdateDto.getName() != null
                ? activityUpdateDto.getName() : activityPackage.getName());
        activityPackage.setDescription(activityUpdateDto.getDescription() != null
                ? activityUpdateDto.getDescription() : activityPackage.getDescription());
        activityPackage.setType(activityUpdateDto.getType() != null
                ? activityUpdateDto.getType() : activityPackage.getType());
        activityPackage.setAvailabilityStartDate(activityUpdateDto.getAvailabilityStartDate() != null
                ? activityUpdateDto.getAvailabilityStartDate() : activityPackage.getAvailabilityStartDate());
        activityPackage.setAvailabilityEndDate(activityUpdateDto.getAvailabilityEndDate() != null
                ? activityUpdateDto.getAvailabilityEndDate() : activityPackage.getAvailabilityEndDate());
        activityPackage.setVisible(activityUpdateDto.isVisible());
    }

}
