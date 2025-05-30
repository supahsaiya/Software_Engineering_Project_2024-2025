package com.example.where2park;

import com.example.where2park.model.ParkingLot;
import com.example.where2park.service.DataStorageManager;
import com.example.where2park.ui.AvailabilityWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class UseCase8Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        String parkingName = "Parking Γκάζι"; // Ensure this exists in parking.xml
        ParkingLot parking = DataStorageManager.getParkingByName(parkingName);

        if (parking != null) {
            // First, display current availability
            AvailabilityWindow.display(parking);

            // Also open the window to update availability
            AvailabilityWindow.updateAvailableSpots(parking);
        } else {
            System.out.println("❌ Parking not found: " + parkingName);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
