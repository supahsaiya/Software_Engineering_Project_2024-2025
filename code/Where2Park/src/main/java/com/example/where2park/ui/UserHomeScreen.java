package com.example.where2park.ui;

import com.example.where2park.service.DatabaseManager;
import java.util.List;

public class UserHomeScreen {

    public void showNearbyParkings(List<DatabaseManager.ParkingSpot> spots) {
        System.out.println("\nNearby parking spots:");
        for (DatabaseManager.ParkingSpot spot : spots) {
            System.out.println("- " + spot);
        }
    }
}
