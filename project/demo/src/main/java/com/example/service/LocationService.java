package com.example.service;

import com.example.domain.Location;
import com.example.domain.User;
import com.example.repository.DatabaseManager;

public class LocationService {

    private final DatabaseManager db;

    public LocationService(DatabaseManager db) {
        this.db = db;
    }

    public void confirmLocation(User user, Location location) {
        user.setCurrentLocation(location);
        db.saveUserLocation(user.getId(), location);
    }

    public void rejectLocation(User user) {
        System.out.println("Location rejected by user.");
    }

    public void enterManualLocation(User user, String address, double lat, double lon) {
        Location manualLocation = new Location(lat, lon, address);
        user.setCurrentLocation(manualLocation);
        db.saveUserLocation(user.getId(), manualLocation);
    }
}
