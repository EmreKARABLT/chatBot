package com.view.chatuserinterface;

import com.Logic.SkillParser;
import com.Logic.SkillTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class TemplateSelectorPane extends BorderPane {
    ScrollPane templates;
    VBox content;
    Button back,select;


    public TemplateSelectorPane(MainInterface mainInterface){
        this.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        this.getStyleClass().add("scroll-pane");
        ArrayList<String> skills = SkillParser.getAllSkills();
        content = new VBox();
        content.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);
        HBox titlePane = new HBox();
        titlePane.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("          Select Your Template");
        title.getStyleClass().add("title-label");

        for (int i = 0; i < skills.size(); i++) {
            Button label = new Button(skills.get(i));
            label.getStyleClass().add("label-select");

            label.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Execute code for double-click event here
                    System.out.println("Button double-clicked!");
                    SkillTemplate skillData ;
                    try {
                         skillData = SkillParser.readSkillsFromFile(label.getText());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    mainInterface.templateBuilderPane.addContent(skillData, label.getText());
                    mainInterface.switchScene(mainInterface.templateBuilder);

                }
            });

            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");

            HBox stuffies = new HBox();
            stuffies.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(label,delete);

            content.getChildren().add(stuffies);

        }

        templates = new ScrollPane(content);
        templates.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        templates.getStyleClass().add("scroll-pane");

        select = new Button("New");
        select.getStyleClass().add("my-button");
        select.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.templateBuilder);
        });

        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });
        titlePane.getChildren().addAll(back,title);
        this.setTop(titlePane);
        this.setCenter(templates);
        this.setBottom(select);

    }
}
