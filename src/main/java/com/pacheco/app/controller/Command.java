package com.pacheco.app.controller;

import com.pacheco.app.model.AppState;

public interface Command {

    public void changePageTo(String pageName);

    public void setUsername(String username);

    public String getUsername();

    public void setToken(String token);

    public String getToken();

    public AppState getState();

}
