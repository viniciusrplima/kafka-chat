package com.pacheco.app.controller;

import com.pacheco.app.model.MessageDTO;
import com.pacheco.app.service.KafkaClient;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;

@Data
public class ChatController extends AbstractController {

    @FXML
    private VBox container;

    @FXML
    private VBox chat;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField messageField;

    @FXML
    private Label chatTitle;

    private KafkaClient client;

    public ChatController() {
        new AnimationTimer() {
            @SneakyThrows
            @Override
            public void handle(long l) {
                Optional<MessageDTO> messageDTO = client.receive();

                if (messageDTO.isPresent()) {
                    try {
                        addMessage(messageDTO.get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void setup() {
        client = new KafkaClient(getCommand().getUsername(), getCommand().getState().getKafkaServerUrl());
        client.changeTopic(getCommand().getState().getChat().getKafkaTopic());
        chatTitle.setText(getCommand().getState().getChat().getName());
    }

    @Override
    public void quit() {
        client.close();
    }

    public void setTopic(String topic) {
        this.client.changeTopic(topic);
    }

    public void addMessage(MessageDTO message) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chat-item.fxml"));
        Parent chatItem = loader.load();

        ChatItemController controller = loader.getController();
        controller.getText().setText(message.getMessage());
        controller.getUser().setText(message.getUsername());

        if (message.getUsername().equalsIgnoreCase(getCommand().getUsername())) {
            controller.getWrapper().setStyle("-fx-alignment: bottom-right");
            controller.getChatItem().setStyle("-fx-background-color: #cfc");
        }

        chat.getChildren().add(chatItem);
        scroll.setVvalue(1);
    }

    public void sendMessage() throws IOException {
        String message = messageField.getText();

        if (!message.isEmpty()) {
            messageField.setText("");
            client.send(new MessageDTO(getCommand().getUsername(), message));
        }
    }

    public void goBack() {
        changePageTo("chat-list-page");
    }

    public void keyListener(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }
}
