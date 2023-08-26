package com.ramongarver.poppy.api.exception;

public class AdminSelfRoleChangeException extends RuntimeException {
    public AdminSelfRoleChangeException() {
        super("Even though you are an admin, your role must be modified by another user");
    }
}

