package com.example.where2park.ui;

import com.example.where2park.service.DataStorageManager;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ClientHomeScreen {

    public static void display(int userId) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20");

        Label welcomeLabel = new Label("Welcome back, Client #" + userId + "!");
        // You can add more UI elements here for the client home page

        root.getChildren().add(welcomeLabel);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Client Home");
        stage.show();
    }
    public void showNearby() {
        new DataStorageManager().queryFindNearby(); // No need to pass anything
    }

}
