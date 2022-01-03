package com.pacheco.app.exception;

public class ChatNotFoundException extends BusinessException {

    public ChatNotFoundException(Long id) {
        super(String.format("Chat with id %d not found", id));
    }

}
