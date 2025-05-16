module com.mdmakeup {
    requires transitive javafx.controls;
    requires transitive javafx.fxml; 
    requires java.logging;
    requires java.desktop;

    opens com.mdmakeup.controllers to javafx.fxml;
    opens com.mdmakeup.models to javafx.base;

    exports com.mdmakeup;
    exports com.mdmakeup.controllers;
    exports com.mdmakeup.models;
}