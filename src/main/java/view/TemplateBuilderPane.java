package view;

import Logic.Action;
import Logic.SkillParser;
import Logic.SkillTemplate;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;

public class TemplateBuilderPane extends BorderPane {

    VBox content,questionContent,slotsContent,actionsContent;
    ScrollPane actionsScroll,slotsScroll,questionsScroll;

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> slots = new ArrayList<>();
    ArrayList<String> actions = new ArrayList<>();
    TextField enterFileName;
    Button back,submit,addQuestion,addSlot,addAction;
    HBox inputQuestion, inputSlot,inputAction;
    TextField questionTextField, slotsTextField,actionsTextField;

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

        // Questions display

        questionContent = new VBox();
        questionContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        questionContent.getStyleClass().add("content");
        questionContent.setAlignment(Pos.TOP_CENTER);

        questionsScroll = new ScrollPane(questionContent);
        questionsScroll.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        questionsScroll.getStyleClass().add("scroll-pane-question");

        inputQuestion = new HBox();
        addQuestion = new Button("ADD NEW QUESTION");
        addQuestion.getStyleClass().add("add-button");
        inputQuestion.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        inputQuestion.getStyleClass().add("input-content");
        inputQuestion.getChildren().add(addQuestion);
        questionContent.getChildren().add(inputQuestion);

        questionTextField = new TextField();
        questionTextField.setPromptText("Enter your text here");
        questionTextField.getStyleClass().add("text-field-inputbox");

        addQuestion.setOnAction(e->{
            inputQuestion.getChildren().remove(addQuestion);
            inputQuestion.getChildren().add(questionTextField);
            questionTextField.requestFocus();
        });

        questionTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                questionTextField.getStyleClass().remove("text-field-inputbox");
                questionTextField.getStyleClass().add("text-field-inputbox-focused");
            } else {
                questionTextField.getStyleClass().remove("text-field-inputbox-focused");
                questionTextField.getStyleClass().add("text-field-inputbox");
            }
        });

        questionTextField.setOnAction(e ->{
            Label newLabel = new Label(questionTextField.getText());
            this.questions.add(questionTextField.getText());
            newLabel.getStyleClass().add("label-build");
            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                questionContent.getChildren().remove(newLabel);
                questionContent.getChildren().remove(stuffies);
                questionContent.getChildren().remove(delete);
            });
            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");

            edit.setOnAction(event ->{
                questions.remove(newLabel.getText());
                questionContent.getChildren().remove(stuffies);
                inputQuestion.getChildren().remove(addQuestion);
                questionTextField.setText(newLabel.getText());
                inputQuestion.getChildren().add(questionTextField);
                questionContent.getChildren().add(inputQuestion);
                questionTextField.requestFocus();
                questionsScroll.setVvalue(1.0);

            });

            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            questionContent.getChildren().remove(inputQuestion);
            stuffies.getChildren().addAll(newLabel,edit,delete);

            questionContent.getChildren().add(stuffies);

            inputQuestion.getChildren().add(addQuestion);
            inputQuestion.getChildren().remove(questionTextField);
            //questionContent.getChildren().add(inputQuestion);

            questionTextField.setText("");
        });

        // Questions display end

        // Slots display

        slotsContent = new VBox();
        slotsContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        slotsContent.getStyleClass().add("content");
        slotsContent.setAlignment(Pos.TOP_CENTER);

        slotsScroll = new ScrollPane(slotsContent);
        slotsScroll.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        slotsScroll.getStyleClass().add("scroll-pane-slot");

        inputSlot = new HBox();
        addSlot = new Button("ADD NEW SLOT");
        addSlot.getStyleClass().add("add-button");
        inputSlot.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        inputSlot.getStyleClass().add("input-content");
        inputSlot.getChildren().add(addSlot);
        slotsContent.getChildren().add(inputSlot);


        slotsTextField = new TextField();
        slotsTextField.setPromptText("Enter your text here");
        slotsTextField.getStyleClass().add("text-field-inputbox");

        addSlot.setOnAction(e->{
            inputSlot.getChildren().remove(addSlot);
            inputSlot.getChildren().add(slotsTextField);
            slotsTextField.requestFocus();
        });

        slotsTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                slotsTextField.getStyleClass().remove("text-field-inputbox-field");
                slotsTextField.getStyleClass().add("text-field-inputbox-focused");
            } else {
                slotsTextField.getStyleClass().remove("text-field-inputbox-focused");
                slotsTextField.getStyleClass().add("text-field-inputbox-field");
            }
        });

        slotsTextField.setOnAction(e ->{
            Label newLabel = new Label(slotsTextField.getText());
            this.slots.add(slotsTextField.getText());
            newLabel.getStyleClass().add("label-build");
            HBox stuffies = new HBox();
            Button delete = new Button("X");
            delete.getStyleClass().add("delete-button");
            delete.setOnAction(event -> {
                slotsContent.getChildren().remove(newLabel);
                slotsContent.getChildren().remove(stuffies);
                slotsContent.getChildren().remove(delete);
            });
            Button edit = new Button("Edit");
            edit.getStyleClass().add("edit-button");
            edit.setOnAction(event ->{
                slots.remove(newLabel.getText());
                slotsContent.getChildren().remove(stuffies);
                inputSlot.getChildren().remove(addSlot);
                slotsTextField.setText(newLabel.getText());
                inputSlot.getChildren().add(slotsTextField);
                slotsTextField.requestFocus();
                slotsScroll.setVvalue(1.0);

            });

            stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
            stuffies.getStyleClass().add("content");
            slotsContent.getChildren().remove(inputSlot);
            stuffies.getChildren().addAll(newLabel,edit,delete);

            slotsContent.getChildren().add(stuffies);

            inputSlot.getChildren().add(addSlot);
            inputSlot.getChildren().remove(slotsTextField);
            slotsContent.getChildren().add(inputSlot);

            slotsTextField.setText("");
        });

        // Slots display end

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
            mainInterface.templateSelectorPane.refresh();
            mainInterface.switchScene(mainInterface.templateSelector);
        });
        content.getChildren().addAll(questionsScroll,slotsScroll,actionsScroll);


        HBox bottomPanel = new HBox();
        bottomPanel.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        bottomPanel.getStyleClass().add("content");




        enterFileName = new TextField();
        enterFileName.setPromptText("Enter file name");
        enterFileName.getStyleClass().add("text-field-inputbox");


        enterFileName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                enterFileName.getStyleClass().remove("text-field-inputbox");
                enterFileName.getStyleClass().add("text-field-inputbox-focused");
            } else {
                enterFileName.getStyleClass().remove("text-field-inputbox-focused");
                enterFileName.getStyleClass().add("text-field-inputbox");
            }
        });

        submit = new Button("Submit");
        submit.getStyleClass().add("my-button");
        submit.setOnAction(event -> {
            if(enterFileName.getText().equals("")||enterFileName.getText().split(" ").length>1||questions.size() == 0||slots.size()==0||actions.size()==0){
                if(enterFileName.getText().equals("")||enterFileName.getText().split(" ").length>1){
                    enterFileName.getStyleClass().removeAll();
                    enterFileName.getStyleClass().add("text-field-error");
                }
                if(questions.size() == 0){
                    questionsScroll.getStyleClass().remove("scroll-pane-question");
                    questionsScroll.getStyleClass().add("scroll-pane-error");
                }
                if(slots.size()==0){    
                    slotsScroll.getStyleClass().remove("scroll-pane-slot");
                    slotsScroll.getStyleClass().add("scroll-pane-error");
                }
                if(actions.size() == 0){
                    actionsScroll.getStyleClass().remove("scroll-pane-action");
                    actionsScroll.getStyleClass().add("scroll-pane-error"); 
                }
                
            }
            else{
                String write = "";
                for (String q : this.questions) {
                    if (!q.contains("Question")) {
                        write += "Question "+q+"\n";
                    } else {
                        write += q+"\n";
                    }
                }
                for (String s : this.slots) {
                    if(!s.contains("Slot")){
                        write += "Slot "+s+"\n";
                    }else{
                        write += s+"\n";
                    }
                }
                for (String a : this.actions) {
                    if(!a.contains("Action")){
                        write += "Action "+ a+"\n";
                    }else{
                        write +=  a+"\n";
                    }
                }
                SkillParser.writeToFile(enterFileName.getText(), write);
                enterFileName.setText("");
            }
          
        });

        bottomPanel.getChildren().addAll(enterFileName,submit);



        titlePane.getChildren().addAll(back,title);
        this.setTop(titlePane);
        this.setCenter(content);
        this.setBottom(bottomPanel);
    }

    public void clearContent(){
        questions = new ArrayList<>();
        slots = new ArrayList<>();
        actions= new ArrayList<>();



        questionContent = new VBox();
        questionContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        questionContent.getStyleClass().add("content");
        questionContent.setAlignment(Pos.TOP_CENTER);
        questionContent.getChildren().add(inputQuestion);

        questionsScroll.setContent(questionContent);

        slotsContent = new VBox();
        slotsContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        slotsContent.getStyleClass().add("content");
        slotsContent.setAlignment(Pos.TOP_CENTER);
        slotsContent.getChildren().add(inputSlot);

        slotsScroll.setContent(slotsContent);

        actionsContent = new VBox();
        actionsContent.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        actionsContent.getStyleClass().add("content");
        actionsContent.setAlignment(Pos.TOP_CENTER);
        actionsContent.getChildren().add(inputAction);

        actionsScroll.setContent(actionsContent);

   
        if(questionsScroll.getStyleClass().remove("scroll-pane-error")){
            questionsScroll.getStyleClass().add("scroll-pane-question");
        }
     
        if(slotsScroll.getStyleClass().remove("scroll-pane-error")){
            slotsScroll.getStyleClass().add("scroll-pane-slot");
        }
        
        if(actionsScroll.getStyleClass().remove("scroll-pane-error") ){
            actionsScroll.getStyleClass().add("scroll-pane-action");
        }
        if(enterFileName.getStyleClass().remove("text-field-error")){
            enterFileName.getStyleClass().add("text-field");
        }

        enterFileName.setText("");


    }

    public void addContent(SkillTemplate skill, String filename){

        // Question read
        enterFileName.setText(filename);
        
        String question = skill.getQuestion();
        questions.add(question);

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

        Button edit = new Button("Edit");
        edit.getStyleClass().add("edit-button");
        edit.setOnAction(event ->{
            questions.remove(newLabel.getText());
            questionContent.getChildren().remove(stuffies);
            inputQuestion.getChildren().remove(addQuestion);
            questionTextField.setText(newLabel.getText());
            inputQuestion.getChildren().add(questionTextField);
            questionContent.getChildren().add(inputQuestion);
            questionTextField.requestFocus();
            questionsScroll.setVvalue(1.0);

        });

        stuffies.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        stuffies.getStyleClass().add("content");
        stuffies.getChildren().addAll(newLabel,edit,delete);
        questionContent.getChildren().remove(inputQuestion);
        questionContent.getChildren().add(stuffies);

        //Question read end

        //Slots read

        ArrayList<String> slotNames = skill.getKeywords();
        ArrayList<String>[] arrayLists = new ArrayList[slotNames.size()];
        for (int i = 0; i < slotNames.size(); i++) {
            arrayLists[i] = skill.getSlots().get(slotNames.get(i));
        }

        for (int i = 0; i < arrayLists.length; i++) {

            for (int j = 0; j < arrayLists[i].size(); j++) {
                String text = "<"+slotNames.get(i)+"> "+arrayLists[i].get(j);
                slots.add(text);
                Label lbl = new Label(text);
                lbl.getStyleClass().add("label-build");

                HBox stuff = new HBox();


                Button editbutton = new Button("Edit");
                editbutton.getStyleClass().add("edit-button");
                editbutton.setOnAction(event ->{
                    slots.remove(lbl.getText());
                    slotsContent.getChildren().remove(stuffies);
                    inputSlot.getChildren().remove(addSlot);
                    slotsTextField.setText(lbl.getText());
                    inputSlot.getChildren().add(slotsTextField);
                    slotsTextField.requestFocus();
                    slotsScroll.setVvalue(1.0);

                });
                Button del = new Button("X");
                del.getStyleClass().add("delete-button");
                del.setOnAction(event -> {
                    slotsContent.getChildren().remove(lbl);
                    slotsContent.getChildren().remove(stuff);
                    slotsContent.getChildren().remove(del);
                });

                stuff.getStylesheets().add("file:src/main/resources/view/mystyles.css");
                stuff.getStyleClass().add("content");
                stuff.getChildren().addAll(lbl,editbutton,del);


                slotsContent.getChildren().add(stuff);

            }
            slotsContent.getChildren().remove(inputSlot);
            slotsContent.getChildren().add(inputSlot);



        }

        // Slots read end

        // Action read

        String[] file = SkillParser.readRawTextFromFile(filename).split("\n");
        ArrayList<String> actions = new ArrayList<>();
        for (int i = 0; i < file.length; i++) {
            String firstWord = file[i].split(" ")[0];

            if (firstWord.equals("Action")) {
                this.actions.add( file[i]);
                String str;
                str = file[i].substring(file[i].indexOf(" ") + 1);
                actions.add(str);
            }
        }

        for (int i = 0; i < actions.size(); i++) {

            Label lbl = new Label(actions.get(i));
            lbl.getStyleClass().add("label-build");
           
            HBox stuff = new HBox();
            Button del = new Button("X");
            del.getStyleClass().add("delete-button");
            del.setOnAction(event -> {
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
