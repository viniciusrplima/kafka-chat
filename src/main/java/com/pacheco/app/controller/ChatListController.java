package com.pacheco.app.controller;

import com.pacheco.app.model.ChatDTO;
import com.pacheco.app.service.ChatServer;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ObservableListValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
public class ChatListController extends AbstractController {

    @FXML
    private ListView list;

    private List<ChatDTO> chats;

    private ChatServer server;

    public void setup() {
        chats = new ArrayList<>();
        server = new ChatServer();
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
        getCommand().getState().setChat(chats.get(index));
        changePageTo("chat-page");
    }

    public void createChat() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Chat");

        Optional<String> chatNameOpt = dialog.showAndWait();

        if (chatNameOpt.isPresent() && !chatNameOpt.get().isBlank()) {
            doCreateChat(chatNameOpt.get());
        }
        else if (chatNameOpt.isPresent() && chatNameOpt.get().isBlank()) {
            showAlert("Chat name can't be empty");
        }
    }

    public void doCreateChat(String chatName) {
        try {
            server.createChat(chatName, getCommand().getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String message) {
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
