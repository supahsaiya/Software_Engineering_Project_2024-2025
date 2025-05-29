package com.example.where2park.ui;

import com.example.where2park.controller.ManageUserBookingClass;
import com.example.where2park.model.Booking;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.List;

public class ParkingListScreen {

    public static void display(List<Booking> bookings, ManageUserBookingClass controller, String message) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        if (bookings.isEmpty()) {
            Label noBookingsLabel = new Label(message);
            root.getChildren().add(noBookingsLabel);
        } else {
            for (Booking spot : bookings) {
                Button btn = new Button("Select " + spot);
                btn.setOnAction(e -> selectParkingSpot(stage, controller, spot));
                root.getChildren().add(btn);
            }
        }

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Your Bookings");
        stage.show();
    }

    private static void selectParkingSpot(Stage stage, ManageUserBookingClass controller, Booking spot) {
        stage.close();
        controller.onSelectBooking(spot.getSpot(), spot.getDate());
    }

    public static void showNoBookings() {
        System.out.println("No bookings available for review.");
        // Optionally, show an alert here if you want:
        // Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // alert.setTitle("No Bookings");
        // alert.setHeaderText(null);
        // alert.setContentText("You have no bookings eligible for review.");
        // alert.showAndWait();
    }
}
