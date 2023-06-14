package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.repository.WorkGroupRepository;
import com.ramongarver.poppy.api.service.WorkGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkGroupServiceImpl implements WorkGroupService {

    private final WorkGroupRepository workGroupRepository;

    @Override
    public WorkGroup createWorkGroup(WorkGroup workGroup) {
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
    public WorkGroup updateWorkGroup(WorkGroup workGroup) {
        final WorkGroup existingWorkGroup = getWorkGroupById(workGroup.getId());
        existingWorkGroup.setName(workGroup.getName());
        existingWorkGroup.setShortName(workGroup.getShortName());
        existingWorkGroup.setDescription(workGroup.getDescription());
        return workGroupRepository.save(existingWorkGroup);
    }

    @Override
    public void deleteWorkGroup(Long workGroupId) {
        workGroupRepository.deleteById(workGroupId);
    }

}
