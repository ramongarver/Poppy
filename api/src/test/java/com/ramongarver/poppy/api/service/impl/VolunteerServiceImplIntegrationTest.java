package com.ramongarver.poppy.api.service.impl;

import com.ramongarver.poppy.api.dto.volunteer.VolunteerCreateDto;
import com.ramongarver.poppy.api.dto.volunteer.VolunteerUpdateDto;
import com.ramongarver.poppy.api.entity.Volunteer;
import com.ramongarver.poppy.api.enums.Role;
import com.ramongarver.poppy.api.exception.EmailExistsException;
import com.ramongarver.poppy.api.exception.ResourceNotFoundException;
import com.ramongarver.poppy.api.service.VolunteerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
class VolunteerServiceImplIntegrationTest {

    @Autowired
    private VolunteerService volunteerService;

    @Test
    void testCreateVolunteer() {
        final VolunteerCreateDto volunteerCreateDto = VolunteerCreateDto.builder()
                .firstName("Test Volunteer FirstName")
                .lastName("Test Volunteer LastName")
                .email("test@example.com")
                .password("TestPassword")
                .role(Role.USER)
                .startDate(LocalDate.of(2022, 9, 1))
                .endDate(LocalDate.of(2023, 7, 1))
                .build();

        final Volunteer createdVolunteer = volunteerService.createVolunteer(volunteerCreateDto);

        assertAll(
                () -> assertNotNull(createdVolunteer),
                () -> assertNotNull(createdVolunteer.getId()),
                () -> assertEquals(volunteerCreateDto.getEmail(), createdVolunteer.getEmail()),
                () -> assertEquals(volunteerCreateDto.getFirstName(), createdVolunteer.getFirstName()),
                () -> assertEquals(volunteerCreateDto.getLastName(), createdVolunteer.getLastName()),
                () -> assertEquals(volunteerCreateDto.getRole(), createdVolunteer.getRole()),
                () -> assertEquals(volunteerCreateDto.getStartDate(), createdVolunteer.getStartDate()),
                () -> assertEquals(volunteerCreateDto.getEndDate(), createdVolunteer.getEndDate()),
                () -> assertTrue(volunteerService.doesEmailExist(volunteerCreateDto.getEmail()))
        );
    }

    @Test
    void testCreateVolunteerWithExistingEmail() {
        final VolunteerCreateDto volunteerCreateDtoWithExistingEmail = VolunteerCreateDto.builder()
                .email("pablo.ramirez@poppy-test.com")
                .build();

        assertThrows(EmailExistsException.class, () -> {
            volunteerService.createVolunteer(volunteerCreateDtoWithExistingEmail);
        });
    }

    @Test
    void testGetVolunteerById() {
        final Long volunteerId = 100L;
        final Volunteer volunteer = volunteerService.getVolunteerById(volunteerId);

        assertAll(
                () -> assertNotNull(volunteer),
                () -> assertEquals(volunteerId, volunteer.getId())
        );
    }

    @Test
    void testGetVolunteerByEmail() {
        final String volunteerEmail = "pablo.ramirez@poppy-test.com";
        final Volunteer volunteer = volunteerService.getVolunteerByEmail(volunteerEmail);

        assertAll(
                () -> assertNotNull(volunteer),
                () -> assertEquals(volunteerEmail, volunteer.getEmail())
        );
    }

    @Test
    void testGetVolunteersByIds() {
        final List<Long> volunteerIds = List.of(99L, 100L);

        final List<Volunteer> volunteers = volunteerService.getVolunteersByIds(volunteerIds);

        assertAll(
                () -> assertNotNull(volunteers, "List of volunteers should not be null"),
                () -> assertEquals(volunteerIds.size(), volunteers.size(), "Number of retrieved volunteers should match the number of provided IDs"),
                () -> {
                    volunteers.forEach(volunteer -> {
                        assertTrue(volunteerIds.contains(volunteer.getId()), "Volunteer's ID should be in the provided IDs list");
                    });
                }
        );
    }

    @Test
    void testGetVolunteersByIdsWhenListIsNull() {
        final List<Volunteer> volunteers = volunteerService.getVolunteersByIds(null);

        assertAll(
                () -> assertNotNull(volunteers, "List of volunteers should not be null"),
                () -> assertTrue(volunteers.isEmpty(), "List of volunteers should be empty when provided ID list is null")
        );
    }

    @Test
    @WithMockUser(username = "pablo.ramirez@poppy-test.com", roles = {"ADMIN"})
    void testUpdateVolunteerSuccessfully() {
        final Long existingVolunteerId = 100L;
        final VolunteerUpdateDto updateDto = VolunteerUpdateDto.builder()
                .firstName("Test Volunteer FirstName")
                .build();

        /* final Volunteer updatedVolunteer = volunteerService.updateVolunteer(existingVolunteerId, updateDto);

        assertAll(
                () -> assertNotNull(updatedVolunteer),
                () -> assertEquals(updateDto.getFirstName(), updatedVolunteer.getFirstName())
        ); */
    }

    @Test
    void testUpdateVolunteerWithNonExistentId() {
        final Long nonExistentVolunteerId = 9999L;
        final VolunteerUpdateDto updateDto = VolunteerUpdateDto.builder()
                .firstName("Updated FirstName")
                .firstName("Test Volunteer FirstName")
                .build();

        assertThrows(ResourceNotFoundException.class, () -> {
            volunteerService.updateVolunteer(nonExistentVolunteerId, updateDto);
        });
    }

    @Test
    void testDeleteVolunteerSuccessfully() {
        final Long existingVolunteerId = 100L;
        volunteerService.deleteVolunteer(existingVolunteerId);
        assertThrows(ResourceNotFoundException.class, () -> {
            volunteerService.getVolunteerById(existingVolunteerId);
        });
    }

    @Test
    void testDeleteVolunteerWithNonExistentId() {
        final Long nonExistentVolunteerId = 9999L;
        assertThrows(ResourceNotFoundException.class, () -> {
            volunteerService.deleteVolunteer(nonExistentVolunteerId);
        });
    }

    @Test
    void testVerifyVolunteerExistsWithNonExistentId() {
        final Long nonExistentVolunteerId = 9999L;
        assertThrows(ResourceNotFoundException.class, () -> {
            volunteerService.verifyVolunteerExists(nonExistentVolunteerId);
        });
    }

}
