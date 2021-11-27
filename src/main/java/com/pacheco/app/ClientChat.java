package com.pacheco.app;

import com.pacheco.app.controller.*;
import com.pacheco.app.service.KafkaClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChat extends Application implements Command {

    private KafkaClient client;

    private String username;

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new Group(), 600, 480);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("global.css").toExternalForm());

        stage.setScene(scene);

        stage.setTitle("Kafka Chat");
        stage.show();

        changePageTo("login-page");
    }

    private static void main(String[] args) {
        launch(args);
    }

    @Override
    public void changePageTo(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(String.format("%s.fxml", pageName)));
            Parent root = loader.load();
            Controller controller = loader.getController();
            controller.setCommand(this);
            controller.setup();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
