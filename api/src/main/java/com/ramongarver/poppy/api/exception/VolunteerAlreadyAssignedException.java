package com.ramongarver.poppy.api.exception;

import com.ramongarver.poppy.api.entity.Volunteer;

public class VolunteerAlreadyAssignedException extends RuntimeException {
    public VolunteerAlreadyAssignedException(Volunteer volunteer, String entityName, Long entityId, String entityDisplayName) {
        super(
                String.format(
                        "Volunteer %d (%s) already assigned to %s %d (%s)",
                        volunteer.getId(),
                        volunteer.getFirstName() + " " + volunteer.getLastName(),
                        entityName,
                        entityId,
                        entityDisplayName
                )
        );
    }
}
