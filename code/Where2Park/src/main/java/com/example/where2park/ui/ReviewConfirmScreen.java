package com.example.where2park.ui;

import com.example.where2park.model.Booking;
import com.example.where2park.model.Parking;
import com.example.where2park.controller.ManageReviewClass;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReviewConfirmScreen {

    public static void display(Parking parking, Booking booking, int userId, int stars, String reviewText) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        Label title = new Label("Confirm Your Review");
        Label parkingLabel = new Label("Parking: " + parking.getName());
        Label address = new Label("Address: " + parking.getAddress());
        Label dateLabel = new Label("Booking Date: " + booking.getDate());
        Label starsLabel = new Label("Stars: " + stars);
        Label reviewLabel = new Label("Review: " + reviewText);

        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(e -> {
            stage.close();
            ManageReviewClass.confirmationDone(parking, booking, userId, stars, reviewText);
        });

        root.getChildren().addAll(title, parkingLabel, address, dateLabel, starsLabel, reviewLabel, confirmBtn);
        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Review Confirmation");
        stage.show();
    }
}
