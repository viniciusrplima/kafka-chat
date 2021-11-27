package com.pacheco.app.controller;

import lombok.Data;

@Data
public abstract class AbstractController implements Controller {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void changePageTo(String pageName) {
        this.command.changePageTo(pageName);
    }

    public void setup() {}

}
