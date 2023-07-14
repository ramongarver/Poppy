package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.WorkGroup;

import java.util.List;

public interface WorkGroupService {

    WorkGroup createWorkGroup(WorkGroupCreateDto workGroupCreateDto);

    WorkGroup getWorkGroupById(Long workGroupId);

    List<WorkGroup> getWorkGroupsByIds(List<Long> workGroupsIds);

    List<WorkGroup> getAllWorkGroups();

    WorkGroup updateWorkGroup(Long workgroupId, WorkGroupUpdateDto workGroupUpdateDto);

    void deleteWorkGroup(Long workGroupId);

}
