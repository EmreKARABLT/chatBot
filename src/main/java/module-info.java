module com.view.chatuserinterface {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.view.chatuserinterface to javafx.fxml;
    exports com.view.chatuserinterface;
}