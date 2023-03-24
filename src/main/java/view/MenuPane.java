package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MenuPane extends FlowPane {


     Button assistantBtn,templateBtn,templateBuildBtn;

    public MenuPane(MainInterface mainInterface){

        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        Label title = new Label("         Project 2-2 Group-04         ");
        title.getStyleClass().add("label-menu-title");
        assistantBtn = new Button("Assistant");
        assistantBtn.getStyleClass().add("my-button");
        assistantBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.assistant);
        });

        templateBtn = new Button("Template Selector");
        templateBtn.getStyleClass().add("my-button");
        templateBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.templateSelector);
        });

        templateBuildBtn = new Button("Template Builder");
        templateBuildBtn.getStyleClass().add("my-button");
        templateBuildBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.templateBuilder);
        });

        this.getStyleClass().add("menu-pane");
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.getChildren().addAll(title,assistantBtn,templateBtn,templateBuildBtn);
    }

}
