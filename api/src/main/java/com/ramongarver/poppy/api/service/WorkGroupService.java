package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.entity.WorkGroup;

import java.util.List;

public interface WorkGroupService {

    WorkGroup createWorkGroup(WorkGroup workGroup);

    WorkGroup getWorkGroupById(Long workGroupId);

    List<WorkGroup> getWorkGroupsByIds(List<Long> workGroupsIds);

    List<WorkGroup> getAllWorkGroups();

    WorkGroup updateWorkGroup(WorkGroup workGroup);

    void deleteWorkGroup(Long workGroupId);

}
