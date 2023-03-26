package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainInterface extends Application {

    Scene menu,assistant,templateSelector,templateBuilder;
    final int HEIGHT = 800;
    final int WIDTH = 600;
    Stage stage;

    TemplateBuilderPane templateBuilderPane;
    TemplateSelectorPane templateSelectorPane;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        AssistantPane assistantPane = new AssistantPane(this);
        assistant = new Scene(assistantPane,WIDTH,HEIGHT);

        MenuPane menuPane = new MenuPane(this);
        menu = new Scene(menuPane,WIDTH,HEIGHT);

        templateSelectorPane = new TemplateSelectorPane(this);
        templateSelector = new Scene(templateSelectorPane,WIDTH,HEIGHT);

        templateBuilderPane = new TemplateBuilderPane(this);
        templateBuilder = new Scene(templateBuilderPane,WIDTH,HEIGHT);

        stage.setScene(menu);
        stage.setResizable(false);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }

    public void switchScene(Scene newScene){
        stage.setScene(newScene);

    }

}