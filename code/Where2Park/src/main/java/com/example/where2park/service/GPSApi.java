package com.example.where2park.service;

import java.util.Random;

public class GPSApi {

    private static final String[] FAKE_LOCATIONS = {
            "123 Aiolou St, Athens",
            "45 Ermou St, Athens",
            "9 Syntagma Square, Athens",
            "21 Patision Ave, Athens",
            "84 Panepistimiou St, Athens"
    };

    public String detectLocation() {
        Random rand = new Random();
        int index = rand.nextInt(FAKE_LOCATIONS.length);
        return FAKE_LOCATIONS[index];
    }
}
