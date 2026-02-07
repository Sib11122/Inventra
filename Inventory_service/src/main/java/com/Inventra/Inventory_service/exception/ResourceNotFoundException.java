package com.Inventra.Inventory_service.exception;


public class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

