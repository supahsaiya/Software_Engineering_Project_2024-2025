package com.example.where2park.service;

import com.example.where2park.service.GPSApi;
import com.example.where2park.model.Location;
import com.example.where2park.ui.ConfirmLocationScreen;
import com.example.where2park.ui.NewAddressScreen;
import com.example.where2park.ui.UserHomeScreen;
import com.example.where2park.ui.UserWelcomeScreen;
import javafx.application.Platform;

import java.util.List;

public class ManageLocationClass {

    private GPSApi gpsApi;
    private DatabaseManager dbManager = new DatabaseManager();
    private UserHomeScreen userHomeScreen = new UserHomeScreen();
    private Location confirmedLocation;

    public static ManageLocationClass instance; // <- ADD THIS


    public ManageLocationClass() {
        gpsApi = new GPSApi();
        instance = this; // <- SET INSTANCE
    }



    // renamed findLocation to getLocation for clarity
    public Location getLocation() {

        // Simulate 5% chance of location services being disabled
        if (Math.random() < 0.05) {
            System.out.println("📴 Location services are OFF.");
            UserWelcomeScreen.showEnableLocationMessage(); // Show user message
            return null;
        }

        return gpsApi.detectLocation(); // Normal behavior

    }


    public Location sendLocationForConfirmation() {
        Location detectedLocation = getLocation();
        if (detectedLocation == null) {
            System.out.println("Failed to detect location.");
            return null;
        }

        ConfirmLocationScreen.display(detectedLocation, this);  // blocks here until user closes

        return confirmedLocation;  // Return the value after dialog closes
    }



    public void processUserLocation(Location location) {
        dbManager.querySaveLocation(location);
        List<DatabaseManager.ParkingSpot> nearbySpots = dbManager.queryFindNearby(location);
        //userHomeScreen.showNearbyParkings(nearbySpots);
    }

    public Location getConfirmedLocation() {
        return confirmedLocation;
    }
    public void setConfirmedLocation(Location location) {
        this.confirmedLocation = location;
    }


    public void locationRejected() {
        System.out.println("User rejected the location. Awaiting manual entry...");
        Platform.runLater(() -> NewAddressScreen.display());
    }


    public static void newLocationAdded(String address) {
        System.out.println("User entered new address: " + address);

        // Use the geocode method to get accurate coordinates
        Location manualLocation = GPSApi.geocode(address);

        if (manualLocation != null) {
            instance.confirmedLocation = manualLocation;
            System.out.println("Manual location set: " + manualLocation);
            // Save to XML
            instance.dbManager.initializeOrUpdateUserData(1, "Alice", manualLocation);

            // Resume app flow
            instance.processUserLocation(manualLocation);
        } else {
            System.out.println("Geocoding failed for address: " + address);
        }
    }



}
