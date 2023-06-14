package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.VolunteerDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.service.ActivityService;
import com.ramongarver.poppy.api.service.WorkGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class VolunteerMapper {

    private final ActivityService activityService;
    private final WorkGroupService workGroupService;

    public VolunteerDto toDto(Volunteer volunteer) {
        return VolunteerDto.builder()
                .id(volunteer.getId())
                .firstName(volunteer.getFirstName())
                .lastName(volunteer.getLastName())
                .email(volunteer.getEmail())
                .startDate(volunteer.getStartDate())
                .endDate(volunteer.getEndDate())
                .workGroupsIds(volunteer.getWorkGroups().stream().map(WorkGroup::getId).toList())
                .activitiesIds(volunteer.getActivities().stream().map(Activity::getId).toList())
                .build();
    }

    public Volunteer fromDto(VolunteerDto volunteerDto) {
        final List<Activity> activities = activityService.getActivitiesByIds(volunteerDto.getActivitiesIds());
        final List<WorkGroup> workGroups = workGroupService.getWorkGroupsByIds(volunteerDto.getWorkGroupsIds());

        return Volunteer.builder()
                .id(volunteerDto.getId())
                .firstName(volunteerDto.getFirstName())
                .lastName(volunteerDto.getLastName())
                .email(volunteerDto.getEmail())
                .role(volunteerDto.getRole())
                .startDate(volunteerDto.getStartDate())
                .endDate(volunteerDto.getEndDate())
                .activities(activities)
                .workGroups(workGroups)
                .build();
    }

}
