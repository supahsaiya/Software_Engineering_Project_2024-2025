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

    // Overloaded method (no error message) - overloading is totally common in java
    public static void display(Parking parking, Booking booking, int userId) {
        display(parking, booking, userId, "");  // Delegate to full method with empty error
    }

    // Main method with optional error message
    public static void display(Parking parking, Booking booking, int userId, String errorMessage) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        // Parking info and booking date
        Label info = new Label("Parking: " + parking.getName() + "\n" +  "Address: " + parking.getAddress() + "\nBooking Date: " + booking.getDate());
        info.setStyle("-fx-font-weight: bold;");

        // Error label, only shown if not empty
        Label errorLabel = new Label(errorMessage);
        if (!errorMessage.isEmpty()) {
            errorLabel.setStyle("-fx-text-fill: red;");
        }

        // User input fields
        TextArea reviewText = new TextArea();
        reviewText.setPromptText("Write your review... (limit 200 char)");
        // Limit input to 200 characters
        reviewText.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.length() > 200) {
                reviewText.setText(oldText);  // revert to old text if new text too long
            }
        });


        TextField starInput = new TextField();
        starInput.setPromptText("Stars (1 to 5)");

        Button submit = new Button("Submit Review");
        submit.setOnAction(e -> {
            String review = reviewText.getText().trim();
            String starsStr = starInput.getText().trim();
            int stars = starsStr.matches("\\d+") ? Integer.parseInt(starsStr) : -1;

            ManageReviewClass.reviewValidated(parking, booking, userId, stars, review);
            stage.close();
        });

        // Add components
        root.getChildren().addAll(info);
        if (!errorMessage.isEmpty()) {
            root.getChildren().add(errorLabel);
        }
        root.getChildren().addAll(reviewText, starInput, submit);

        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Add Review");
        stage.show();
    }


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


    private static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
