package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ActivityMapper {

    private final VolunteerService volunteerService;

    public ActivityReadDto toReadDto(Activity activity) {
        return ActivityReadDto.builder()
                .id(activity.getId())
                .name(activity.getName())
                .description(activity.getDescription())
                .localDateTime(activity.getLocalDateTime())
                .volunteersIds(activity.getVolunteers() != null ? activity.getVolunteers().stream().map(Volunteer::getId).toList() : List.of())
                .build();
    }

    public List<ActivityReadDto> toListReadDto(List<Activity> activities) {
        return activities.stream()
                .map(this::toReadDto)
                .toList();
    }

    public Activity fromReadDto(ActivityReadDto activityReadDto) {
        List<Volunteer> volunteers = volunteerService.getVolunteersByIds(activityReadDto.getVolunteersIds());

        return Activity.builder()
                .id(activityReadDto.getId())
                .name(activityReadDto.getName())
                .description(activityReadDto.getDescription())
                .localDateTime(activityReadDto.getLocalDateTime())
                .volunteers(volunteers)
                .build();
    }

    public Activity fromCreateDto(ActivityCreateDto activityCreateDto) {
        return Activity.builder()
                .name(activityCreateDto.getName())
                .description(activityCreateDto.getDescription())
                .localDateTime(activityCreateDto.getLocalDateTime())
                .build();
    }

    public void fromUpdateDto(Activity activity, ActivityUpdateDto activityUpdateDto) {
        activity.setName(activityUpdateDto.getName());
        activity.setDescription(activityUpdateDto.getDescription());
        activity.setLocalDateTime(activityUpdateDto.getLocalDateTime());
    }

}
