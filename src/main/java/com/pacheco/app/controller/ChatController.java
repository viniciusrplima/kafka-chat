package com.pacheco.app.controller;

import com.pacheco.app.model.MessageDTO;
import com.pacheco.app.service.KafkaClient;
import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;

@Data
public class ChatController implements Controller {

    @FXML
    private VBox container;

    @FXML
    private VBox chat;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField messageField;

    private KafkaClient client = new KafkaClient();

    private Timer timer;

    public ChatController() {
        client.changeTopic("kafka-chat-topic");

        new AnimationTimer() {
            @SneakyThrows
            @Override
            public void handle(long l) {
                Optional<MessageDTO> messageDTO = client.receive();

                if (messageDTO.isPresent()) {
                    try {

                        addMessage(messageDTO.get(), false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void setup() {
        scroll.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                scroll.setVvalue(1);
            }
        });
    }

    public void setTopic(String topic) {
        this.client.changeTopic(topic);
    }

    public void addMessage(MessageDTO message, Boolean isMe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chat-item.fxml"));
        Parent chatItem = loader.load();

        ChatItemController controller = loader.getController();
        controller.getText().setText(message.getMessage());
        controller.getUser().setText(message.getUsername());

        if (isMe) {
            controller.getWrapper().setStyle("-fx-alignment: bottom-right");
            controller.getChatItem().setStyle("-fx-background-color: #cfc");
        }

        controller.getUser().setText("marcela");

        chat.getChildren().add(chatItem);
    }

    public void sendMessage() throws IOException {
        String message = messageField.getText();

        if (!message.isEmpty()) {
            messageField.setText("");
            client.send(message);
        }
    }

    public void keyListener(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }
}
