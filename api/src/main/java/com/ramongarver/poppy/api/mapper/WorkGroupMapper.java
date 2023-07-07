package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkGroupMapper {

    private final VolunteerService volunteerService;

    public WorkGroupDto toDto(WorkGroup workGroup) {
        return WorkGroupDto.builder()
                .id(workGroup.getId())
                .name(workGroup.getName())
                .shortName(workGroup.getShortName())
                .description(workGroup.getDescription())
                .volunteersId(workGroup.getVolunteers().stream().map(Volunteer::getId).toList())
                .build();
    }

    public WorkGroup fromDto(WorkGroupDto workGroupDto) {
        List<Volunteer> volunteers = volunteerService.getVolunteersByIds(workGroupDto.getVolunteersId());

        return WorkGroup.builder()
                .id(workGroupDto.getId())
                .name(workGroupDto.getName())
                .shortName(workGroupDto.getShortName())
                .description(workGroupDto.getDescription())
                .volunteers(volunteers)
                .build();
    }
}