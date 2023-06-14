package com.ramongarver.poppy.api.service;

import com.ramongarver.poppy.api.entity.Volunteer;

import java.util.List;

public interface VolunteerService {

    Volunteer createVolunteer(Volunteer volunteer);

    Volunteer getVolunteerById(Long volunteerId);

    Volunteer getVolunteerByEmail(String email);

    List<Volunteer> getVolunteersByIds(List<Long> volunteersId);

    List<Volunteer> getAllVolunteers();

    Volunteer updateVolunteer(Volunteer volunteer);

    void deleteVolunteer(Long volunteerId);

    boolean doesEmailExist(String email);

}
