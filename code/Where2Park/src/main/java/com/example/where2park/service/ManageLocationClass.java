package com.example.where2park.service;

import com.example.where2park.service.GPSApi;
import com.example.where2park.model.Location;
import com.example.where2park.ui.ConfirmLocationScreen;
import com.example.where2park.ui.UserHomeScreen;
import java.util.List;

public class ManageLocationClass {

    private GPSApi gpsApi;
    private DatabaseManager dbManager = new DatabaseManager();
    private UserHomeScreen userHomeScreen = new UserHomeScreen();
    private Location confirmedLocation;

    public ManageLocationClass() {
        gpsApi = new GPSApi();
    }

    // renamed findLocation to getLocation for clarity
    public Location getLocation() {
        return gpsApi.detectLocation();
    }


    public void sendLocationForConfirmation() {
        Location detectedLocation = getLocation();

        if (detectedLocation == null) {
            System.out.println("Failed to detect location.");
            return;
        }

        ConfirmLocationScreen.display(detectedLocation);  // This blocks until UI closes
        this.confirmedLocation = ConfirmLocationScreen.getConfirmedLocation();

        if (confirmedLocation != null) {
            System.out.println("Location confirmed: " + confirmedLocation);
            processUserLocation(confirmedLocation);
        } else {
            System.out.println("User rejected the location. Prompt for manual input or retry.");
        }
    }


    public void processUserLocation(Location location) {
        dbManager.querySaveLocation(location);
        List<DatabaseManager.ParkingSpot> nearbySpots = dbManager.queryFindNearby(location);
        userHomeScreen.showNearbyParkings(nearbySpots);
    }

    public Location getConfirmedLocation() {
        return confirmedLocation;
    }


}
