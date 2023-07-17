package com.ramongarver.poppy.api.exception;

import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;

public class VolunteerAlreadyAssignedException extends RuntimeException {
    public VolunteerAlreadyAssignedException(Volunteer volunteer, Activity activity) {
        super(
                String.format(
                        "Volunteer %d (%s) already assigned to Activity %d (%s)",
                        volunteer.getId(),
                        volunteer.getFirstName() + " " + volunteer.getLastName(),
                        activity.getId(),
                        activity.getName()
                )
        );
    }
}
