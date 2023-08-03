package com.ramongarver.poppy.api.controller;

import com.ramongarver.poppy.api.dto.volunteer.VolunteerIdsDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupReadDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.mapper.WorkGroupMapper;
import com.ramongarver.poppy.api.service.WorkGroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstants.WORKGROUPS_ROUTE)
public class WorkGroupController {

    private final WorkGroupMapper workGroupMapper;

    private final WorkGroupService workGroupService;

    @GetMapping("{id}")
    public ResponseEntity<WorkGroupReadDto> getWorkGroupById(@PathVariable("id") Long workGroupId) {
        final WorkGroup workGroup = workGroupService.getWorkGroupById(workGroupId);
        return new ResponseEntity<>(workGroupMapper.toReadDto(workGroup), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WorkGroupReadDto>> getAllWorkGroups() {
        final List<WorkGroup> workGroups = workGroupService.getAllWorkGroups();
        return new ResponseEntity<>(workGroupMapper.toListReadDto(workGroups), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<WorkGroupReadDto> createWorkGroup(@Valid @RequestBody WorkGroupCreateDto workGroupCreateDto) {
        final WorkGroup savedWorkGroup = workGroupService.createWorkGroup(workGroupCreateDto);
        return new ResponseEntity<>(workGroupMapper.toReadDto(savedWorkGroup), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PutMapping("{id}")
    public ResponseEntity<WorkGroupReadDto> updateWorkGroup(@PathVariable("id") Long workGroupId,
                                                            @RequestBody WorkGroupUpdateDto workGroupUpdateDto) {
        final WorkGroup updatedWorkGroup = workGroupService.updateWorkGroup(workGroupId, workGroupUpdateDto);
        return new ResponseEntity<>(workGroupMapper.toReadDto(updatedWorkGroup), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWorkGroup(@PathVariable("id") Long workGroupId) {
        workGroupService.deleteWorkGroup(workGroupId);
        return new ResponseEntity<>("Work group successfully deleted!", HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{workGroupId}" + ControllerConstants.VOLUNTEERS_RESOURCE + "/{volunteerId}")
    public ResponseEntity<Void> assignVolunteerToWorkGroup(@PathVariable("workGroupId") Long workGroupId,
                                                           @PathVariable("volunteerId") Long volunteerId) {
        workGroupService.assignVolunteerToWorkGroup(workGroupId, volunteerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{workGroupId}" + ControllerConstants.VOLUNTEERS_RESOURCE + "/{volunteerId}")
    public ResponseEntity<Void> removeVolunteerFromWorkGroup(@PathVariable("workGroupId") Long workGroupId,
                                                             @PathVariable("volunteerId") Long volunteerId) {
        workGroupService.removeVolunteerFromWorkGroup(workGroupId, volunteerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @PostMapping("{workGroupId}" + ControllerConstants.VOLUNTEERS_RESOURCE)
    public ResponseEntity<Void> assignVolunteersToWorkGroup(@PathVariable("workGroupId") Long workGroupId,
                                                            @RequestBody VolunteerIdsDto volunteerIdsDto) {
        workGroupService.assignVolunteersToWorkGroup(workGroupId, volunteerIdsDto.getVolunteerIds());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('MANAGER')")
    @DeleteMapping("{workGroupId}" + ControllerConstants.VOLUNTEERS_RESOURCE)
    public ResponseEntity<Void> removeVolunteersFromWorkGroup(@PathVariable("workGroupId") Long workGroupId,
                                                              @RequestBody VolunteerIdsDto volunteerIdsDto) {
        workGroupService.removeVolunteersFromWorkGroup(workGroupId, volunteerIdsDto.getVolunteerIds());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
