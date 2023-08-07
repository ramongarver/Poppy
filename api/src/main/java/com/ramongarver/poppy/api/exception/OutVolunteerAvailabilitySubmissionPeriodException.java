package com.ramongarver.poppy.api.exception;

import java.time.LocalDate;

public class OutVolunteerAvailabilitySubmissionPeriodException extends RuntimeException {
    public OutVolunteerAvailabilitySubmissionPeriodException(Long activityPackageId, LocalDate startDate, LocalDate endDate) {
        super(String.format("We are outside the period for submitting/modifying volunteer availability for the activity " +
                "package with ID %d. The availability period is from %s to %s", activityPackageId, startDate, endDate));
    }
}

