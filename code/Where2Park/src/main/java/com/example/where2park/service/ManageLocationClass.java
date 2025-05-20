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
        ConfirmLocationScreen.display(detectedLocation); // Show UI

        // You need to get a result back from the screen somehow
        // For example, ConfirmLocationScreen.getConfirmedLocation()
        this.confirmedLocation = ConfirmLocationScreen.getConfirmedLocation();

        if (confirmedLocation != null) {
            processUserLocation(confirmedLocation);
        } else {
            System.out.println("User rejected location.");
            // You could prompt for manual input here
        }
    }

    public void processUserLocation(Location location) {
        dbManager.querySaveLocation(location);
        List<DatabaseManager.ParkingSpot> nearbySpots = dbManager.queryFindNearby(location);
        userHomeScreen.showNearbyParkings(nearbySpots);
    }
    /*
    public void processUserLocation() {
        Location location = gpsApi.detectLocation();
        dbManager.querySaveLocation(location);

        List<DatabaseManager.ParkingSpot> nearbySpots = dbManager.queryFindNearby(location);
        userHomeScreen.showNearbyParkings(nearbySpots);
    }

     */
}
