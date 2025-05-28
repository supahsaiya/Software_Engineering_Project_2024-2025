package com.example.where2park.ui;

import com.example.where2park.model.Parking;
import com.example.where2park.service.DataStorageManager;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.AvailabilityWindow;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeHomeScreen {

    public void selectAvailabilityWindow(String parkingName) {
        Parking parking = DatabaseManager.getParkingByName(parkingName); // Or wherever you get it

        if (parking == null) {
            System.out.println("Error: Parking not found for " + parkingName);
            return;
        }

        AvailabilityWindow.display(parking);
    }

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Κεντρική Οθόνη Υπαλλήλου");

        Label label = new Label("Επιστροφή στην κεντρική οθόνη υπαλλήλου.");
        window.setScene(new Scene(new VBox(label), 300, 150));
        window.show();
    }

}
