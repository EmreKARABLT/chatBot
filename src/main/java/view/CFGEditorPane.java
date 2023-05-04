package view;

import Logic.CFG.GrammerEditor;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CFGEditorPane extends BorderPane {
    VBox content,rulesContent,actionsContent;
    ScrollPane actionsScroll,rulesScroll;
    List<String> rules;
    List<String> actions;
    Button back,submit, addRule,addAction;
    HBox inputRule,inputAction;
    TextField rulesTextField,actionsTextField;

    GrammerEditor editor;

    public CFGEditorPane(MainInterface mainInterface){
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
        editor = new GrammerEditor();

        rules = editor.getRules();
        actions = editor.getActions();


        HBox titlePane = new HBox();
        titlePane.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("          Build Your Template");
        title.getStyleClass().add("title-label");

        this.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        this.getStyleClass().add("menu-pane");


        content = new VBox();
        content.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        content.getStyleClass().add("content");



        // rules display

        rulesContent = new VBox();
        rulesContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        rulesContent.getStyleClass().add("content");
        rulesContent.setAlignment(Pos.TOP_CENTER);

        rulesScroll = new ScrollPane(rulesContent);
        rulesScroll.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        rulesScroll.getStyleClass().add("scroll-pane-slot");

        inputRule = new HBox();
        addRule = new Button("ADD NEW RULE");
        addRule.getStyleClass().add("add-button");
        inputRule.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        inputRule.getStyleClass().add("input-content");
        inputRule.getChildren().add(addRule);
        rulesContent.getChildren().add(inputRule);


        rulesTextField = new TextField();
        rulesTextField.setPromptText("Enter your text here");
        rulesTextField.getStyleClass().add("text-field-inputbox");

        addRule.setOnAction(e->{
            inputRule.getChildren().remove(addRule);
            inputRule.getChildren().add(rulesTextField);
            rulesTextField.requestFocus();
        });

        rulesTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                rulesTextField.getStyleClass().remove("text-field-inputbox-field");
                rulesTextField.getStyleClass().add("text-field-inputbox-focused");
            } else {
                rulesTextField.getStyleClass().remove("text-field-inputbox-focused");
                rulesTextField.getStyleClass().add("text-field-inputbox-field");
            }
        });

        rulesTextField.setOnAction(e ->{
            Label newLabel = new Label(rulesTextField.getText());
            this.rules.add(rulesTextField.getText());
            newLabel.getStyleClass().add("label-build");
            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                rulesContent.getChildren().remove(newLabel);
                rulesContent.getChildren().remove(stuffies);
                rulesContent.getChildren().remove(delete);
            });
            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");
            edit.setOnAction(event ->{
                rules.remove(newLabel.getText());
                rulesContent.getChildren().remove(stuffies);
                inputRule.getChildren().remove(addRule);
                rulesTextField.setText(newLabel.getText());
                inputRule.getChildren().add(rulesTextField);
                rulesTextField.requestFocus();
                rulesScroll.setVvalue(1.0);

            });

            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            rulesContent.getChildren().remove(inputRule);
            stuffies.getChildren().addAll(newLabel,edit,delete);

            rulesContent.getChildren().add(stuffies);

            inputRule.getChildren().add(addRule);
            inputRule.getChildren().remove(rulesTextField);
            rulesContent.getChildren().add(inputRule);

            rulesTextField.setText("");
        });

        // rules display end

        // Actions display

        actionsContent = new VBox();
        actionsContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);

        actionsScroll = new ScrollPane(actionsContent);
        actionsScroll.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        actionsScroll.getStyleClass().add("scroll-pane-action");

        inputAction = new HBox();
        addAction = new Button("ADD NEW ACTION");
        addAction.getStyleClass().add("add-button");
        inputAction.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        inputAction.getStyleClass().add("input-content");
        inputAction.getChildren().add(addAction);
        actionsContent.getChildren().add(inputAction);

        actionsTextField = new TextField();
        actionsTextField.setPromptText("Enter your text here");
        actionsTextField.getStyleClass().add("text-field-inputbox");

        addAction.setOnAction(e->{
            inputAction.getChildren().remove(addAction);
            inputAction.getChildren().add(actionsTextField);
            actionsTextField.requestFocus();
        });


        actionsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                actionsTextField.getStyleClass().remove("text-field-inputbox-field");
                actionsTextField.getStyleClass().add("text-field-inputbox-focused");
            } else {
                actionsTextField.getStyleClass().remove("text-field-inputbox-focused");
                actionsTextField.getStyleClass().add("text-field-inputbox-field");
            }
        });

        actionsTextField.setOnAction(e ->{
            Label newLabel = new Label(actionsTextField.getText());
            newLabel.getStyleClass().add("label-build");
            this.actions.add(actionsTextField.getText());

            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                actionsContent.getChildren().remove(newLabel);
                actionsContent.getChildren().remove(stuffies);
                actionsContent.getChildren().remove(delete);
            });

            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");
            edit.setOnAction(event ->{
                actions.remove(newLabel.getText());
                actionsContent.getChildren().remove(stuffies);
                inputAction.getChildren().remove(addAction);
                actionsTextField.setText(newLabel.getText());
                inputAction.getChildren().add(actionsTextField);
                actionsTextField.requestFocus();
                actionsScroll.setVvalue(1.0);

            });


            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            actionsContent.getChildren().remove(inputAction);
            stuffies.getChildren().addAll(newLabel,edit,delete);

            actionsContent.getChildren().add(stuffies);

            inputAction.getChildren().add(addAction);
            inputAction.getChildren().remove(actionsTextField);
            actionsContent.getChildren().add(inputAction);

            actionsTextField.setText("");
        });

        // Actions display end

        back = new Button("Back");
        back.getStyleClass().add("my-button");
        back.setOnAction(e ->{
            this.clearContent();
            this.clearContent();
            mainInterface.switchScene(mainInterface.menu);
        });
        content.getChildren().addAll(rulesScroll,actionsScroll);


        HBox bottomPanel = new HBox();
        bottomPanel.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        bottomPanel.getStyleClass().add("content");






        submit = new Button("Submit");
        submit.getStyleClass().add("my-button");
        submit.setOnAction(event -> {
            editor.writeToFile();
        });

        bottomPanel.getChildren().addAll(submit);



        titlePane.getChildren().addAll(back,title);
        this.setTop(titlePane);
        this.setCenter(content);
        this.setBottom(bottomPanel);
    }

    public void clearContent(){


        rulesContent = new VBox();
        rulesContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        rulesContent.getStyleClass().add("content");
        rulesContent.setAlignment(Pos.TOP_CENTER);
        rulesContent.getChildren().add(inputRule);

        rulesScroll.setContent(rulesContent);

        actionsContent = new VBox();
        actionsContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);
        actionsContent.getChildren().add(inputAction);

        actionsScroll.setContent(actionsContent);


        if(rulesScroll.getStyleClass().remove("scroll-pane-error")){
            rulesScroll.getStyleClass().add("scroll-pane-slot");
        }

        if(actionsScroll.getStyleClass().remove("scroll-pane-error") ){
            actionsScroll.getStyleClass().add("scroll-pane-action");
        }

    }

    public void addContent(){

        HBox stuffies = new HBox();

        for (int i = 0; i < rules.size(); i++) {


                String text = rules.get(i);
                Label lbl = new Label(text);
                lbl.getStyleClass().add("label-build");

                HBox stuff = new HBox();


                Button editbutton = new Button("Edit");
                editbutton.getStyleClass().add("edit-button");
                editbutton.setOnAction(event ->{
                    rules.remove(lbl.getText());
                    rulesContent.getChildren().remove(stuffies);
                    inputRule.getChildren().remove(addRule);
                    rulesTextField.setText(lbl.getText());
                    inputRule.getChildren().add(rulesTextField);
                    rulesTextField.requestFocus();
                    rulesScroll.setVvalue(1.0);

                });
                Button del = new Button("X");
                del.getStyleClass().add("delete-button");
                del.setOnAction(event -> {
                    rules.remove(lbl.getText());
                    rulesContent.getChildren().remove(lbl);
                    rulesContent.getChildren().remove(stuff);
                    rulesContent.getChildren().remove(del);
                });

                stuff.getStylesheets().add("file:src/main/resources/view/mystyles.css");
                stuff.getStyleClass().add("content");
                stuff.getChildren().addAll(lbl,editbutton,del);


                rulesContent.getChildren().add(stuff);


            rulesContent.getChildren().remove(inputRule);
            rulesContent.getChildren().add(inputRule);



        }

        // rules read end

        // Action read



        for (int i = 0; i < actions.size(); i++) {

            Label lbl = new Label(actions.get(i));
            lbl.getStyleClass().add("label-build");

            HBox stuff = new HBox();
            Button del = new Button("X");
            del.getStyleClass().add("delete-button");
            del.setOnAction(event -> {
                actions.remove(lbl.getText());
                actionsContent.getChildren().remove(lbl);
                actionsContent.getChildren().remove(stuff);
                actionsContent.getChildren().remove(del);
            });
            Button editbutton = new Button("Edit");
            editbutton.getStyleClass().add("edit-button");

            editbutton.setOnAction(event ->{
                actionsContent.getChildren().remove(stuff);
                this.actions.remove(lbl.getText());
                inputAction.getChildren().remove(addAction);
                actionsTextField.setText(lbl.getText());
                inputAction.getChildren().add(actionsTextField);
                actionsTextField.requestFocus();
                actionsScroll.setVvalue(1.0);

            });


            stuff.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuff.getStyleClass().add("content");
            stuff.getChildren().addAll(lbl,editbutton,del);

            actionsContent.getChildren().add(stuff);

            actionsContent.getChildren().remove(inputAction);
            actionsContent.getChildren().add(inputAction);
        }

        // Action read end


    }
}
