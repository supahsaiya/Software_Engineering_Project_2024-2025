package com.example.where2park.ui;

import com.example.where2park.model.Parking;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AvailabilityWindow {

    private static Label currentAvailabilityLabel;
    private static Label totalSpotsLabel;

    /**
     * Displays the Availability Window for a given parking object.
     */
    public static void display(Parking parking) {
        Stage stage = new Stage();
        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Availability for: " + parking.getName());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        currentAvailabilityLabel = new Label();
        totalSpotsLabel = new Label();

        showCurrentAvailability(parking);
        showAvailableSpots(parking);

        root.getChildren().addAll(title, currentAvailabilityLabel, totalSpotsLabel);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Parking Availability");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays the number of currently available spots.
     */
    public static void showCurrentAvailability(Parking parking) {
        if (currentAvailabilityLabel != null) {
            currentAvailabilityLabel.setText("Currently Available Spots: " + parking.getCurrentlyAvailable());
        }
    }

    /**
     * Displays the total number of parking spots.
     */
    public static void showAvailableSpots(Parking parking) {
        if (totalSpotsLabel != null) {
            totalSpotsLabel.setText("Total Spots: " + parking.getTotalSpots());
        }
    }
}
