package com.pacheco.app.controller;

import com.pacheco.app.model.ChatDTO;
import com.pacheco.app.service.ChatServer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
public class ChatListController extends AbstractController {

    @FXML
    private ListView list;

    @FXML
    private Text profileUsername;

    private List<ChatDTO> chats;

    private ChatServer server;

    public void setup() {
        chats = new ArrayList<>();
        server = new ChatServer(getCommand().getState().getChatServerUrl());
        profileUsername.setText(getCommand().getUsername());
        updateList();
    }

    public void updateList() {
        try {
            chats = server.listChats(getCommand().getToken());
            list.getItems().clear();

            for (ChatDTO chat : chats) {
                list.getItems().add(chat.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enterChat() {
        int index = list.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            getCommand().getState().setChat(chats.get(index));
            changePageTo("chat-page");
        } else {
            showAlert("No chat selected");
        }
    }

    public void createChat() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Chat");

        Optional<String> chatNameOpt = dialog.showAndWait();

        if (chatNameOpt.isPresent()) {
            if (!chatNameOpt.get().isBlank()) {
                doCreateChat(chatNameOpt.get());
            }
            else {
                showAlert("Chat name can't be empty");
            }
        }
    }

    public void doCreateChat(String chatName) {
        try {
            server.createChat(chatName, getCommand().getToken());
            updateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptInvitation() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Accept Invite");
        dialog.setContentText("Insert chat code");

        Optional<String> codeOpt = dialog.showAndWait();

        if (codeOpt.isPresent()) {
            if (!codeOpt.get().isBlank()) {
                doAcceptInvitation(codeOpt.get());
            } else {
                showAlert("Chat code can't be empty");
            }
        }
    }

    public void doAcceptInvitation(String chatCode) {
        try {
            server.joinChat(Integer.parseInt(chatCode), getCommand().getToken());
            updateList();
        } catch (NumberFormatException e) {
            showAlert("Chat code must be a number");
        } catch (Exception e) {
            showAlert("Error joining chat");
        }
    }

    public void invite() {
        int index = list.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Long chatCode = chats.get(index).getId();

            Dialog dialog = new Dialog();
            dialog.setTitle("Invite");
            dialog.setContentText("Send this code to your friend: " + chatCode);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(okButton);
            dialog.showAndWait();
        } else {
            showAlert("No chat selected");
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

    public void exitChat() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Exit Chat");
        dialog.setContentText("Are you sure to exit chat?");
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButton, confirmButton);
        dialog.showAndWait();
    }

}
