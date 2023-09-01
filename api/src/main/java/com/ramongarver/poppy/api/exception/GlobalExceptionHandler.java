package com.ramongarver.poppy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<Object> handleBadRequestExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoAdminRoleException.class, IncorrectOldPasswordException.class})
    public ResponseEntity<Object> handleForbiddenExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmailExistsException.class, VolunteerAlreadyAssignedException.class, VolunteerNotAssignedException.class,
            ResourceAlreadyExistsException.class, ActivityNotInActivityPackageException.class,
            OutVolunteerAvailabilitySubmissionPeriodException.class, AdminSelfRoleChangeException.class,
            VolunteersAlreadyAssignedToActivitiesInActivityPackageException.class})
    public ResponseEntity<Object> handleConflictExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
