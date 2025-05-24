package com.example.where2park.ui;

import com.example.where2park.controller.ManageUserBookingClass;
import com.example.where2park.model.Booking;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ParkingListScreen {
    public static void display(List<Booking> bookings, ManageUserBookingClass controller) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        for (Booking booking : bookings) {
            Button btn = new Button("Select " + booking.toString());

            btn.setOnAction(e -> {
                stage.close();
                controller.onSelectBooking(booking.getSpot(), booking.getDate());
            });

            root.getChildren().add(btn);
        }

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Your Bookings");
        stage.show();
    }

}
