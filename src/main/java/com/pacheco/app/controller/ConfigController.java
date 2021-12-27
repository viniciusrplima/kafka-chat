package com.pacheco.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

public class ConfigController extends AbstractController {

    @FXML
    private TextField kafkaServerUrl;

    @FXML
    private TextField chatServerUrl;

    public void submit() {
        String kafkaUrl = kafkaServerUrl.getText();
        String chatServer = chatServerUrl.getText();

        if (kafkaUrl.isBlank()) {
            showAlert("Kafka Server URL can't be empty");
        } else if (chatServer.isBlank()) {
            showAlert("Chat Server URL can't be empty");
        } else {
            getCommand().getState().setChatServerUrl(chatServer);
            getCommand().getState().setKafkaServerUrl(kafkaUrl);

            changePageTo("login-page");
        }
    }

    private void showAlert(String message) {
        Dialog dialog = new Dialog();
        dialog.setTitle("Alert");
        dialog.setContentText(message);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.showAndWait();
    }

}
