package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class MenuPane extends FlowPane {


     Button assistantBtn,templateBtn,templateBuildBtn,cfgAssistantBtn,cfgEditorBtn,imgDetBtn,transformerButton;

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
            mainInterface.templateSelectorPane.refresh();
            mainInterface.switchScene(mainInterface.templateSelector);
        });

        templateBuildBtn = new Button("Template Builder");
        templateBuildBtn.getStyleClass().add("my-button");
        templateBuildBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.templateBuilder);
        });

        cfgAssistantBtn = new Button("CFG Assistant");
        cfgAssistantBtn.getStyleClass().add("my-button");
        cfgAssistantBtn.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.cfgAssistant);
        });

        cfgEditorBtn = new Button("CFG Editor");
        cfgEditorBtn.getStyleClass().add("my-button");
        cfgEditorBtn.setOnAction(e ->{
            //mainInterface.cfgEditorPane.clearContent();
            mainInterface.cfgEditorPane.addContent();
            mainInterface.switchScene(mainInterface.cfgEditor);
        });

        transformerButton = new Button("Transformer");
        transformerButton.getStyleClass().add("my-button");
        transformerButton.setOnAction(e ->{
            //mainInterface.cfgEditorPane.clearContent();
            mainInterface.switchScene(mainInterface.transformerAssistant);
        });

        imgDetBtn = new Button("Image Detection");
        imgDetBtn.getStyleClass().add("my-button");
        imgDetBtn.setOnAction(e ->{
            //mainInterface.cfgEditorPane.clearContent();
            mainInterface.cfgEditorPane.addContent();
            mainInterface.switchScene(mainInterface.imageDetection);
        });

        this.getStyleClass().add("menu-pane");
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.getChildren().addAll(title,assistantBtn,templateBtn,templateBuildBtn,cfgAssistantBtn,cfgEditorBtn,imgDetBtn,transformerButton);
    }

}
