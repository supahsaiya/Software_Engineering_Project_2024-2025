package com.example.service;

import com.example.domain.Location;

import java.util.Random;

public class GPSService {

    public Location detectLocation() {
        // Simulate GPS location detection
        Random rand = new Random();
        double latitude = 37.975 + rand.nextDouble() * 0.01;
        double longitude = 23.735 + rand.nextDouble() * 0.01;

        return new Location(latitude, longitude);
    }
}
