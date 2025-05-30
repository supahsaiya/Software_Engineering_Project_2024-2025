package com.example.where2park.ui;

import com.example.where2park.model.Location;
import com.example.where2park.model.ParkingSpot;
import com.example.where2park.service.DataStorageManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ClientHomeScreen {

    // Dummy user location for demo (replace this with real user location logic)
    private static final Location dummyUserLocation = new Location("Syntagma Square", 37.9755, 23.7348);

    public static void display(int userId) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        Label welcomeLabel = new Label("Welcome back, Client #" + userId + "!");

        Button showNearbyBtn = new Button("Show Nearby Parkings");
        showNearbyBtn.setOnAction(e -> showNearby());

        root.getChildren().addAll(welcomeLabel, showNearbyBtn);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Client Home");
        stage.show();
    }

    public static void showNearby() {
        DataStorageManager dataManager = new DataStorageManager();

        List<ParkingSpot> nearbySpots = dataManager.queryFindNearby(dummyUserLocation);

        if (nearbySpots.isEmpty()) {
            System.out.println("No nearby parking spots found.");
        } else {
            System.out.println("Nearby Parking Spots:");
            for (ParkingSpot spot : nearbySpots) {
                System.out.printf("- %s (%.5f, %.5f)%n", spot.getName(), spot.getLatitude(), spot.getLongitude());
            }
        }
    }
}
