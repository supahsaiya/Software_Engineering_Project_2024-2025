package com.example.where2park.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.example.where2park.controller.ManageLocationClass;

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
            addNewAddress(address, window);
        });

        VBox layout = new VBox(10, label, addressField, submitButton);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 300, 150));
        window.showAndWait(); // Pause until window is closed
    }

    public static void addNewAddress(String address, Stage windowToClose) {
        if (address != null && !address.trim().isEmpty()) {
            ManageLocationClass.newLocationAdded(address); // ✅ central logic
            if (windowToClose != null) {
                windowToClose.close(); // ✅ Close UI window if applicable
            }
        } else {
            System.out.println("Address cannot be empty.");
        }
    }
}
