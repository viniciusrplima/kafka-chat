package com.pacheco.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Data;

@Data
public class LoginController implements Controller {

    @FXML
    private Label alert;

    public void submit() {
        alert.setText("hellooooo");
        alert.setVisible(true);
    }

}
