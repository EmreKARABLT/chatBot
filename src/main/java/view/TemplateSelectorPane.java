package view;

import Logic.SkillParser;
import Logic.SkillTemplate;
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
    HBox bottom;
    MainInterface mainInterface;

    public TemplateSelectorPane(MainInterface mainInterface){
        this.mainInterface = mainInterface;

        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");
        ArrayList<String> skills = SkillParser.getAllSkillPaths();

        content = new VBox();
        content.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);
        HBox titlePane = new HBox();

        titlePane.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("          Select Your Template");
        title.getStyleClass().add("title-label");

        for (int i = 0; i < skills.size(); i++) {
            Button label = new Button(skills.get(i));
            label.getStyleClass().add("label-select");

            label.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Execute code for double-click event here
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
            delete.setOnAction(e ->{

            });

            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");
            edit.setOnAction(event -> {
                SkillTemplate skillData ;
                try {
                    skillData = SkillParser.readSkillsFromFile(label.getText());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mainInterface.templateBuilderPane.addContent(skillData, label.getText());
                mainInterface.switchScene(mainInterface.templateBuilder);

            });



            HBox stuffies = new HBox();
            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(label,edit,delete);

            content.getChildren().add(stuffies);

        }

        templates = new ScrollPane(content);
        templates.getStylesheets().add("file:src/main/resources/view/mystyles.css");
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

        bottom = new HBox();
        bottom.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        bottom.getStyleClass().add("content");
        bottom.getChildren().add(select);
        titlePane.getChildren().addAll(back,title);
        this.setTop(titlePane);
        this.setCenter(templates);
        this.setBottom(bottom);

    }
    public void refresh(){
        ArrayList<String> skills = SkillParser.getAllSkillPaths();


        content = new VBox();
        content.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);
        for (int i = 0; i < skills.size(); i++) {
            Button label = new Button(skills.get(i));
            label.getStyleClass().add("label-select");

            label.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Execute code for double-click event here
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

            
            HBox stuffies = new HBox();

            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(e ->{
                SkillParser.deleteFile(label.getText());
                content.getChildren().remove(stuffies);

            });

            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");
            edit.setOnAction(event -> {
                SkillTemplate skillData ;
                try {
                    skillData = SkillParser.readSkillsFromFile(label.getText());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mainInterface.templateBuilderPane.addContent(skillData, label.getText());
                mainInterface.switchScene(mainInterface.templateBuilder);

            });

     


            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(label,edit,delete);

            content.getChildren().add(stuffies);
            
            
        }
        
        templates = new ScrollPane(content);
        templates.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        templates.getStyleClass().add("scroll-pane");
        this.setCenter(templates);

    }
}
