package com.example.where2park.ui;

import com.example.where2park.controller.ManageInfoClass;
import com.example.where2park.model.Parking;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditParkingScreen {
    public static void display() {
        Parking data = ManageInfoClass.loadParkingData();

        Stage window = new Stage();
        window.setTitle("Επεξεργασία Πληροφοριών");

        TextField nameField = new TextField(data.getName());
        TextField addressField = new TextField(data.getAddress());
        TextField telField = new TextField(data.getTel());
        TextField spotsField = new TextField(String.valueOf(data.getTotalSpots()));

        Button saveButton = new Button("Αποθήκευση");
        saveButton.setOnAction(e -> {
            try {
                String tel = telField.getText();
                int totalSpots = Integer.parseInt(spotsField.getText());

                if (tel.length() != 10) {
                    ErrorScreen.display("Λάθος αριθμός τηλεφώνου. Πρέπει να είναι 10 ψηφία.");
                    return;
                }

                // Load existing data to preserve lat/lon/currentlyAvailable
                Parking existing = ManageInfoClass.loadParkingData();
                if (existing == null) {
                    ErrorScreen.display("Αποτυχία φόρτωσης των τρεχουσών πληροφοριών.");
                    return;
                }

                // Update editable fields only
                Parking updated = new Parking(
                        nameField.getText(),
                        existing.getLat(),
                        existing.getLon(),
                        addressField.getText(),
                        tel,
                        totalSpots,
                        existing.getCurrentlyAvailable()
                );

                // Save and verify
                if (ManageInfoClass.saveParkingData(updated)) {
                    VerifyChangesScreen.display();
                    window.close();
                } else {
                    ErrorScreen.display("Αποτυχία αποθήκευσης των πληροφοριών.");
                }

            } catch (NumberFormatException ex) {
                ErrorScreen.display("Ο αριθμός θέσεων πρέπει να είναι αριθμός.");
            } catch (Exception ex) {
                ErrorScreen.display("Σφάλμα εισόδου. Ελέγξτε τα πεδία.");
            }
        });


        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.addRow(0, new Label("Όνομα:"), nameField);
        grid.addRow(1, new Label("Διεύθυνση:"), addressField);
        grid.addRow(2, new Label("Τηλέφωνο:"), telField);
        grid.addRow(3, new Label("Συνολικές Θέσεις:"), spotsField);
        grid.add(saveButton, 1, 4);

        Scene scene = new Scene(grid, 400, 250);
        window.setScene(scene);
        window.show();
    }
}
