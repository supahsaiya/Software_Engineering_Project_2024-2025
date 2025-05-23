package com.example.where2park.ui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.example.where2park.service.ManageLocationClass;
import com.example.where2park.service.GPSApi;
import com.example.where2park.model.Location;

public class NewAddressScreen {

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); // Blocks other windows
        window.setTitle("Add New Address");

        Label label = new Label("Enter new address:");
        TextField addressField = new TextField();

        Button submitButton = new Button("Submit");


        submitButton.setOnAction(e -> {
            String address = addressField.getText();
            if (address != null && !address.isEmpty()) {
                Location location = GPSApi.geocode(address); // ✅ Convert to lat/lon
                if (location != null) {
                    // No need for Platform.runLater here; you're already on the JavaFX thread
                    ManageLocationClass.instance.setConfirmedLocation(location); // ✅ Set it
                    System.out.println("Manual location set: " + location);
                } else {
                    System.out.println("Failed to geocode address.");
                }
                window.close(); // Close the UI
            }
        });



        VBox layout = new VBox(10, label, addressField, submitButton);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 150));
        window.showAndWait(); // Pause until window is closed
    }
}
