package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.exception.EmailExistsException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.repository.VolunteerRepository;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {
    private final VolunteerRepository volunteerRepository;

    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        if (doesEmailExist(volunteer.getEmail())) {
            throw new EmailExistsException(volunteer.getEmail());
        }

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
    public List<Volunteer> getVolunteersByIds(List<Long> volunteersIds) {
        return volunteerRepository.findByIdIn(volunteersIds != null ? volunteersIds : List.of());
    }

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        final Volunteer existingVolunteer = getVolunteerById(volunteer.getId());
        existingVolunteer.setFirstName(volunteer.getFirstName());
        existingVolunteer.setLastName(volunteer.getLastName());
        existingVolunteer.setEmail(volunteer.getEmail());
        existingVolunteer.setPassword(volunteer.getPassword());
        existingVolunteer.setRole(volunteer.getRole());
        existingVolunteer.setStartDate(volunteer.getStartDate());
        existingVolunteer.setEndDate(volunteer.getEndDate());
        return existingVolunteer;
    }

    @Override
    public void deleteVolunteer(Long volunteerId) {
        volunteerRepository.deleteById(volunteerId);
    }

    @Override
    public boolean doesEmailExist(String email) {
        return volunteerRepository.findByEmail(email).isPresent();
    }

}
