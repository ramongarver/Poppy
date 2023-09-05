package com.ramongarver.poppy.api.exception;

import java.util.Map;

public class ActivityAlreadyAssignedToActivityPackageException extends RuntimeException {
    public ActivityAlreadyAssignedToActivityPackageException(Map<Long, Long> activityAndPackageIds) {
        super(
                String.format(
                        "Activities already assigned to your activity packages (activityId=activityPackageId):  %s",
                        activityAndPackageIds.toString()
                )
        );
    }
}