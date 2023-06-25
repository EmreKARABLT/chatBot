module view {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;
    requires libtensorflow;
    requires com.google.gson;


    opens view to javafx.fxml;
    exports view;
}
