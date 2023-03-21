package com.view.chatuserinterface;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MenuPane extends FlowPane {

     Button assistantBtn,templateBtn;

    public MenuPane(MainInterface mainInterface){
        this.getStylesheets().add("com/view/chatuserinterface/mystyles.css");

        assistantBtn = new Button("Assistant");
        assistantBtn.getStyleClass().add("my-button");
        assistantBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.assistant);
        });

        templateBtn = new Button("Template Builder");
        templateBtn.getStyleClass().add("my-button");
        templateBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.templateSelector);
        });
        this.getStyleClass().add("menu-pane");
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.getChildren().addAll(assistantBtn,templateBtn);
    }

}
