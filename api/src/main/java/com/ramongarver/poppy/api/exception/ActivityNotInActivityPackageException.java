package com.ramongarver.poppy.api.exception;

import java.util.List;

public class ActivityNotInActivityPackageException extends RuntimeException {
    public ActivityNotInActivityPackageException(Long activityPackageId, List<Long> missingActivityIds) {
        super(
                String.format(
                        "The activities with IDs: %s do not belong to the activity package with ID: %d",
                        missingActivityIds.toString(),
                        activityPackageId
                )
        );
    }
}
