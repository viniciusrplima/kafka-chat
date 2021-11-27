package com.pacheco.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Data;

@Data
public class LoginController extends AbstractController {

    @FXML
    private Label alert;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    public void submit() {
        getCommand().setUsername(usernameField.getText());
        changePageTo("chat-page");
    }

    public void toRegisterPage() {
        changePageTo("register-page");
    }

}
