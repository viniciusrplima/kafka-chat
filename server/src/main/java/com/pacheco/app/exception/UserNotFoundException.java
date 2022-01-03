package com.pacheco.app.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long id) {
        super(String.format("User with id %d not found", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with username %s not found", username));
    }
}
