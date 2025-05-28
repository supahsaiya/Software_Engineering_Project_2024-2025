package com.example.where2park.ui;

import com.example.where2park.controller.ManageDisplayStatisticsClass;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.ErrorScreen;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ViewStatsScreen {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Επιλογή Στατιστικών");

        Label title = new Label("Επιλέξτε Κατηγορία και Φίλτρο Χρόνου:");

        ComboBox<String> categoryBox = new ComboBox<>();
        ComboBox<String> filterBox = new ComboBox<>();
        categoryBox.getItems().addAll(DatabaseManager.queryStatisticalCategories()); // Step 3
        filterBox.getItems().addAll("Ημέρα", "Ώρα", "Μήνας");

        Button submit = new Button("Εμφάνιση");

        submit.setOnAction(e -> {
            String category = categoryBox.getValue();
            String filter = filterBox.getValue();

            if (category == null || filter == null) {
                showErrorMessage("Πρέπει να επιλέξετε και τις δύο κατηγορίες.");
                return;
            }

            String result = DatabaseManager.queryStatistics(category, filter); // Step 5
            if (result == null) {
                showErrorMessage("Δεν υπάρχουν διαθέσιμα δεδομένα για την επιλογή σας.");
                return;
            }

            ManageDisplayStatisticsClass.processStats(result); // Step 6
            window.close();
        });

        VBox layout = new VBox(10, title, categoryBox, filterBox, submit);
        window.setScene(new Scene(layout, 300, 200));
        window.show();
    }

    public static void showErrorMessage(String message) {
        ErrorScreen.display(message); // Step 5a.2
    }
}
