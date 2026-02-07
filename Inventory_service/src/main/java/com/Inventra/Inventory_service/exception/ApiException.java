package com.Inventra.Inventory_service.exception;


public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}

