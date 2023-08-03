package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ActivityMapper {

    public ActivityReadDto toReadDto(Activity activity) {
        return ActivityReadDto.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .localDateTime(activity.getLocalDateTime())
                .place(activity.getPlace())
                .volunteerIds(activity.getVolunteers() != null ? activity.getVolunteers().stream().map(Volunteer::getId).toList() : List.of())
                .build();
    }

    public List<ActivityReadDto> toListReadDto(List<Activity> activities) {
        return activities.stream()
                .map(this::toReadDto)
                .toList();
    }

    public Activity fromCreateDto(ActivityCreateDto activityCreateDto) {
        return Activity.builder()
                .name(activityCreateDto.getName())
                .description(activityCreateDto.getDescription())
                .localDateTime(activityCreateDto.getLocalDateTime())
                .place(activityCreateDto.getPlace())
                .build();
    }

    public void fromUpdateDto(Activity existingActivity, ActivityUpdateDto activityUpdateDto) {
        existingActivity.setName(activityUpdateDto.getName() != null
                ? activityUpdateDto.getName() : existingActivity.getName());
        existingActivity.setDescription(activityUpdateDto.getDescription() != null
                ? activityUpdateDto.getDescription() : existingActivity.getDescription());
        existingActivity.setLocalDateTime(activityUpdateDto.getLocalDateTime() != null
                ? activityUpdateDto.getLocalDateTime() : existingActivity.getLocalDateTime());
        existingActivity.setPlace(activityUpdateDto.getPlace() != null
                ? activityUpdateDto.getPlace() : existingActivity.getPlace());
    }

}
