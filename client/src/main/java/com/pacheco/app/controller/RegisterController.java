package com.pacheco.app.controller;

import com.pacheco.app.service.ApiErrorException;
import com.pacheco.app.service.ChatServer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Data;


@Data
public class RegisterController extends AbstractController {

    private static final int USERNAME_MIN_SIZE = 4;
    private static final int USERNAME_MAX_SIZE = 20;
    private static final int PASSWORD_MIN_SIZE = 4;
    private static final int PASSWORD_MAX_SIZE = 20;

    @FXML
    private Label alert;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    private ChatServer server;

    public void setup() {
        this.server = new ChatServer(getCommand().getState().getChatServerUrl());
    }

    public void submit() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            setAlert("Username can't be empty");
        }
        else if (username.length() < USERNAME_MIN_SIZE) {
            setAlert("Username size must be larger than " + USERNAME_MIN_SIZE + " characters");
        }
        else if (username.length() > USERNAME_MAX_SIZE) {
            setAlert("Username size must be smaller than " + USERNAME_MAX_SIZE + " characters");
        }
        else if (password.isEmpty()) {
            setAlert("Password can't be empty");
        }
        else if (username.length() < PASSWORD_MIN_SIZE) {
            setAlert("Password size must be larger than " + PASSWORD_MIN_SIZE + " characters");
        }
        else if (username.length() > PASSWORD_MAX_SIZE) {
            setAlert("Password size must be smaller than " + PASSWORD_MAX_SIZE + " characters");
        }
        else {
            doSubmit(username, password);
        }
    }

    public void doSubmit(String username, String password) {
        try {
            this.server.register(username, password);
            changePageTo("login-page");
        }
        catch (ApiErrorException e) {
            setAlert(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlert(String message) {
        alert.setVisible(true);
        alert.setText(message);
    }

}
