package com.example.where2park;

import com.example.where2park.controller.ManageClientBookingsClass;
import javafx.application.Application;
import javafx.stage.Stage;

//USECASE 7 TEST

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Simulate user Alice (id = 1)
        int userId = 1;

        // Simulate clicking the "Review" button
        ManageClientBookingsClass controller = new ManageClientBookingsClass(userId);
        controller.start();  // This will call queryUserBookings() and open ParkingListScreen
    }

    public static void main(String[] args) {
        launch(args);
    }
}
