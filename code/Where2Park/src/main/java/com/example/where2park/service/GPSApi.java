package com.example.where2park.service;

import com.example.where2park.model.Location;

import java.util.Random;

public class GPSApi {

    private static final Location[] FAKE_LOCATIONS = {
            new Location(37.9785, 23.7267, "123 Aiolou St, Athens"),
            new Location(37.9761, 23.7313, "45 Ermou St, Athens"),
            new Location(37.9755, 23.7348, "9 Syntagma Square, Athens"),
            new Location(37.9885, 23.7319, "21 Patision Ave, Athens"),
            new Location(37.9814, 23.7281, "84 Panepistimiou St, Athens")
    };

    public Location detectLocation() {
        Random rand = new Random();
        int index = rand.nextInt(FAKE_LOCATIONS.length);
        return FAKE_LOCATIONS[index];
    }
}
