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

        ComboBox<Parking> parkingBox = new ComboBox<>();
        ComboBox<String> categoryBox = new ComboBox<>();
        ComboBox<String> filterBox = new ComboBox<>();

        showStatisticalCategories(parkingBox, categoryBox, filterBox); // Step 2

        Button submit = new Button("Εμφάνιση");
        submit.setOnAction(e -> {
            selectCategory(parkingBox, categoryBox, filterBox, window); // Step 3
        });

        VBox layout = new VBox(10, title, parkingBox, categoryBox, filterBox, submit);
        layout.setStyle("-fx-padding: 15");
        window.setScene(new Scene(layout, 350, 250));
        window.show();
    }

    public static void showStatisticalCategories(ComboBox<Parking> parkingBox, ComboBox<String> categoryBox, ComboBox<String> filterBox) {
        List<Parking> parkings = DatabaseManager.loadParkings(); // Assumes XML loading
        parkingBox.setItems(FXCollections.observableArrayList(parkings));
        parkingBox.setPromptText("Επιλέξτε πάρκινγκ");

        parkingBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Parking parking) {
                return parking != null ? parking.getName() : "";
            }

            @Override
            public Parking fromString(String string) {
                return null;
            }
        });

        categoryBox.getItems().addAll(DatabaseManager.queryStatisticalCategories());
        filterBox.getItems().addAll("Ημέρα", "Ώρα", "Μήνας");
    }

    public static void selectCategory(ComboBox<Parking> parkingBox, ComboBox<String> categoryBox, ComboBox<String> filterBox, Stage window) {
        Parking selectedParking = parkingBox.getValue();
        String category = categoryBox.getValue();
        String filter = filterBox.getValue();

        if (selectedParking == null || category == null || filter == null) {
            showErrorMessage("Πρέπει να επιλέξετε πάρκινγκ, κατηγορία και φίλτρο.");
            return;
        }

        String result = DatabaseManager.queryStatistics(category, filter);
        if (result == null) {
            showErrorMessage("Δεν υπάρχουν διαθέσιμα δεδομένα για την επιλογή σας.");
            return;
        }

        String header = "Πάρκινγκ: " + selectedParking.getName()
                + "\nΔιεύθυνση: " + selectedParking.getAddress() + "\n\n";

        ManageDisplayStatisticsClass.processStats(header + result);
        window.close();
    }

    public static void showErrorMessage(String message) {
        ErrorScreen.display(message); // Alternative flow 1
    }
}
