package com.example.where2park.controller;

import com.example.where2park.model.*;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.ParkingReviewForm;
import com.example.where2park.ui.ReviewConfirmScreen;

public class ManageReviewClass {
    private int userId;
    private Booking booking;
    private ParkingLot parking;

    public ManageReviewClass(int userId, String spot, String date) {
        this.userId = userId;
        this.booking = new Booking(spot, date);
        this.parking = DatabaseManager.getParkingByName(spot);
    }

    // Start the review process
    public void create() {
        ParkingReviewForm.display(parking, booking, userId);
    }

    // Called after user submits review
    public static void reviewValidated(ParkingLot parking, Booking booking, int userId, int stars, String reviewText) {
        ReviewConfirmScreen.display(parking, booking, userId, stars, reviewText);
    }


    // Called after confirm
    public void confirmationDone(ParkingLot parking, Booking booking, int stars, String reviewText) {
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
    public void errorStars() {
        System.out.println("⚠️ Error: Star rating missing or invalid.");
        ParkingReviewForm.display(parking, booking, userId, "Please select a star rating from 1 to 5.");
    }

    public void errorText() {
        System.out.println("⚠️ Error: Review text too long.");
        ParkingReviewForm.display(parking, booking, userId, "Review text cannot exceed 200 characters.");
    }

}
