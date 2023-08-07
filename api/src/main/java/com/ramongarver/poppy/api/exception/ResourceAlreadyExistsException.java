package com.ramongarver.poppy.api.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("Resource %s already exists for %s with value %s", resourceName, fieldName, fieldValue));
    }
}
