
package com.example.where2park.controller;

import com.example.where2park.model.Location;
import com.example.where2park.model.Booking;
import com.example.where2park.ui.ParkingListScreen;
import com.example.where2park.ui.UserHomeScreen;
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

        if (bookings.isEmpty()) {
            showNoBookings();
            ParkingListScreen.display(bookings, this, "No bookings available for review.");
            UserHomeScreen.display(userId); // Redirect back to client home
        } else {
            ParkingListScreen.display(bookings, this, "Select Booking: ");
        }
    }


    public void onSelectBooking(String spot, String date) {
        new ManageReviewClass(userId, spot, date).start();
    }

    public void showNoBookings() {
        System.out.println("No bookings available for review.");
        // Optionally, show an alert here if you want:
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle("No Bookings");
        // alert.setHeaderText(null);
        // alert.setContentText("You have no bookings eligible for review.");
        // alert.showAndWait();
    }

}



