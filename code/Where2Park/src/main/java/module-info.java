module com.example.where2park {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.where2park to javafx.fxml;
    exports com.example.where2park;
    exports com.example.where2park.ui; // <-- this exports your UI package so JavaFX can access it

    // export other packages if needed, e.g. service
    exports com.example.where2park.service;
}