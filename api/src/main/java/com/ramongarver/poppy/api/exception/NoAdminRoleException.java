package com.ramongarver.poppy.api.exception;

public class NoAdminRoleException extends RuntimeException {
    public NoAdminRoleException() {
        super("You cannot modify the role because you are not an admin");
    }
}
