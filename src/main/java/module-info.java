module view {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;
    requires libtensorflow;


    opens view to javafx.fxml;
    exports view;
}
