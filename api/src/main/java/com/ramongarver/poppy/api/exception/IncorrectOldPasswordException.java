package com.ramongarver.poppy.api.exception;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException() {
        super("The provided old password is incorrect");
    }
}