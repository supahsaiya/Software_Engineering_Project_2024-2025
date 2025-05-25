package com.example.where2park.controller;

import com.example.where2park.model.*;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.ParkingReviewForm;
import com.example.where2park.ui.ReviewConfirmScreen;

public class ManageReviewClass {
    private int userId;
    private Booking booking;
    private Parking parking;

    public ManageReviewClass(int userId, String spot, String date) {
        this.userId = userId;
        this.booking = new Booking(spot, date);
        this.parking = DatabaseManager.getParkingByName(spot);
    }

    // Start the review process
    public void start() {
        ParkingReviewForm.display(parking, booking, userId);
    }

    // Called after user submits review
    public static void reviewValidated(Parking parking, Booking booking, int userId, int stars, String reviewText) {
        ReviewConfirmScreen.display(() -> {
            // On Confirm button press, call confirmationDone
            new ManageReviewClass(userId, parking.getName(), booking.getDate())
                    .confirmationDone(parking, booking, stars, reviewText);
        });
    }

    // Called after confirm
    public void confirmationDone(Parking parking, Booking booking, int stars, String reviewText) {
        Review review = new Review(parking.getName(), userId, booking.getDate(), stars, reviewText);
        boolean success = review.updateReviewList();

        if (success) {
            Client client = DatabaseManager.getClientById(userId);
            if (client != null) {
                ManageEmailClass.sendEmailTo(client.getName()); // Simulated email
                System.out.println(" Review confirmed and email sent to client: " + client.getName());
            }
        } else {
            System.out.println(" Failed to save review.");
        }
    }
}
