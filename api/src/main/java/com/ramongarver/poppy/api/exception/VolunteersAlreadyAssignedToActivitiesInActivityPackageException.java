package com.ramongarver.poppy.api.exception;

public class VolunteersAlreadyAssignedToActivitiesInActivityPackageException extends RuntimeException {
    public VolunteersAlreadyAssignedToActivitiesInActivityPackageException(Long activityPackageId) {
        super(String.format("Volunteers have already been assigned to activities in the ActivityPackage with id=%d", activityPackageId));
    }
}

