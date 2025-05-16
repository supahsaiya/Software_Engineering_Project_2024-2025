package com.example.service;

import com.example.domain.Location;
import com.example.domain.ParkingSpot;
import com.example.repository.DatabaseManager;

import java.util.List;

public class ParkingService {

    private final DatabaseManager db;

    public ParkingService(DatabaseManager db) {
        this.db = db;
    }

    public List<ParkingSpot> findNearbyParking(Location userLocation) {
        return db.queryFindNearby(userLocation);
    }
}
