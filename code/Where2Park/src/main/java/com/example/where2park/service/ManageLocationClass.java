
package com.example.where2park.service;
import com.example.where2park.service.GPSApi;
import com.example.where2park.ui.ConfirmLocationScreen;

public class ManageLocationClass {

    private String location;
    private ConfirmLocationScreen confirmLocationScreen;
    private GPSApi gpsApi;

    public ManageLocationClass() {
        gpsApi = new GPSApi();
        confirmLocationScreen = new ConfirmLocationScreen();
    }

    // renamed findLocation to getLocation for clarity
    public String getLocation() {
        location = gpsApi.detectLocation();
        return location;
    }

    public void sendLocationForConfirmation() {
        String detectedLocation = getLocation();
        confirmLocationScreen.setLocation(detectedLocation);
        confirmLocationScreen.display();
        confirmLocationScreen.askUserConfirmation(); // <-- user input now handled here
    }

}
