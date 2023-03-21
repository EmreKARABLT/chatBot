package com.view.chatuserinterface;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.Objects;

public class AssistantPane extends BorderPane {
    ScrollPane responses;
    VBox content;

    Button back;

    public AssistantPane(MainInterface mainInterface){
        this.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        this.getStyleClass().add("menu-pane");
        content = new VBox();
        content.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);

        responses = new ScrollPane(content);
        responses.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        responses.getStyleClass().add("scroll-pane");


        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });

        TextField textField = new TextField();
        textField.setPromptText("Enter your text here");
        textField.getStyleClass().add("text-field");

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.getStyleClass().remove("text-field");
                textField.getStyleClass().add("text-field-focused");
            } else {
                textField.getStyleClass().remove("text-field-focused");
                textField.getStyleClass().add("text-field");
            }
        });
        textField.setOnAction(e ->{
            Label newLabel = new Label(textField.getText());
            newLabel.getStyleClass().add("label");
            content.getChildren().add(newLabel);
            responses.setVvalue(1.0);
            textField.setText("");
        });

        this.setBottom(textField);
        this.setTop(back);
        this.setCenter(responses);

    }
}
