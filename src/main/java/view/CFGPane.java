package view;

import Logic.CFG.NGram;
import Logic.CFG.RecursiveParser;
import Logic.SkillTemplate;
import Logic.TemplateController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CFGPane extends BorderPane {
    ScrollPane responses;
    VBox content, holder;

    Button back;

    public CFGPane(MainInterface mainInterface) throws IOException {

        HBox titlePane = new HBox();
        titlePane.getStylesheets().add("file:src/main/resources/view/mystyles.css");
        titlePane.getStyleClass().add("content-title");
        Label title = new Label("                 CFG Assistant");

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
        back.setOnAction(e -> {
            mainInterface.switchScene(mainInterface.menu);
        });

        TextField textField = new TextField();
        TextArea predictionText = new TextArea();
        Map<String, List<String>> rules = RecursiveParser.parseGrammarFromFile("src/main/java/Logic/CFG/grammar.txt");
        textField.setPromptText("Enter your text here");
        textField.getStyleClass().add("text-field");
        predictionText.setEditable(false);

        AtomicInteger currentWordIndex = new AtomicInteger(0);
        AtomicReference<List<String>> nextWordsRef = new AtomicReference<>(new ArrayList<>());

        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                event.consume(); 
                String currentText = textField.getText();
                String[] words = currentText.split(" ");
                String lastWord = words[words.length - 1];

                try {
                    List<String> nextWords = NGram.predictWords(lastWord, rules);
                    nextWordsRef.set(nextWords);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                List<String> nextWords = nextWordsRef.get();
                if (!nextWords.isEmpty()) {
                    String nextWord = nextWords.get(currentWordIndex.get());
                    textField.appendText(" " + nextWord);
                }
            } else if (event.getCode() == KeyCode.ALT) {
                List<String> nextWords = nextWordsRef.get();
                currentWordIndex.set((currentWordIndex.get() + 1) % nextWords.size());
            }
        });

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.getStyleClass().remove("text-field");
                textField.getStyleClass().add("text-field-focused");
            } else {
                textField.getStyleClass().remove("text-field-focused");
                textField.getStyleClass().add("text-field");
            }
        });

        textField.setOnAction(e -> {
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

            content.getChildren().addAll(newLabel, outputLabel);
            responses.setVvalue(1.0);
            textField.setText("");
        });
        holder.getChildren().add(responses);
        titlePane.getChildren().addAll(back, title);
        this.setBottom(textField);
        this.setTop(titlePane);
        this.setCenter(holder);

    }
}
