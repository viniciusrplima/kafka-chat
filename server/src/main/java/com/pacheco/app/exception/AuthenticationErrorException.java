package com.pacheco.app.exception;

public class AuthenticationErrorException extends BusinessException {
    public AuthenticationErrorException(String message) {
        super(message);
    }
}
