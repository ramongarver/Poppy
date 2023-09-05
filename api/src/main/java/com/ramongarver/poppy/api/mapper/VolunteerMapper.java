package com.ramongarver.poppy.api.mapper;

import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReadDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerReducedReadDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.entity.WorkGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class VolunteerMapper {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public VolunteerReadDto toReadDto(Volunteer volunteer) {
        return VolunteerReadDto.builder()
                .id(volunteer.getId())
                .firstName(volunteer.getFirstName())
                .lastName(volunteer.getLastName())
                .email(volunteer.getEmail())
                .role(volunteer.getRole())
                .startDate(volunteer.getStartDate())
                .endDate(volunteer.getEndDate())
                .workGroupIds(volunteer.getWorkGroups() != null
                        ? volunteer.getWorkGroups().stream().map(WorkGroup::getId).toList() : List.of())
                .activityIds(volunteer.getActivities() != null
                        ? volunteer.getActivities().stream().map(Activity::getId).toList() : List.of())
                .build();
    }

    public List<VolunteerReadDto> toListReadDto(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(this::toReadDto)
                .toList();
    }

    public Volunteer fromCreateDto(VolunteerCreateDto volunteerCreateDto) {
        return Volunteer.builder()
                .firstName(volunteerCreateDto.getFirstName())
                .lastName(volunteerCreateDto.getLastName())
                .email(volunteerCreateDto.getEmail())
                .password(passwordEncoder.encode(volunteerCreateDto.getPassword()))
                .role(volunteerCreateDto.getRole())
                .startDate(volunteerCreateDto.getStartDate())
                .endDate(volunteerCreateDto.getEndDate())
                .build();
    }

    public void fromUpdateDto(Volunteer existingVolunteer, VolunteerUpdateDto volunteerUpdateDto) {
        userMapper.fromUpdateDto(existingVolunteer, volunteerUpdateDto);
        existingVolunteer.setStartDate(volunteerUpdateDto.getStartDate() != null
                ? volunteerUpdateDto.getStartDate() : existingVolunteer.getStartDate());
        existingVolunteer.setEndDate(volunteerUpdateDto.getEndDate() != null
                ? volunteerUpdateDto.getEndDate() : existingVolunteer.getEndDate());
    }

    public VolunteerReducedReadDto toReducedReadDto(Volunteer volunteer) {
        return VolunteerReducedReadDto.builder()
                .id(volunteer.getId())
                .firstName(volunteer.getFirstName())
                .lastName(volunteer.getLastName())
                .build();
    }

    public List<VolunteerReducedReadDto> toListReducedReadDto(List<Volunteer> volunteers) {
        return volunteers.stream()
                .map(this::toReducedReadDto)
                .toList();
    }

}
