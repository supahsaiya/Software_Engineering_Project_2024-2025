package com.example;

import com.example.domain.User;
import com.example.domain.Location;
import com.example.domain.ParkingSpot;
import com.example.service.*;
import com.example.repository.*;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        // Setup
        DatabaseManager db = new DatabaseManager();
        GPSService gps = new GPSService();
        LocationService locationService = new LocationService(db);
        ParkingService parkingService = new ParkingService(db);

        // Simulate user
        User user = new User("u1", "Alice");

        // Step 1: Detect GPS location
        Location detected = gps.detectLocation();
        System.out.println("Detected location: " + detected);

        // Step 2: User confirms it
        locationService.confirmLocation(user, detected);

        // Step 3: Find nearby parking
        List<ParkingSpot> nearby = parkingService.findNearbyParking(user.getCurrentLocation());

        System.out.println("\nNearby Parking Spots:");
        for (ParkingSpot spot : nearby) {
            System.out.println("• " + spot);
        }
    }
}
