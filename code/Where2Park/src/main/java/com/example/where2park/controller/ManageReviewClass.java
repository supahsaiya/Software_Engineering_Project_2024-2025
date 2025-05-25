package com.example.where2park.controller;

import com.example.where2park.model.Booking;
import com.example.where2park.model.Parking;
import com.example.where2park.model.Review;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.ui.ParkingReviewForm;
import com.example.where2park.ui.ReviewConfirmScreen;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageReviewClass {
    private int userId;
    private Booking booking;
    private Parking parking;

    public ManageReviewClass(int userId, String spot, String date) {
        this.userId = userId;
        this.booking = new Booking(spot, date);
        this.parking = DatabaseManager.getParkingByName(spot);
    }

    public void start() {
        ParkingReviewForm.display(parking, booking, userId);
    }
    public static void reviewValidated(Parking parking, Booking booking, int userId, int stars, String reviewText) {
        ReviewConfirmScreen.display(parking, booking, userId, stars, reviewText);
    }

    /*
    public static void confirmationDone(Parking parking, Booking booking, int userId, int stars, String reviewText) {
        DatabaseManager.saveReview(parking.getName(), userId, booking.getDate(), stars, reviewText);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Your review has been submitted!");
        alert.showAndWait();
    }

     */
    public void confirmationDone(Review review, String clientEmail) {
        boolean success = review.updateReviewList();
        if (success) {
            ManageEmailClass.sendEmailTo(clientEmail);
            System.out.println("Review saved and confirmation email (mock) sent.");
        } else {
            System.out.println("Failed to save review.");
        }
    }

}
