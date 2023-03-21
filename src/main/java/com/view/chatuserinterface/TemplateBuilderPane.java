package com.view.chatuserinterface;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TemplateBuilderPane extends BorderPane {

    VBox content;


    Button back;
    public TemplateBuilderPane(MainInterface mainInterface){
        /*
            161925
            22395b
            4b617c
            72889d
            c0d6df
            f15025
            b7ce63

            So im going to add a vbox to the center, then im going to add scroll pane text field scroll pane text field scroll pane text field
         */
        this.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        this.getStyleClass().add("menu-pane");


        content = new VBox();
        content.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        content.getStyleClass().add("content");

        VBox questionContent = new VBox();
        questionContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        questionContent.getStyleClass().add("content");
        questionContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane questions = new ScrollPane(questionContent);
        questions.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        questions.getStyleClass().add("scroll-pane");

        TextField questionTextField = new TextField();
        questionTextField.setPromptText("Enter your text here");
        questionTextField.getStyleClass().add("text-field");

        VBox slotsContent = new VBox();
        slotsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        slotsContent.getStyleClass().add("content");
        slotsContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane slots = new ScrollPane(slotsContent);
        slots.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        slots.getStyleClass().add("scroll-pane");

        TextField slotsTextField = new TextField();
        slotsTextField.setPromptText("Enter your text here");
        slotsTextField.getStyleClass().add("text-field");

        VBox actionsContent = new VBox();
        actionsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);

        ScrollPane actions = new ScrollPane(actionsContent);
        actions.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        actions.getStyleClass().add("scroll-pane");

        TextField actionsTextField = new TextField();
        actionsTextField.setPromptText("Enter your text here");
        actionsTextField.getStyleClass().add("text-field");


        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            mainInterface.switchScene(mainInterface.menu);
        });
        content.getChildren().addAll(questions,questionTextField,slots,slotsTextField,actions,actionsTextField);

        questionTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                questionTextField.getStyleClass().remove("text-field");
                questionTextField.getStyleClass().add("text-field-focused");
            } else {
                questionTextField.getStyleClass().remove("text-field-focused");
                questionTextField.getStyleClass().add("text-field");
            }
        });

        slotsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                slotsTextField.getStyleClass().remove("text-field");
                slotsTextField.getStyleClass().add("text-field-focused");
            } else {
                slotsTextField.getStyleClass().remove("text-field-focused");
                slotsTextField.getStyleClass().add("text-field");
            }
        });

        actionsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                actionsTextField.getStyleClass().remove("text-field");
                actionsTextField.getStyleClass().add("text-field-focused");
            } else {
                actionsTextField.getStyleClass().remove("text-field-focused");
                actionsTextField.getStyleClass().add("text-field");
            }
        });

        questionTextField.setOnAction(e ->{
            Label newLabel = new Label(questionTextField.getText());
            newLabel.getStyleClass().add("label");
            questionContent.getChildren().add(newLabel);
            questions.setVvalue(1.0);
            questionTextField.setText("");
        });

        slotsTextField.setOnAction(e ->{
            Label newLabel = new Label(slotsTextField.getText());
            newLabel.getStyleClass().add("label");
            slotsContent.getChildren().add(newLabel);
            slots.setVvalue(1.0);
            slotsTextField.setText("");
        });

        actionsTextField.setOnAction(e ->{
            Label newLabel = new Label(actionsTextField.getText());
            newLabel.getStyleClass().add("label");
            actionsContent.getChildren().add(newLabel);
            actions.setVvalue(1.0);
            actionsTextField.setText("");
        });

        this.setTop(back);
        this.setCenter(content);
    }





}
