
package com.example.where2park.controller;

import com.example.where2park.model.Location;
import com.example.where2park.model.Booking;
import com.example.where2park.ui.ParkingListScreen;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.controller.ManageReviewClass;
import java.util.List;

public class ManageUserBookingClass {
    private int userId;

    public ManageUserBookingClass(int userId) {
        this.userId = userId;
    }

    public void start() {
        List<Booking> bookings = DatabaseManager.queryUserBookings(userId);
        ParkingListScreen.display(bookings, this);
    }

    public void onSelectBooking(String spot, String date) {
        new ManageReviewClass(userId, spot, date).start();
    }
}

