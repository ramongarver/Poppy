package com.ramongarver.poppy.api.exception;

import java.util.Map;

public class ActivityNotAssignedToActivityPackageException extends RuntimeException {
    public ActivityNotAssignedToActivityPackageException(Map<Long, Long> activityAndPackageIds) {
        super(
                String.format(
                        "Activities not assigned to the activity package (activityId=activityPackageId): %s",
                        activityAndPackageIds.toString()
                )
        );
    }
}
