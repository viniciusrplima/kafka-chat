package com.pacheco.app;

import com.pacheco.app.controller.*;
import com.pacheco.app.model.AppState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientChat extends Application implements Command {

    private Scene scene;

    private AppState state = new AppState();

    private Controller currentPageController;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(new Group(), 600, 480);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("global.css").toExternalForm());

        stage.setScene(scene);

        stage.setTitle("Kafka Chat");
        stage.show();

        changePageTo("config-page");
    }

    private static void main(String[] args) {
        launch(args);
    }

    @Override
    public void changePageTo(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(String.format("%s.fxml", pageName)));
            Parent root = loader.load();

            if (currentPageController != null) {
                currentPageController.quit();
            }

            currentPageController = loader.getController();
            currentPageController.setCommand(this);
            currentPageController.setup();

            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUsername(String username) {
        state.setUsername(username);
    }

    @Override
    public String getUsername() {
        return state.getUsername();
    }

    @Override
    public void setToken(String token) {
        state.setToken("Bearer " + token);
    }

    @Override
    public String getToken() {
        return state.getToken();
    }

    @Override
    public AppState getState() {
        return state;
    }
}
