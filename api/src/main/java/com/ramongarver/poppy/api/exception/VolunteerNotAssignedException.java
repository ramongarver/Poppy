package com.ramongarver.poppy.api.exception;

import com.ramongarver.poppy.api.entity.Activity;
import com.ramongarver.poppy.api.entity.Volunteer;

public class VolunteerNotAssignedException extends RuntimeException {
    public VolunteerNotAssignedException(Volunteer volunteer, Activity activity) {
        super(
                String.format(
                        "Volunteer %d (%s) is not assigned to Activity %d (%s)",
                        volunteer.getId(),
                        volunteer.getFirstName() + " " + volunteer.getLastName(),
                        activity.getId(),
                        activity.getName()
                )
        );
    }
}
