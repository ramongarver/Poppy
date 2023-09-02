package com.ramongarver.poppy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupCreateDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupReadDto;
import com.ramongarver.poppy.api.dto.workgroup.WorkGroupUpdateDto;
import com.ramongarver.poppy.api.entity.WorkGroup;
import com.ramongarver.poppy.api.mapper.WorkGroupMapper;
import com.ramongarver.poppy.api.service.JwtService;
import com.ramongarver.poppy.api.service.WorkGroupService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkGroupController.class)
class WorkGroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private WorkGroupService workGroupService;

    @MockBean
    private WorkGroupMapper workGroupMapper;

    // Register the JavaTimeModule to allow ObjectMapper handling Java 8 date/time types
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Mock Data
    private final WorkGroup mockWorkGroup = WorkGroup.builder()
            .id(1L)
            .name("WorkGroupName")
            .shortName("WorkGroupShortName")
            .description("WorkGroupDescription")
            .volunteers(List.of())
            .build();
    private final WorkGroupReadDto mockWorkGroupReadDto = WorkGroupReadDto.builder()
            .id(1L)
            .name("WorkGroupName")
            .shortName("WorkGroupShortName")
            .description("WorkGroupDescription")
            .volunteerIds(List.of())
            .build();

    @BeforeEach
    void setUp() {
        // Configuring the common mocks
        when(workGroupMapper.toReadDto(any(WorkGroup.class))).thenReturn(mockWorkGroupReadDto);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser
    void testGetWorkGroupById() throws Exception {
        when(workGroupService.getWorkGroupById(anyLong())).thenReturn(mockWorkGroup);

        mockMvc.perform(
                        get(ControllerConstants.WORKGROUPS_ROUTE + "/{workGroupId}", 1)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockWorkGroup.getId().intValue())))
                .andExpect(jsonPath("$.name", is(mockWorkGroup.getName())))
                .andExpect(jsonPath("$.shortName", is(mockWorkGroup.getShortName())))
                .andExpect(jsonPath("$.description", is(mockWorkGroup.getDescription())))
                .andExpect(jsonPath("$.volunteerIds", is(mockWorkGroup.getVolunteers())));
    }

    @Test
    @WithMockUser
    void testGetAllWorkGroups() throws Exception {
        final List<WorkGroup> workGroups = List.of(mockWorkGroup);
        final List<WorkGroupReadDto> workGroupReadDto = List.of(mockWorkGroupReadDto);

        when(workGroupService.getAllWorkGroups()).thenReturn(workGroups);
        when(workGroupMapper.toListReadDto(workGroups)).thenReturn(workGroupReadDto);

        mockMvc.perform(
                        get(ControllerConstants.WORKGROUPS_ROUTE)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(mockWorkGroup.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(mockWorkGroup.getName())))
                .andExpect(jsonPath("$[0].shortName", is(mockWorkGroup.getShortName())))
                .andExpect(jsonPath("$[0].description", is(mockWorkGroup.getDescription())))
                .andExpect(jsonPath("$[0].volunteerIds", is(mockWorkGroup.getVolunteers())));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testCreateWorkGroup() throws Exception {
        when(workGroupService.createWorkGroup(any(WorkGroupCreateDto.class))).thenReturn(mockWorkGroup);

        mockMvc.perform(
                        post(ControllerConstants.WORKGROUPS_ROUTE)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockWorkGroup)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(mockWorkGroup.getId().intValue())))
                .andExpect(jsonPath("$.name", is(mockWorkGroup.getName())))
                .andExpect(jsonPath("$.shortName", is(mockWorkGroup.getShortName())))
                .andExpect(jsonPath("$.description", is(mockWorkGroup.getDescription())))
                .andExpect(jsonPath("$.volunteerIds", is(mockWorkGroup.getVolunteers())));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testUpdateWorkGroup() throws Exception {
        when(workGroupService.updateWorkGroup(any(Long.class), any(WorkGroupUpdateDto.class))).thenReturn(mockWorkGroup);

        mockMvc.perform(
                        put(ControllerConstants.WORKGROUPS_ROUTE + "/{workGroupId}", 1)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockWorkGroup)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockWorkGroup.getId().intValue())))
                .andExpect(jsonPath("$.name", is(mockWorkGroup.getName())))
                .andExpect(jsonPath("$.shortName", is(mockWorkGroup.getShortName())))
                .andExpect(jsonPath("$.description", is(mockWorkGroup.getDescription())))
                .andExpect(jsonPath("$.volunteerIds", is(mockWorkGroup.getVolunteers())));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void testDeleteWorkGroup() throws Exception {
        doNothing().when(workGroupService).deleteWorkGroup(anyLong());

        mockMvc.perform(
                        delete(ControllerConstants.WORKGROUPS_ROUTE + "/{id}", 1)
                                .with(csrf()))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Work group successfully deleted!"));
    }

    @Test
    void testUnauthenticated() throws Exception {
        mockMvc.perform(get(ControllerConstants.WORKGROUPS_ROUTE + "/{workGroupId}", 1))
                .andExpect(status().isUnauthorized());
    }

}
