package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.dto.user.password.PasswordChangeDto;
import com.ramongarver.poppy.api.dto.user.password.PasswordResetDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;

import java.util.List;

public interface VolunteerService {

    Volunteer createVolunteer(VolunteerCreateDto volunteerCreateDto);

    Volunteer getVolunteerById(Long volunteerId);

    Volunteer getVolunteerByEmail(String email);

    List<Volunteer> getVolunteersByIds(List<Long> volunteersId);

    List<Volunteer> getAllVolunteers();

    Volunteer updateVolunteer(Long volunteerId, VolunteerUpdateDto volunteerUpdateDto);

    void deleteVolunteer(Long volunteerId);

    void verifyVolunteerExists(Long volunteerId);

    boolean doesEmailExist(String email);

    void changePassword(Long volunteerId, PasswordChangeDto passwordChangeDto);

    void resetPassword(Long volunteerId, PasswordResetDto passwordResetDto);

}
