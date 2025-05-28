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
        if (data == null) {
            redoMessage("Αποτυχία φόρτωσης των πληροφοριών.");
            return;
        }

        Stage window = new Stage();
        window.setTitle("Επεξεργασία Πληροφοριών");

        // Step: selectEditArea()
        TextField[] fields = selectEditArea(data);
        TextField nameField = fields[0];
        TextField addressField = fields[1];
        TextField telField = fields[2];
        TextField spotsField = fields[3];

        Button saveButton = new Button("Αποθήκευση");

        saveButton.setOnAction(e -> {
            checkInfo(window, nameField, addressField, telField, spotsField);
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

    public static TextField[] selectEditArea(Parking data) {
        TextField nameField = new TextField(data.getName());
        TextField addressField = new TextField(data.getAddress());
        TextField telField = new TextField(data.getTel());
        TextField spotsField = new TextField(String.valueOf(data.getTotalSpots()));

        return new TextField[]{nameField, addressField, telField, spotsField};
    }

    // Step: checkInfo()
    public static void checkInfo(Stage window, TextField nameField, TextField addressField, TextField telField, TextField spotsField) {
        try {
            String tel = telField.getText();
            int totalSpots = Integer.parseInt(spotsField.getText());

            if (tel.length() != 10) {
                redoMessage("Λάθος αριθμός τηλεφώνου. Πρέπει να είναι 10 ψηφία.");
                return;
            }

            Parking existing = ManageInfoClass.loadParkingData();
            if (existing == null) {
                redoMessage("Αποτυχία φόρτωσης των τρεχουσών πληροφοριών.");
                return;
            }

            Parking updated = new Parking(
                    nameField.getText(),
                    existing.getLat(),
                    existing.getLon(),
                    addressField.getText(),
                    tel,
                    totalSpots,
                    existing.getCurrentlyAvailable()
            );

            if (ManageInfoClass.saveParkingData(updated)) {
                showResult(window); // Step: showResult()
            } else {
                redoMessage("Αποτυχία αποθήκευσης των πληροφοριών.");
            }

        } catch (NumberFormatException ex) {
            redoMessage("Ο αριθμός θέσεων πρέπει να είναι αριθμός.");
        } catch (Exception ex) {
            redoMessage("Σφάλμα εισόδου. Ελέγξτε τα πεδία.");
        }
    }

    // Step: showResult()
    public static void showResult(Stage window) {
        VerifyChangesScreen.display(); // Acknowledge changes
        redirect(window);
    }

    // Step: redoMessage()
    public static void redoMessage(String error) {
        ErrorScreen.display(error); // Show error popup
    }

    // Step: redirect()
    public static void redirect(Stage window) {
        window.close(); // Go back / finish
    }
}
