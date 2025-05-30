package com.example.where2park.ui;

import com.example.where2park.model.Location;
import com.example.where2park.controller.ManageLocationClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmLocationScreen {

    private static ManageLocationClass manager;
    private static Location confirmedLocation = null;

    public static Location getConfirmedLocation() {
        return confirmedLocation;
    }

    public static void display(Location location, ManageLocationClass mgr) {
        manager = mgr;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Location");

        Label locationLabel = new Label("Is this your current location?\n" + location.getAddress());

        // Create WebView and load the map
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        double[] coords = geocodeLocation(location.getAddress());
        String mapUrl = "https://www.openstreetmap.org/?mlat=" + coords[0] + "&mlon=" + coords[1] + "#map=15/" + coords[0] + "/" + coords[1];
        webEngine.load(mapUrl);

        // Buttons
        Button confirmBtn = new Button("Yes, Confirm");
        confirmBtn.setOnAction(e -> {
            confirm(location, window);
        });

        Button rejectBtn = new Button("No, Enter Manually");
        rejectBtn.setOnAction(e -> {
            rejectLocation(window);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(webView, locationLabel, confirmBtn, rejectBtn);

        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void confirm(Location location, Stage window) {
        if (manager != null) {
            manager.setConfirmedLocation(location);
            confirmedLocation = location;
        }
        if (window != null) window.close();
    }

    public static void rejectLocation(Stage window) {
        if (manager != null) {
            manager.setConfirmedLocation(null);
            manager.locationRejected();  // Trigger manual entry flow
        }
        confirmedLocation = null;
        if (window != null) window.close();
    }

    // Geocoding helper
    private static double[] geocodeLocation(String location) {
        try {
            String encodedLocation = java.net.URLEncoder.encode(location, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedLocation + "&format=json&limit=1";

            java.net.URL url = new java.net.URL(urlStr);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Where2ParkApp/1.0");

            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String json = response.toString();
            if (json.startsWith("[") && json.length() > 10) {
                int latIndex = json.indexOf("\"lat\":\"");
                int lonIndex = json.indexOf("\"lon\":\"");

                if (latIndex != -1 && lonIndex != -1) {
                    latIndex += 7;
                    lonIndex += 7;

                    double lat = Double.parseDouble(json.substring(latIndex, json.indexOf("\"", latIndex)));
                    double lon = Double.parseDouble(json.substring(lonIndex, json.indexOf("\"", lonIndex)));
                    return new double[]{lat, lon};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Default fallback (Athens)
        return new double[]{37.9838, 23.7275};
    }
}
