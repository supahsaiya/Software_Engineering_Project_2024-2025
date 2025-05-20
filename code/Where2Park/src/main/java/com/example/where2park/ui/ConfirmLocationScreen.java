package com.example.where2park.ui;

import com.example.where2park.model.Location;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



public class ConfirmLocationScreen extends Application {


    private static Location locationToConfirm;

    public static void display(Location location) {
        locationToConfirm = location;
        Application.launch(ConfirmLocationScreen.class);
    }

    private static Location confirmedLocation = null;

    public static Location getConfirmedLocation() {
        return confirmedLocation;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Confirm Location");

        Label locationLabel = new Label("Is this your location?\n" + locationToConfirm.toString());


        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        //double[] coords = geocodeLocation(location);
        double[] coords = geocodeLocation(locationToConfirm.getAddress());

        String mapUrl = "https://www.openstreetmap.org/?mlat=" + coords[0] + "&mlon=" + coords[1] + "#map=15/" + coords[0] + "/" + coords[1];
        webEngine.load(mapUrl);


        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnAction(e -> {
            System.out.println("Location confirmed!");


            // TODO: save location logic here
            confirmedLocation = locationToConfirm;
            primaryStage.close();
        });

        Button rejectBtn = new Button("Reject");
        rejectBtn.setOnAction(e -> {
            System.out.println("Location rejected by user.");
            // TODO: open manual input screen here
            confirmedLocation = null;

            primaryStage.close();
        });



        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(webView, locationLabel, confirmBtn, rejectBtn);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
/// TEMPORARY GEOCODING METHOD
    private double[] geocodeLocation(String location) {
        try {
            String encodedLocation = java.net.URLEncoder.encode(location, "UTF-8");
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encodedLocation + "&format=json&limit=1";

            java.net.URL url = new java.net.URL(urlStr);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Where2ParkApp/1.0"); // required by Nominatim

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
                } else {
                    System.out.println("❌ Location not found in API response.");
                }
            } else {
                System.out.println("❌ Empty or malformed response.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Default fallback (Athens)
        return new double[]{37.9838, 23.7275};
    }


}
