package view;

import Logic.SkillTemplate;
import Logic.TemplateController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.FileNotFoundException;
import java.util.Objects;

public class AssistantPane extends BorderPane {
    ScrollPane responses;
    VBox content,holder;

    Button back;

    public AssistantPane(MainInterface mainInterface){

        HBox titlePane = new HBox();
        titlePane.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("            Template Assistant");

        title.getStyleClass().add("title-label");
        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");
        content = new VBox();
        content.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        content.getStyleClass().add("content");
        content.setAlignment(Pos.TOP_CENTER);

        holder = new VBox();
        holder.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        holder.getStyleClass().add("holder");


        responses = new ScrollPane(content);
        responses.getStylesheets().add("file:src/main/resources/view/mystyles.css");
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
            TemplateController assistant;
            try {
                 assistant = new TemplateController();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            SkillTemplate template= assistant.getMatchedRule(textField.getText());
            String output;
            try {
                if(template!= null){
                    if(template.getAction(assistant.getQuestionAsList(textField.getText())).answer != null||assistant.getQuestionAsList(textField.getText()) != null){
    
                        output = template.getAction(assistant.getQuestionAsList(textField.getText())).answer;
                    }else{
                        output = "Sorry I am unable to find an answer for that";
                    }                
                }else{
                    output = "Sorry I am unable to find an answer for that";
                }
                
            } catch (Exception ex) {
                output = "Sorry I am unable to find an answer for that";
            }

            Label outputLabel = new Label(output);
            outputLabel.getStyleClass().add("label-output");

            content.getChildren().addAll(newLabel,outputLabel);
            responses.setVvalue(1.0);
            textField.setText("");
        });
        holder.getChildren().add(responses);
        titlePane.getChildren().addAll(back,title);
        this.setBottom(textField);
        this.setTop(titlePane);
        this.setCenter(holder);

    }
}
