package com.ramongarver.poppy.api.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super("Email address " + email + " already exists");
    }

}
