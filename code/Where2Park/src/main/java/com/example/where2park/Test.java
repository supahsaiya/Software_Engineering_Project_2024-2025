package com.example.where2park;

import com.example.where2park.service.ManageLocationClass;
import com.example.where2park.model.Location;
import com.example.where2park.service.GPSApi;
import com.example.where2park.service.DatabaseManager;

//class made for testing

public class Test {

    public static void main(String[] args) {
        ManageLocationClass manager = new ManageLocationClass();

        // Step 1: Launch confirmation and wait for user input
        manager.sendLocationForConfirmation();

        // Step 2: Only then try to get the confirmed location
        Location confirmed = manager.getConfirmedLocation();

        if (confirmed != null) {
            System.out.println("Confirmed Location: " + confirmed);
            setupUser(confirmed); // pass confirmed location
        } else {
            System.out.println("No location confirmed. Stopping.");
        }
    }

    public static void setupUser(Location location) {
        new DatabaseManager().initializeOrUpdateUserData(1, "Alice", location);
    }
}

