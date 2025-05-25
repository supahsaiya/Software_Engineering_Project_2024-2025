package com.example.where2park.ui;

import com.example.where2park.model.Parking;
import com.example.where2park.service.DataStorageManager;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.AvailabilityWindow;

public class EmployeeHomeScreen {

    public void selectAvailabilityWindow(String parkingName) {
        Parking parking = DatabaseManager.getParkingByName(parkingName); // Or wherever you get it

        if (parking == null) {
            System.out.println("Error: Parking not found for " + parkingName);
            return;
        }

        AvailabilityWindow.display(parking);
    }

}
