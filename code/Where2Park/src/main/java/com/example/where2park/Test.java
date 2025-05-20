package com.example.where2park;

import com.example.where2park.service.ManageLocationClass;
import com.example.where2park.model.Location;

public class Test {
    public static void main(String[] args) {
        ManageLocationClass manager = new ManageLocationClass();

        // Start by showing the location confirmation UI
        manager.sendLocationForConfirmation();

        // After confirmation UI closes:
        // Retrieve confirmed location from ConfirmLocationScreen
        Location confirmed = com.example.where2park.ui.ConfirmLocationScreen.getConfirmedLocation();

        if (confirmed != null) {
            System.out.println("Confirmed Location: " + confirmed);
            manager.processUserLocation(confirmed);
        } else {
            System.out.println("No location confirmed, stopping.");
        }
    }
}
