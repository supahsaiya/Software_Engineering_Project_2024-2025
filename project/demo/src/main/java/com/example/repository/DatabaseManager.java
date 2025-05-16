package com.example.repository;

import com.example.domain.Location;
import com.example.domain.ParkingSpot;

import java.util.*;

public class DatabaseManager {

    private final Map<String, Location> userLocations = new HashMap<>();
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public DatabaseManager() {
        // Dummy parking spots
        parkingSpots.add(new ParkingSpot("p1", "Parking Alpha", new Location(37.977, 23.736)));
        parkingSpots.add(new ParkingSpot("p2", "Beta Parking", new Location(37.978, 23.738)));
        parkingSpots.add(new ParkingSpot("p3", "Gamma Garage", new Location(37.976, 23.734)));
    }

    public void saveUserLocation(String userId, Location location) {
        userLocations.put(userId, location);
        System.out.println("Location saved for user " + userId + ": " + location);
    }

    public List<ParkingSpot> queryFindNearby(Location userLocation) {
        // Simulate: return all parkings within 0.005 degrees
        List<ParkingSpot> result = new ArrayList<>();
        for (ParkingSpot ps : parkingSpots) {
            double dx = Math.abs(ps.getLocation().getLatitude() - userLocation.getLatitude());
            double dy = Math.abs(ps.getLocation().getLongitude() - userLocation.getLongitude());
            if (dx < 0.005 && dy < 0.005) {
                result.add(ps);
            }
        }
        return result;
    }
}
