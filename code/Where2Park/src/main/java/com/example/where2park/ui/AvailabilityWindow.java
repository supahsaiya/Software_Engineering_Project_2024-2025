package com.example.where2park.ui;

import com.example.where2park.controller.ManageAvailabilityClass;
import com.example.where2park.model.ParkingLot;
import com.example.where2park.model.ParkingSpot;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AvailabilityWindow {

    private static Label currentAvailabilityLabel;
    private static Label totalSpotsLabel;



    /**
     * Displays the Availability Window for a given parking object.
     */
    public static void display(ParkingLot parking) {
        Stage stage = new Stage();
        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Availability for: " + parking.getName());
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        currentAvailabilityLabel = new Label();
        totalSpotsLabel = new Label();

        showCurrentAvailability(parking);
        showAvailableSpots(parking);

        root.getChildren().addAll(title, currentAvailabilityLabel, totalSpotsLabel);

        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Parking Availability");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays the number of currently available spots.
     */
    public static void showCurrentAvailability(ParkingLot parking) {
        if (currentAvailabilityLabel != null) {
            currentAvailabilityLabel.setText("Currently Available Spots: " + parking.getCurrentlyAvailable());
        }
    }

    /**
     * Displays the total number of parking spots.
     */
    public static void showAvailableSpots(ParkingLot parking) {
        if (totalSpotsLabel != null) {
            totalSpotsLabel.setText("Total Spots: " + parking.getTotalSpots());
        }
    }



    public static void updateAvailableSpots(ParkingLot parking) {
        Stage stage = new Stage();
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label title = new Label("Update Availability for: " + parking.getName());
        Label availability = new Label("Currently Available: " + parking.getCurrentlyAvailable());
        availability.setStyle("-fx-font-size: 14px;");

        Button increase = new Button("▲");
        Button decrease = new Button("▼");

        increase.setOnAction(e -> {
            int newValue = parking.getCurrentlyAvailable() + 1;
            ManageAvailabilityClass manager = new ManageAvailabilityClass();

            if (validateAvailabilityUpdate(parking, newValue)) {
                manager.validationSuccessful();
                int updated = ParkingSpot.updateTemporarySpotList(parking, newValue);
                //updateAvailabilityInXML(parking.getName(), updated);

                showConfirmationMessage("Updated successfully.");
                showNewAvailableSpots(availability, updated);
            } else {
                manager.capacityOverflow();
                //showErrorMessage("Cannot exceed capacity of " + parking.getTotalSpots());
                showCapacityMessage("Cannot exceed capacity of " + parking.getTotalSpots());
            }
        });



        decrease.setOnAction(e -> {
            int newValue = parking.getCurrentlyAvailable() - 1;
            ManageAvailabilityClass manager = new ManageAvailabilityClass();

            if (validateAvailabilityUpdate(parking, newValue)) {
                manager.validationSuccessful();
                int updated = ParkingSpot.updateTemporarySpotList(parking, newValue);
                //updateAvailabilityInXML(parking.getName(), updated);
                showConfirmationMessage("Updated successfully.");
                showNewAvailableSpots(availability, updated);
            } else {
                manager.capacityUnderflow();
                //showErrorMessage("Cannot have negative available spots.");
                showCapacityMessage("Cannot have negative available spots.");
            }
        });



        root.getChildren().addAll(title, availability, increase, decrease);

        stage.setScene(new Scene(root, 250, 200));
        stage.setTitle("Update Availability");
        stage.show();
    }


    private static boolean validateAvailabilityUpdate(ParkingLot parking, int newValue) {
        return newValue >= 0 && newValue <= parking.getTotalSpots();
    }
    public static void showConfirmationMessage(String message) {
        // Simple feedback for now
        System.out.println("[Confirmation] " + message);
    }

    public static void showNewAvailableSpots(Label label, int updated) {
        label.setText("Currently Available: " + updated);
    }

    /*
    private static void updateAvailabilityInXML(String parkingName, int newAvailable) {
        try {
            File file = new File("src/main/data/parking.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();

                if (name.equals(parkingName)) {
                    el.getElementsByTagName("currentlyAvailable").item(0).setTextContent(String.valueOf(newAvailable));
                    break;
                }
            }

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
    public static void showCapacityMessage(String message) {
        // A dedicated capacity-related error message display
        System.err.println("[Capacity Message] " + message);
    }
    /*
    public static void validateAvailabilityUpdate() {
        // Currently no validation logic required
        System.out.println(" validateAvailabilityUpdate() called.");
    }
     */
    /*
    public static void showErrorMessage(String message) {
        // You can replace this with a pop-up if needed
        System.err.println("[Error] " + message);
    }
*/

}
