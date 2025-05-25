package com.example.where2park.ui;

import com.example.where2park.controller.ManageReviewClass;
import com.example.where2park.model.Booking;
import com.example.where2park.model.Parking;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReviewConfirmScreen {
    public static void display(Parking parking, Booking booking, int userId, int stars, String reviewText) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        Label summary = new Label("You are about to submit this review:\n\n" +
                "Parking: " + parking.getName() + "\n" +
                "Date: " + booking.getDate() + "\n" +
                "Stars: " + stars + "\n" +
                "Review: " + reviewText);

        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            ManageReviewClass manageReview = new ManageReviewClass(userId, parking.getName(), booking.getDate());

            if (!ParkingReviewForm.validateStars(String.valueOf(stars))) {
                manageReview.errorStars();
                stage.close();
                return;
            }

            if (!ParkingReviewForm.validateText(reviewText)) {
                manageReview.errorText();
                stage.close();
                return;
            }

            manageReview.confirmationDone(parking, booking, stars, reviewText);
            stage.close();
        });


        root.getChildren().addAll(summary, confirm);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Confirm Review");
        stage.show();
    }
}



