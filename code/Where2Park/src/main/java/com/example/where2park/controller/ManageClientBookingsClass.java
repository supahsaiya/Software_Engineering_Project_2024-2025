
package com.example.where2park.controller;

import com.example.where2park.model.Booking;
import com.example.where2park.ui.ParkingListScreen;
import com.example.where2park.ui.ClientHomeScreen;
import com.example.where2park.service.DataStorageManager;

import java.util.List;

public class ManageClientBookingsClass {
    private int userId;

    public ManageClientBookingsClass(int userId) {
        this.userId = userId;
    }

    public void start() {
        List<Booking> bookings = DataStorageManager.queryClientBooking(userId);

        if (bookings.isEmpty()) {
            ParkingListScreen.showNoBookings();
            ParkingListScreen.display(bookings, this, "No bookings available for review.");
            ClientHomeScreen.display(userId); // Redirect back to client home
        } else {
            ParkingListScreen.display(bookings, this, "Select Booking: ");
        }
    }


    public void onSelectBooking(String spot, String date) {
        new ManageReviewClass(userId, spot, date).create();
    }



}



