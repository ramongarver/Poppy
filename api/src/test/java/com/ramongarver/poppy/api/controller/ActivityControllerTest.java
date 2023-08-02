package com.ramongarver.poppy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ramongarver.poppy.api.dto.activity.ActivityCreateDto;
import com.ramongarver.poppy.api.dto.activity.ActivityReadDto;
import com.ramongarver.poppy.api.dto.activity.ActivityUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.mapper.ActivityMapper;
import com.ramongarver.poppy.api.service.ActivityService;
import com.ramongarver.poppy.api.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ActivityController.class)
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private ActivityMapper activityMapper;

    // Register the JavaTimeModule to allow ObjectMapper handling Java 8 date/time types
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Mock data
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
        when(activityMapper.toReadDto(any(Activity.class))).thenReturn(mockActivityReadDto);
        when(activityMapper.fromReadDto(any(ActivityReadDto.class))).thenReturn(mockActivity);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser
    void testGetActivityById() throws Exception {
        when(activityService.getActivityById(anyLong())).thenReturn(mockActivity);

        mockMvc.perform(
                        get(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(mockActivity.getName())))
                .andExpect(jsonPath("$.description", is(mockActivity.getDescription())))
                .andExpect(jsonPath("$.localDateTime", is("2023-02-10T19:30:00")))
                .andExpect(jsonPath("$.volunteerIds", is(mockActivity.getVolunteers())));
    }

    @Test
    void testGetActivityById_unauthenticated() throws Exception {
        mockMvc.perform(get(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetAllActivities() throws Exception {
        List<Activity> activities = List.of(mockActivity);
        List<ActivityReadDto> activitiesDto = List.of(mockActivityReadDto);

        when(activityService.getAllActivities()).thenReturn(activities);
        when(activityMapper.toListReadDto(activities)).thenReturn(activitiesDto);

        mockMvc.perform(
                        get(ControllerConstants.ACTIVITIES_ROUTE)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(mockActivity.getName())))
                .andExpect(jsonPath("$[0].description", is(mockActivity.getDescription())))
                .andExpect(jsonPath("$[0].localDateTime", is("2023-02-10T19:30:00")))
                .andExpect(jsonPath("$[0].volunteerIds", is(mockActivity.getVolunteers())));
    }

    @Test
    void testGetAllActivities_unauthenticated() throws Exception {
        mockMvc.perform(get(ControllerConstants.ACTIVITIES_ROUTE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testCreateActivity() throws Exception {
        when(activityService.createActivity(any(ActivityCreateDto.class))).thenReturn(mockActivity);

        mockMvc.perform(
                        post(ControllerConstants.ACTIVITIES_ROUTE)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockActivity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(mockActivity.getName())))
                .andExpect(jsonPath("$.description", is(mockActivity.getDescription())))
                .andExpect(jsonPath("$.localDateTime", is("2023-02-10T19:30:00")))
                .andExpect(jsonPath("$.volunteerIds", is(mockActivity.getVolunteers())));
    }

    @Test
    void testCreateActivity_unauthenticated() throws Exception {
        mockMvc.perform(post(ControllerConstants.ACTIVITIES_ROUTE)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockActivity)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testUpdateActivity() throws Exception {
        when(activityService.updateActivity(any(Long.class), any(ActivityUpdateDto.class))).thenReturn(mockActivity);

        mockMvc.perform(
                        put(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mockActivity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(mockActivity.getName())))
                .andExpect(jsonPath("$.description", is(mockActivity.getDescription())))
                .andExpect(jsonPath("$.localDateTime", is("2023-02-10T19:30:00")))
                .andExpect(jsonPath("$.volunteerIds", is(mockActivity.getVolunteers())));
    }

    @Test
    void testUpdateActivity_unauthenticated() throws Exception {
        mockMvc.perform(put(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockActivity)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivity(anyLong());

        mockMvc.perform(
                        delete(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1)
                                .with(csrf()))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Activity successfully deleted!"));
    }

    @Test
    void testDeleteActivity_unauthenticated() throws Exception {
        mockMvc.perform(delete(ControllerConstants.ACTIVITIES_ROUTE + "/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

}
