package com.pacheco.app;

import com.pacheco.app.controller.ChatController;
import com.pacheco.app.service.KafkaClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientChat extends Application {

    private KafkaClient client;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("chat-page.fxml"));
        Parent root = loader.load();
        ChatController controller = loader.getController();
        controller.setup();

        Scene scene = new Scene(root, 600, 480);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("global.css").toExternalForm());

        stage.setScene(scene);

        stage.setTitle("Kafka Chat");
        stage.show();
    }

    private static void main(String[] args) {
        launch(args);
    }
}
