package com.example.where2park.ui;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



public class UserWelcomeScreen {

    public static void showEnableLocationMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Location Services Disabled");
            alert.setHeaderText("Location Not Available");
            alert.setContentText("Please enable location services to continue.");
            alert.showAndWait();
        });
    }
}
