package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.exception.EmailExistsException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.repository.VolunteerRepository;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerMapper volunteerMapper;

    private final VolunteerRepository volunteerRepository;

    @Override
    public Volunteer createVolunteer(VolunteerCreateDto volunteerCreateDto) {
        verifyEmailNotExists(volunteerCreateDto.getEmail());
        final Volunteer volunteer = volunteerMapper.fromCreateDto(volunteerCreateDto);
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer getVolunteerById(Long volunteerId) {
        return volunteerRepository.findById(volunteerId).orElseThrow(() -> new ResourceNotFoundException(Volunteer.class.getSimpleName(), "id", volunteerId));
    }

    @Override
    public Volunteer getVolunteerByEmail(String email) {
        return volunteerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(Volunteer.class.getSimpleName(), "email", email));
    }

    @Override
    public List<Volunteer> getVolunteersByIds(List<Long> volunteerIds) {
        return volunteerRepository.findByIdIn(volunteerIds != null ? volunteerIds : List.of());
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public Volunteer updateVolunteer(Long volunteerId, VolunteerUpdateDto volunteerUpdateDto) {
        final Volunteer existingVolunteer = getVolunteerById(volunteerId);
        volunteerMapper.fromUpdateDto(existingVolunteer, volunteerUpdateDto);
        return volunteerRepository.save(existingVolunteer);
    }

    @Override
    public void deleteVolunteer(Long volunteerId) {
        verifyVolunteerExists(volunteerId);
        volunteerRepository.deleteById(volunteerId);
    }

    @Override
    public void verifyVolunteerExists(Long volunteerId) {
        if (!volunteerRepository.existsById(volunteerId)) {
            throw new ResourceNotFoundException(Volunteer.class.getSimpleName(), "id", volunteerId);
        }
    }

    private void verifyEmailNotExists(String email) {
        if (doesEmailExist(email)) {
            throw new EmailExistsException(email);
        }
    }

    @Override
    public boolean doesEmailExist(String email) {
        return volunteerRepository.findByEmail(email).isPresent();
    }

}
