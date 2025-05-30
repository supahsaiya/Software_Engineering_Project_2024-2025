package com.example.where2park.ui;

import com.example.where2park.model.Booking;
import com.example.where2park.model.ParkingLot;
import com.example.where2park.controller.ManageReviewClass;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ParkingReviewForm {

    // Overloaded entry method
    public static void display(ParkingLot parking, Booking booking, int userId) {
        display(parking, booking, userId, ""); // Delegate to main display
    }

    // Main display method
    public static void display(ParkingLot parking, Booking booking, int userId, String errorMessage) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        Label info = new Label("Parking: " + parking.getName() + "\n" +
                "Address: " + parking.getAddress() + "\nBooking Date: " + booking.getDate());
        info.setStyle("-fx-font-weight: bold;");

        Label errorLabel = new Label(errorMessage);
        if (!errorMessage.isEmpty()) {
            errorLabel.setStyle("-fx-text-fill: red;");
        }

        TextArea reviewText = enterReviewText(); // ✅
        TextField starInput = selectStarRating(); // ✅
        Button submit = submitReview(stage, parking, booking, userId, reviewText, starInput); // ✅

        root.getChildren().addAll(info);
        if (!errorMessage.isEmpty()) {
            root.getChildren().add(errorLabel);
        }
        root.getChildren().addAll(reviewText, starInput, submit);

        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Add Review");
        stage.show();
    }

    // ✅ New method: enter review text
    private static TextArea enterReviewText() {
        TextArea reviewText = new TextArea();
        reviewText.setPromptText("Write your review... (limit 200 char)");
        reviewText.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 200) {
                reviewText.setText(oldText);
            }
        });
        return reviewText;
    }

    // ✅ New method: select star rating
    private static TextField selectStarRating() {
        TextField starInput = new TextField();
        starInput.setPromptText("Stars (1 to 5)");
        return starInput;
    }

    // ✅ New method: submit review
    private static Button submitReview(Stage stage, ParkingLot parking, Booking booking, int userId, TextArea reviewText, TextField starInput) {
        Button submit = new Button("Submit Review");

        submit.setOnAction(e -> {
            String review = reviewText.getText().trim();
            String starsStr = starInput.getText().trim();
            int stars = starsStr.matches("\\d+") ? Integer.parseInt(starsStr) : -1;

            ManageReviewClass.reviewValidated(parking, booking, userId, stars, review);
            stage.close();
        });

        return submit;
    }

    // Optional utilities for validation (if needed)
    public static boolean validateStars(String stars) {
        try {
            int val = Integer.parseInt(stars);
            return val >= 1 && val <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateText(String text) {
        return !text.isEmpty() && text.length() <= 200;
    }

    // Optional for user feedback (currently unused)
    private static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
