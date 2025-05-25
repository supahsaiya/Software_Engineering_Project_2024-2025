package com.example.where2park;

import com.example.where2park.model.Parking;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.AvailabilityWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class UseCase8Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Simulate selecting a parking spot by name
        String parkingName = "Parking Γκάζι"; // make sure this exists in your XML
        Parking parking = DatabaseManager.getParkingByName(parkingName);

        if (parking != null) {
            // Open the availability window
            AvailabilityWindow.display(parking);
        } else {
            System.out.println("Parking not found: " + parkingName);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
