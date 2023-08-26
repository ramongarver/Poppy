package com.ramongarver.poppy.api.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String email) {
        super(String.format("Email address %s already exists", email));
    }

}
