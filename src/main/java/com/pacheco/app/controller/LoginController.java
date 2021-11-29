package com.pacheco.app.controller;

import com.pacheco.app.service.ApiErrorException;
import com.pacheco.app.service.ChatServer;
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

    private ChatServer server;

    public void setup() {
        this.server = new ChatServer();
    }

    public void submit() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            setAlert("Username can't be empty");
        }
        else if (password.isEmpty()) {
            setAlert("Password can't be empty");
        }
        else {
            doSubmit(username, password);
        }
    }

    public void doSubmit(String username, String password) {
        try {
            String token = this.server.login(username, password);
            getCommand().setToken(token);
            getCommand().setUsername(username);
            changePageTo("chat-list-page");
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

    public void toRegisterPage() {
        changePageTo("register-page");
    }

}
