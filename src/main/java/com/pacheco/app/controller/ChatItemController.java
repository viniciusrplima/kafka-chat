package com.pacheco.app.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Data;

@Data
public class ChatItemController implements Controller {

    @FXML
    private Text user;

    @FXML
    private Text text;

    @FXML
    private HBox wrapper;

    @FXML
    private VBox chatItem;

}
