module view {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;


    opens view to javafx.fxml;
    exports view;
}
