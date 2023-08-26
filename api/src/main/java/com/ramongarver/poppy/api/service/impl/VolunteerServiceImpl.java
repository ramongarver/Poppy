package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.constant.SecurityConstants;
import com.ramongarver.poppy.api.dto.user.password.PasswordChangeDto;
import com.ramongarver.poppy.api.dto.user.password.PasswordResetDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.enums.Role;
import com.ramongarver.poppy.api.exception.AdminSelfRoleChangeException;
import com.ramongarver.poppy.api.exception.EmailExistsException;
import com.ramongarver.poppy.api.exception.IncorrectOldPasswordException;
import com.ramongarver.poppy.api.exception.NoAdminRoleException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.exception.WeakPasswordException;
import com.ramongarver.poppy.api.mapper.VolunteerMapper;
import com.ramongarver.poppy.api.repository.VolunteerRepository;
import com.ramongarver.poppy.api.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final PasswordEncoder passwordEncoder;

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
        verifyRoleUpdatePermission(volunteerId, existingVolunteer.getRole(), volunteerUpdateDto.getRole());
        volunteerMapper.fromUpdateDto(existingVolunteer, volunteerUpdateDto);
        return volunteerRepository.save(existingVolunteer);
    }

    @Override
    public void changePassword(Long volunteerId, PasswordChangeDto passwordChangeDto) {
        final Volunteer existingVolunteer = getVolunteerById(volunteerId);
        verifyOldPasswordIsCorrect(existingVolunteer, passwordChangeDto.getOldPassword());
        verifyNewPasswordIsValid(passwordChangeDto.getNewPassword());
        existingVolunteer.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        volunteerRepository.save(existingVolunteer);
    }

    @Override
    public void resetPassword(Long volunteerId, PasswordResetDto passwordResetDto) {
        final Volunteer existingVolunteer = getVolunteerById(volunteerId);
        verifyNewPasswordIsValid(passwordResetDto.getNewPassword());
        existingVolunteer.setPassword(passwordEncoder.encode(passwordResetDto.getNewPassword()));
        volunteerRepository.save(existingVolunteer);
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

    private void verifyRoleUpdatePermission(Long targetVolunteerId, Role existingRole, Role newRole) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Volunteer authenticatedVolunteer = (Volunteer) authentication.getPrincipal();
        final Long authenticatedVolunteerId = authenticatedVolunteer.getId();
        final Role authenticatedVolunteerRole = authenticatedVolunteer.getRole();

        if (existingRole.equals(newRole)) {
            return;
        }

        if (Role.ADMIN.equals(authenticatedVolunteerRole)) {
            if (targetVolunteerId.equals(authenticatedVolunteerId)) {
                throw new AdminSelfRoleChangeException();
            }
        } else {
            throw new NoAdminRoleException();
        }
    }

    private void verifyOldPasswordIsCorrect(Volunteer volunteer, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, volunteer.getPassword())) {
            throw new IncorrectOldPasswordException();
        }
    }

    private void verifyNewPasswordIsValid(String newPassword) {
        if (newPassword.length() < SecurityConstants.MIN_PASSWORD_LENGTH) {
            throw new WeakPasswordException();
        }
    }

}
