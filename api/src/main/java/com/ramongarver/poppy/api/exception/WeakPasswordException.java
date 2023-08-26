package com.ramongarver.poppy.api.exception;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException() {
        super("The new password is too weak");
    }
}