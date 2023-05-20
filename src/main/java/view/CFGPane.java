package view;

import Logic.CFG.RecursiveParser;
import Logic.SkillTemplate;
import Logic.TemplateController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CFGPane extends BorderPane {
    ScrollPane responses;
    VBox content,holder;

    Button back;

    public CFGPane(MainInterface mainInterface){

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
            String output;

            try {
                RecursiveParser parser = new RecursiveParser();
                output = parser.respond(textField.getText(), "src/main/java/Logic/CFG/grammar.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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
