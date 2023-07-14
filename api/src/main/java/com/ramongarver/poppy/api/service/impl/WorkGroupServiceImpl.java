package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.WorkGroupMapper;
import com.ramongarver.poppy.api.repository.WorkGroupRepository;
import com.ramongarver.poppy.api.service.WorkGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkGroupServiceImpl implements WorkGroupService {

    private final WorkGroupMapper workGroupMapper;

    private final WorkGroupRepository workGroupRepository;

    @Override
    public WorkGroup createWorkGroup(WorkGroupCreateDto workGroupCreateDto) {
        final WorkGroup workGroup = workGroupMapper.fromCreateDto(workGroupCreateDto);
        return workGroupRepository.save(workGroup);
    }

    @Override
    public WorkGroup getWorkGroupById(Long workGroupId) {
        return workGroupRepository.findById(workGroupId)
                .orElseThrow(() -> new ResourceNotFoundException(WorkGroup.class.getSimpleName(), "id", workGroupId));
    }

    @Override
    public List<WorkGroup> getWorkGroupsByIds(List<Long> workGroupsIds) {
        return workGroupRepository.findByIdIn(workGroupsIds);
    }

    @Override
    public List<WorkGroup> getAllWorkGroups() {
        return workGroupRepository.findAll();
    }

    @Override
    public WorkGroup updateWorkGroup(Long workGroupId, WorkGroupUpdateDto workGroupUpdateDto) {
        final WorkGroup existingWorkGroup = getWorkGroupById(workGroupId);
        workGroupMapper.fromUpdateDto(existingWorkGroup, workGroupUpdateDto);
        return workGroupRepository.save(existingWorkGroup);
    }

    @Override
    public void deleteWorkGroup(Long workGroupId) {
        workGroupRepository.deleteById(workGroupId);
    }

}
