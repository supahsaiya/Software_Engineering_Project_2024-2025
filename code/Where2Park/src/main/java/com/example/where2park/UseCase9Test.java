package com.example.where2park;

import com.example.where2park.controller.ManageInfoClass;
import com.example.where2park.ui.EditParkingScreen;
import javafx.application.Application;
import javafx.stage.Stage;

// USECASE 9 TEST

public class UseCase9Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load parking data from XML (simulate the system fetching info)
        var parking = ManageInfoClass.loadParkingData();

        // Open the EditParkingScreen
        EditParkingScreen.display();  // you should pass existing data if needed
    }

    public static void main(String[] args) {
        launch(args);
    }
}
