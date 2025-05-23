package com.example.where2park.service;

import com.example.where2park.model.Location;

import java.util.Random;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GPSApi {

    private static final Location[] FAKE_LOCATIONS = {
            new Location(37.9785, 23.7267, "123 Aiolou St, Athens"),
            new Location(37.9761, 23.7313, "45 Ermou St, Athens"),
            new Location(37.9755, 23.7348, "9 Syntagma Square, Athens"),
            new Location(37.9885, 23.7319, "21 Patision Ave, Athens"),
            new Location(37.9814, 23.7281, "84 Panepistimiou St, Athens")
    };

    public Location detectLocation() {
        Random rand = new Random();
        int index = rand.nextInt(FAKE_LOCATIONS.length);
        return FAKE_LOCATIONS[index];
    }
    public static Location geocode(String address) { //if user adds location manually
        try {
            String encoded = URLEncoder.encode(address, StandardCharsets.UTF_8);
            String urlStr = "https://nominatim.openstreetmap.org/search?q=" + encoded + "&format=json&limit=1";

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setRequestProperty("User-Agent", "Where2ParkApp");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();

            String json = response.toString();
            if (json.startsWith("[") && json.contains("\"lat\"") && json.contains("\"lon\"")) {
                int latIndex = json.indexOf("\"lat\":\"") + 7;
                int lonIndex = json.indexOf("\"lon\":\"") + 7;
                double lat = Double.parseDouble(json.substring(latIndex, json.indexOf("\"", latIndex)));
                double lon = Double.parseDouble(json.substring(lonIndex, json.indexOf("\"", lonIndex)));
                return new Location(lat, lon, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
