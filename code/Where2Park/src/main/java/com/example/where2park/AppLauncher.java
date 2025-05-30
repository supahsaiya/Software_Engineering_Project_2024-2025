package com.example.where2park;

import com.example.where2park.controller.ManageLocationClass;
import com.example.where2park.model.Location;

import javafx.application.Application;
import javafx.stage.Stage;

//USECASE 1 TEST

public class AppLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        ManageLocationClass manager = new ManageLocationClass();

        // Step 1: Confirm location (this blocks)
        Location confirmed = manager.sendLocation();

        if (confirmed != null) {
            System.out.println("Confirmed Location: " + confirmed);
            manager.processUserLocation(confirmed);  // continue with your app flow
        } else {
            System.out.println("No location confirmed. Stopping or prompt manual input.");
            // Optionally close app or show manual input UI here
            primaryStage.close();
        }
    }
    public static void main(String[] args) {
        launch(args);  // Starts JavaFX Application Thread
    }

}



