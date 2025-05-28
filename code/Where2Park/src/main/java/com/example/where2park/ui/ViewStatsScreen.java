package com.example.where2park.ui;

import com.example.where2park.controller.ManageDisplayStatisticsClass;
import com.example.where2park.model.Parking;
import com.example.where2park.service.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ViewStatsScreen {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Επιλογή Στατιστικών");

        Label title = new Label("Επιλέξτε Πάρκινγκ, Κατηγορία και Φίλτρο Χρόνου:");

        // Load parkings from XML
        List<Parking> parkings = DatabaseManager.loadParkings(); // You will define this method
        ComboBox<Parking> parkingBox = new ComboBox<>(FXCollections.observableArrayList(parkings));
        parkingBox.setPromptText("Επιλέξτε πάρκινγκ");

        // Show parking name in dropdown
        parkingBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Parking parking) {
                return parking != null ? parking.getName() : "";
            }

            @Override
            public Parking fromString(String string) {
                return null; // Not needed
            }
        });

        ComboBox<String> categoryBox = new ComboBox<>();
        ComboBox<String> filterBox = new ComboBox<>();
        categoryBox.getItems().addAll(DatabaseManager.queryStatisticalCategories()); // Step 3
        filterBox.getItems().addAll("Ημέρα", "Ώρα", "Μήνας");

        Button submit = new Button("Εμφάνιση");

        submit.setOnAction(e -> {
            Parking selectedParking = parkingBox.getValue();
            String category = categoryBox.getValue();
            String filter = filterBox.getValue();

            if (selectedParking == null || category == null || filter == null) {
                showErrorMessage("Πρέπει να επιλέξετε πάρκινγκ, κατηγορία και φίλτρο.");
                return;
            }

            String result = DatabaseManager.queryStatistics(category, filter); // Step 5
            if (result == null) {
                showErrorMessage("Δεν υπάρχουν διαθέσιμα δεδομένα για την επιλογή σας.");
                return;
            }

            String header = "Πάρκινγκ: " + selectedParking.getName()
                    + "\nΔιεύθυνση: " + selectedParking.getAddress() + "\n\n";
            ManageDisplayStatisticsClass.processStats(header + result); // Step 6
            window.close();
        });

        VBox layout = new VBox(10, title, parkingBox, categoryBox, filterBox, submit);
        layout.setStyle("-fx-padding: 15");
        window.setScene(new Scene(layout, 350, 250));
        window.show();
    }

    public static void showErrorMessage(String message) {
        ErrorScreen.display(message); // Step 5a.2
    }
}
