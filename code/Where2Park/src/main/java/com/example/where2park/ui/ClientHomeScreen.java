package com.example.where2park.ui;

import com.example.where2park.model.ParkingLot;
import com.example.where2park.service.DataStorageManager;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;

import javafx.stage.Modality;
import java.util.List;

public class ClientHomeScreen {



        private static ParkingLot selectedParking = null;

        public static void display(int userId) {
            Stage stage = new Stage();
            VBox root = new VBox(10);
            root.setStyle("-fx-padding: 20");

            Label welcomeLabel = new Label("Welcome back, Client #" + userId + "!");

            // Button to select parking lot
            Button selectParkingBtn = new Button("Select Parking Lot");
            selectParkingBtn.setOnAction(e -> {
                DataStorageManager dbManager = new DataStorageManager();
                List<ParkingLot> parkings = dbManager.getAllParkingLots();

                if (parkings.isEmpty()) {
                    System.out.println("No parking lots found.");
                    return;
                }

                ParkingLot selected = selectParking(parkings);
                if (selected != null) {
                    System.out.println("User selected parking lot: " + selected.getName());
                    // You can add more actions here if needed
                } else {
                    System.out.println("User cancelled parking selection.");
                }
            });

            root.getChildren().addAll(welcomeLabel, selectParkingBtn);

            stage.setScene(new Scene(root, 400, 300));
            stage.setTitle("Client Home");
            stage.show();
        }

        public static ParkingLot selectParking(List<ParkingLot> parkings) {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Select Parking");

            ListView<String> listView = new ListView<>();
            for (ParkingLot lot : parkings) {
                listView.getItems().add(lot.getName() + " (" + lot.getCurrentlyAvailable() + "/" + lot.getTotalSpots() + " available)");
            }

            Button confirmBtn = new Button("Confirm");
            confirmBtn.setDisable(true);

            listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
                confirmBtn.setDisable(newVal.intValue() < 0);
            });

            confirmBtn.setOnAction(e -> {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    selectedParking = parkings.get(index);
                    window.close();
                }
            });

            VBox layout = new VBox(10, new Label("Choose a parking lot:"), listView, confirmBtn);
            layout.setStyle("-fx-padding: 20;");
            window.setScene(new Scene(layout, 400, 300));
            window.showAndWait();

            return selectedParking;
        }



    public void showNearbyParkings() {
        new DataStorageManager().queryFindNearby(); // No need to pass anything
    }
}
