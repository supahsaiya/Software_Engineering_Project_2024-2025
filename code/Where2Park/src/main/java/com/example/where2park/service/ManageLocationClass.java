package com.example.where2park.service;

import com.example.where2park.service.GPSApi;
import com.example.where2park.ui.ConfirmLocationScreen;

public class ManageLocationClass {

    private GPSApi gpsApi;

    public ManageLocationClass() {
        gpsApi = new GPSApi();
    }

    // renamed findLocation to getLocation for clarity
    public String getLocation() {
        return gpsApi.detectLocation();
    }

    public void sendLocationForConfirmation() {
        String detectedLocation = getLocation();
        ConfirmLocationScreen.display(detectedLocation); // launches JavaFX confirmation UI
        // Note: This will block until the JavaFX window is closed
    }
}
