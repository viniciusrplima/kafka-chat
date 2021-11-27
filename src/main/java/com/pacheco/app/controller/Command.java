package com.pacheco.app.controller;

public interface Command {

    public void changePageTo(String pageName);

    public void setUsername(String username);

    public String getUsername();

}
