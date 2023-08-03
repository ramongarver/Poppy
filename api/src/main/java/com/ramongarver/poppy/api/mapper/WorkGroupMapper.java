package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupReadDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkGroupMapper {

    public WorkGroupReadDto toReadDto(WorkGroup workGroup) {
        return WorkGroupReadDto.builder()
                .id(workGroup.getId())
                .name(workGroup.getName())
                .shortName(workGroup.getShortName())
                .description(workGroup.getDescription())
                .volunteerIds(workGroup.getVolunteers().stream().map(Volunteer::getId).toList())
                .build();
    }

    public List<WorkGroupReadDto> toListReadDto(List<WorkGroup> workGroups) {
        return workGroups.stream()
                .map(this::toReadDto)
                .toList();
    }

    public WorkGroup fromCreateDto(WorkGroupCreateDto workGroupCreateDto) {
        return WorkGroup.builder()
                .name(workGroupCreateDto.getName())
                .shortName(workGroupCreateDto.getShortName())
                .description(workGroupCreateDto.getDescription())
                .volunteers(new ArrayList<>())
                .build();
    }

    public void fromUpdateDto(WorkGroup existingWorkGroup, WorkGroupUpdateDto workGroupUpdateDto) {
        existingWorkGroup.setName(workGroupUpdateDto.getName() != null
                ? workGroupUpdateDto.getName() : existingWorkGroup.getName());
        existingWorkGroup.setShortName(workGroupUpdateDto.getShortName() != null
                ? workGroupUpdateDto.getShortName() : existingWorkGroup.getShortName());
        existingWorkGroup.setDescription(workGroupUpdateDto.getDescription() != null
                ? workGroupUpdateDto.getDescription() : existingWorkGroup.getDescription());
    }

}