package com.pacheco.app.service;

public class ApiErrorException extends RuntimeException {
    public ApiErrorException(String message) {
        super(message);
    }
}
