package com.example.where2park.ui;


import com.example.where2park.model.Booking;
import com.example.where2park.model.Parking;
import com.example.where2park.service.DatabaseManager;
import com.example.where2park.controller.ManageReviewClass;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParkingReviewForm {

    public static void display(Parking parking, Booking booking, int userId) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        // Show detailed info
        Label title = new Label("Review: " + parking.getName());
        Label address = new Label("Address: " + parking.getAddress());
        Label tel = new Label("Phone: " + parking.getTel());
        Label bookingDate = new Label("Booking Date: " + booking.getDate());

        TextArea reviewText = new TextArea();
        reviewText.setPromptText("Write your review...");

        TextField starInput = new TextField();
        starInput.setPromptText("Stars (1 to 5)");

        Button submit = new Button("Submit Review");
        submit.setOnAction(e -> {
            String review = reviewText.getText().trim();
            String starsStr = starInput.getText().trim();

            if (!validateStars(starsStr)) {
                showAlert("Invalid star rating. Enter a number from 1 to 5.");
                return;
            }

            if (!validateText(review)) {
                showAlert("Review text cannot be empty.");
                return;
            }

            int stars = Integer.parseInt(starsStr);
            ManageReviewClass.reviewValidated(parking, booking, userId, stars, review);
            stage.close();

        });

        root.getChildren().addAll(title, address, tel, bookingDate, reviewText, starInput, submit);
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Add Review");
        stage.show();
    }

    private static boolean validateStars(String stars) {
        try {
            int val = Integer.parseInt(stars);
            return val >= 1 && val <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateText(String text) {
        return !text.isEmpty();
    }

    private static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
