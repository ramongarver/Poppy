package com.ramongarver.poppy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.user.password.PasswordChangeDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReadDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.enums.Role;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.service.JwtService;
import com.ramongarver.poppy.api.service.VolunteerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VolunteerController.class)
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private VolunteerService volunteerService;

    @MockBean
    private VolunteerMapper volunteerMapper;

    @MockBean
    private ActivityMapper activityMapper;

    // Register the JavaTimeModule to allow ObjectMapper handling Java 8 date/time types
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Mock Data
    private final Volunteer mockVolunteer = Volunteer.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .email("email@poppy-test.com")
            .password("test")
            .role(Role.USER)
            .startDate(LocalDate.of(2023, 2, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .workGroups(List.of())
            .activities(List.of())
            .build();
    private final VolunteerReadDto mockVolunteerReadDto = VolunteerReadDto.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .email("email@poppy-test.com")
            .role(Role.USER)
            .startDate(LocalDate.of(2023, 2, 1))
            .endDate(LocalDate.of(2023, 12, 31))
            .workGroupIds(List.of())
            .activityIds(List.of())
            .build();
    private final Activity mockActivity = Activity.builder()
            .id(1L)
            .name("ActivityName")
            .description("ActivityDescription")
            .localDateTime(LocalDateTime.of(2023, 2, 10, 19, 30, 0))
            .place("ActivityPlace")
            .volunteers(List.of())
            .build();
    private final ActivityReadDto mockActivityReadDto = ActivityReadDto.builder()
            .id(1L)
            .name("ActivityName")
            .description("ActivityDescription")
            .localDateTime(LocalDateTime.of(2023, 2, 10, 19, 30, 0))
            .place("ActivityPlace")
            .volunteerIds(List.of())
            .build();


    @BeforeEach
    void setUp() {
        // Configuring the common mocks
        when(volunteerMapper.toReadDto(any(Volunteer.class))).thenReturn(mockVolunteerReadDto);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser
    void testGetVolunteerById() throws Exception {
        when(volunteerService.getVolunteerById(anyLong())).thenReturn(mockVolunteer);

        mockMvc.perform(
                        get(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockVolunteer.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(mockVolunteer.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(mockVolunteer.getLastName())))
                .andExpect(jsonPath("$.email", is(mockVolunteer.getEmail())))
                .andExpect(jsonPath("$.role", is(mockVolunteer.getRole().toString())))
                .andExpect(jsonPath("$.startDate", is(mockVolunteer.getStartDate().toString())))
                .andExpect(jsonPath("$.endDate", is(mockVolunteer.getEndDate().toString())));
    }

    @Test
    @WithMockUser
    void testGetAllVolunteers() throws Exception {
        final List<Volunteer> volunteers = List.of(mockVolunteer);
        final List<VolunteerReadDto> volunteersDto = List.of(mockVolunteerReadDto);

        when(volunteerService.getAllVolunteers()).thenReturn(List.of(mockVolunteer));
        when(volunteerMapper.toListReadDto(volunteers)).thenReturn(volunteersDto);

        mockMvc.perform(get(ControllerConstants.VOLUNTEERS_ROUTE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(mockVolunteer.getId().intValue())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateVolunteer() throws Exception {
        when(volunteerService.createVolunteer(any())).thenReturn(mockVolunteer);

        mockMvc.perform(post(ControllerConstants.VOLUNTEERS_ROUTE)
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockVolunteer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockUser
    void testUpdateVolunteer() throws Exception {
        when(volunteerService.updateVolunteer(anyLong(), any())).thenReturn(mockVolunteer);

        mockMvc.perform(put(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}", 1)
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(mockVolunteer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockVolunteer.getId().intValue())));
    }

    @Test
    @WithMockUser
    void testChangePassword() throws Exception {
        doNothing().when(volunteerService).changePassword(anyLong(), any());

        mockMvc.perform(patch(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}"
                        + ControllerConstants.VOLUNTEERS_RESOURCE_PASSWORD
                        + ControllerConstants.CHANGE_ACTION, 1)
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PasswordChangeDto.builder().build())))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully changed!"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testResetPassword() throws Exception {
        doNothing().when(volunteerService).resetPassword(anyLong(), any());

        mockMvc.perform(patch(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}"
                        + ControllerConstants.VOLUNTEERS_RESOURCE_PASSWORD
                        + ControllerConstants.RESET_ACTION, 1)
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(PasswordChangeDto.builder().build())))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully reset!"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteVolunteer() throws Exception {
        doNothing().when(volunteerService).deleteVolunteer(anyLong());

        mockMvc.perform(delete(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}", 1)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetVolunteerActivities() throws Exception {
        when(volunteerService.getVolunteerById(anyLong())).thenReturn(mockVolunteer);
        when(activityMapper.toListReadDto(any())).thenReturn(List.of(mockActivityReadDto));

        mockMvc.perform(get(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}" + ControllerConstants.ACTIVITIES_RESOURCE, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void testGetVolunteerActivities_ShowOnlyFuture() throws Exception {
        when(volunteerService.getVolunteerById(anyLong())).thenReturn(mockVolunteer);
        when(activityMapper.toListReadDto(any())).thenReturn(List.of(mockActivityReadDto));

        mockMvc.perform(
                        get(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}" + ControllerConstants.ACTIVITIES_RESOURCE, 1)
                                .param("showOnlyFutureActivities", "true")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testUnauthenticated() throws Exception {
        mockMvc.perform(get(ControllerConstants.VOLUNTEERS_ROUTE + "/{volunteerId}", 1))
                .andExpect(status().isUnauthorized());
    }

}
