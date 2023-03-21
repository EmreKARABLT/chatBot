package com.view.chatuserinterface;

import com.Logic.Action;
import com.Logic.SkillParser;
import com.Logic.SkillTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.ArrayList;

public class TemplateBuilderPane extends BorderPane {

    VBox content,questionContent,slotsContent,actionsContent;
    ScrollPane actionsScroll,slotsScroll,questionsScroll;

    String questions,slots,actions = "";
    Button back,submit;
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
        submit = new Button("Submit");
        submit.getStyleClass().add("my-button");
        Popup popup = new Popup();
        popup.setAutoHide(true);

// Create a label to display in the popup
        Label label = new Label("You have either entered no question or too many questons");

// Add the label to the popup
        popup.getContent().add(label);

// Set the position of the popup relative to the button
        popup.setX(submit.getLayoutX() + submit.getWidth());
        popup.setY(submit.getLayoutY());
        submit.setOnAction(event -> {
            if(this.questions.split("\n").length==1){
                try {

                SkillParser.writeToFile(this.questions,this.questions+this.slots+this.actions);
                }catch (Exception e){
                    System.out.println(e);
                }
                mainInterface.switchScene(mainInterface.menu);
            }else {
                popup.show(submit.getScene().getWindow());
            }
        });

        content = new VBox();
        content.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        content.getStyleClass().add("content");

         questionContent = new VBox();
        questionContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        questionContent.getStyleClass().add("content");
        questionContent.setAlignment(Pos.TOP_CENTER);

         questionsScroll = new ScrollPane(questionContent);
        questionsScroll.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        questionsScroll.getStyleClass().add("scroll-pane");

        TextField questionTextField = new TextField();
        questionTextField.setPromptText("Enter your text here");
        questionTextField.getStyleClass().add("text-field");

         slotsContent = new VBox();
        slotsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        slotsContent.getStyleClass().add("content");
        slotsContent.setAlignment(Pos.TOP_CENTER);

         slotsScroll = new ScrollPane(slotsContent);
        slotsScroll.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        slotsScroll.getStyleClass().add("scroll-pane");

        TextField slotsTextField = new TextField();
        slotsTextField.setPromptText("Enter your text here");
        slotsTextField.getStyleClass().add("text-field");

        actionsContent = new VBox();
        actionsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);

         actionsScroll = new ScrollPane(actionsContent);
        actionsScroll.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        actionsScroll.getStyleClass().add("scroll-pane");

        TextField actionsTextField = new TextField();
        actionsTextField.setPromptText("Enter your text here");
        actionsTextField.getStyleClass().add("text-field");


        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            this.clearContent();
            mainInterface.switchScene(mainInterface.menu);
        });
        content.getChildren().addAll(questionsScroll,questionTextField,slotsScroll,slotsTextField,actionsScroll,actionsTextField);

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
            this.questions = "Question "+questionTextField.getText();
            newLabel.getStyleClass().add("label-build");
            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                questionContent.getChildren().remove(newLabel);
                questionContent.getChildren().remove(stuffies);
                questionContent.getChildren().remove(delete);


            });

            stuffies.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(newLabel,delete);

            questionContent.getChildren().add(stuffies);

            questionTextField.setText("");
        });

        slotsTextField.setOnAction(e ->{

            Label newLabel = new Label(slotsTextField.getText());
            this.slots += "Slot "+ slotsTextField.getText()+"\n";
            newLabel.getStyleClass().add("label-build");

            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                System.out.println(newLabel.getStyleClass().toString());
                slotsContent.getChildren().remove(newLabel);
                slotsContent.getChildren().remove(stuffies);
                slotsContent.getChildren().remove(delete);
            });

            stuffies.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(newLabel,delete);


            slotsContent.getChildren().add(stuffies);
            slotsTextField.setText("");
        });

        actionsTextField.setOnAction(e ->{
            Label newLabel = new Label(actionsTextField.getText());
            newLabel.getStyleClass().add("label-build");
            this.actions += "Action "+actionsTextField.getText()+"\n";

            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                actionsContent.getChildren().remove(newLabel);
                actionsContent.getChildren().remove(stuffies);
                actionsContent.getChildren().remove(delete);
            });

            stuffies.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
            stuffies.getStyleClass().add("content");
            stuffies.getChildren().addAll(newLabel,delete);

            actionsContent.getChildren().add(stuffies);

            actionsTextField.setText("");
        });

        this.setTop(back);
        this.setCenter(content);
        this.setBottom(submit);
    }

    public void clearContent(){
        questions = "";
        slots = "";
        actions="";



        questionContent = new VBox();
        questionContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        questionContent.getStyleClass().add("content");
        questionContent.setAlignment(Pos.TOP_CENTER);

        questionsScroll.setContent(questionContent);

        slotsContent = new VBox();
        slotsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        slotsContent.getStyleClass().add("content");
        slotsContent.setAlignment(Pos.TOP_CENTER);

        slotsScroll.setContent(slotsContent);

        actionsContent = new VBox();
        actionsContent.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);

        actionsScroll.setContent(actionsContent);

    }

    public void addContent(SkillTemplate skill, String filename){


        String question = skill.getQuestion();
        questions = "Question" + question;

        Label newLabel = new Label(question);
        newLabel.getStyleClass().add("label-build");
        HBox stuffies = new HBox();
        Button delete = new Button("X");
        delete.getStyleClass().add("delete-button");
        delete.setOnAction(event -> {
            questionContent.getChildren().remove(newLabel);
            questionContent.getChildren().remove(stuffies);
            questionContent.getChildren().remove(delete);
        });

        stuffies.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
        stuffies.getStyleClass().add("content");
        stuffies.getChildren().addAll(newLabel,delete);

        questionContent.getChildren().add(stuffies);

        ArrayList<String> slotNames = skill.getKeywords();
        ArrayList<String>[] arrayLists = new ArrayList[slotNames.size()];
        for (int i = 0; i < slotNames.size(); i++) {
            arrayLists[i] = skill.getSlots().get(slotNames.get(i));
        }

        for (int i = 0; i < arrayLists.length; i++) {

            for (int j = 0; j < arrayLists[i].size(); j++) {
                String text = "<"+slotNames.get(i)+"> "+arrayLists[i].get(j);
                slots += "Slot "+text +"\n";
                Label lbl = new Label(text);
                lbl.getStyleClass().add("label-build");

                HBox stuff = new HBox();
                Button del = new Button("X");
                del.getStyleClass().add("delete-button");
                del.setOnAction(event -> {
                    slotsContent.getChildren().remove(lbl);
                    slotsContent.getChildren().remove(stuff);
                    slotsContent.getChildren().remove(del);
                });

                stuff.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
                stuff.getStyleClass().add("content");
                stuff.getChildren().addAll(lbl,del);


                slotsContent.getChildren().add(stuff);

            }



        }

        String[] file = SkillParser.readRawTextFromFile(filename).split("\n");
        ArrayList<String> actions = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            String firstWord = file[i].split(" ")[0];

            if (firstWord.equals("Action")) {
                this.actions += file[i]+"\n";
                String str;
                str = file[i].substring(file[i].indexOf(" ") + 1);
                actions.add(str);
            }
        }

        for (int i = 0; i < actions.size(); i++) {

            Label lbl = new Label( actions.get(i));
            lbl.getStyleClass().add("label-build");

            HBox stuff = new HBox();
            Button del = new Button("X");
            del.getStyleClass().add("delete-button");
            del.setOnAction(event -> {
                actionsContent.getChildren().remove(lbl);
                actionsContent.getChildren().remove(stuff);
                actionsContent.getChildren().remove(del);
            });

            stuff.getStylesheets().add("com/view/chatuserinterface/mystyles.css");
            stuff.getStyleClass().add("content");
            stuff.getChildren().addAll(lbl,del);

            actionsContent.getChildren().add(stuff);

        }


    }

}
