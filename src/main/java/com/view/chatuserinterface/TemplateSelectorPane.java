package com.view.chatuserinterface;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TemplateSelectorPane extends BorderPane {
    ScrollPane templates;
    VBox content;
    Button back;
    public TemplateSelectorPane(MainInterface mainInterface){
        this.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        this.getStyleClass().add("scroll-pane");



        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });
        this.setTop(back);
    }
}
