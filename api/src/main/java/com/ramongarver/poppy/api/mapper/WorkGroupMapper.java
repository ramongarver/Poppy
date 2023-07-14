package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupReadDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkGroupMapper {

    private final VolunteerService volunteerService;

    public WorkGroupReadDto toReadDto(WorkGroup workGroup) {
        return WorkGroupReadDto.builder()
                .id(workGroup.getId())
                .name(workGroup.getName())
                .shortName(workGroup.getShortName())
                .description(workGroup.getDescription())
                .volunteersId(workGroup.getVolunteers().stream().map(Volunteer::getId).toList())
                .build();
    }

    public List<WorkGroupReadDto> toListReadDto(List<WorkGroup> workGroups) {
        return workGroups.stream()
                .map(this::toReadDto)
                .toList();
    }

    public WorkGroup fromReadDto(WorkGroupReadDto workGroupReadDto) {
        List<Volunteer> volunteers = volunteerService.getVolunteersByIds(workGroupReadDto.getVolunteersId());

        return WorkGroup.builder()
                .id(workGroupReadDto.getId())
                .name(workGroupReadDto.getName())
                .shortName(workGroupReadDto.getShortName())
                .description(workGroupReadDto.getDescription())
                .volunteers(volunteers)
                .build();
    }

    public WorkGroup fromCreateDto(WorkGroupCreateDto workGroupCreateDto) {
        return WorkGroup.builder()
                .name(workGroupCreateDto.getName())
                .shortName(workGroupCreateDto.getShortName())
                .description(workGroupCreateDto.getDescription())
                .volunteers(new ArrayList<>())
                .build();
    }

    public void fromUpdateDto(WorkGroup workGroup, WorkGroupUpdateDto workGroupUpdateDto) {
        workGroup.setName(workGroupUpdateDto.getName());
        workGroup.setShortName(workGroupUpdateDto.getShortName());
        workGroup.setDescription(workGroupUpdateDto.getDescription());
    }

}