package com.pacheco.app.model;

import lombok.Data;

@Data
public class AppState {

    private String token;

    private String username;

    private ChatDTO chat;

}
