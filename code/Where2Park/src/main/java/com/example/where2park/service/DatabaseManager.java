package com.example.where2park.service;

import com.example.where2park.model.Location;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class DatabaseManager {

    // For now, we "save" by just printing
    public void querySaveLocation(Location userLocation) {
        System.out.println("Saving user location: " + userLocation);
        // later: write to XML or real DB
    }

    public List<ParkingSpot> queryFindNearby(Location userLocation) {
        List<ParkingSpotDistance> spotsWithDistance = new ArrayList<>();
        double searchRadiusKm = 2.0;  // example radius

        try {
            File file = new File("src/main/data/parking.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodes = doc.getElementsByTagName("parking");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);

                String name = e.getElementsByTagName("name").item(0).getTextContent();
                double lat = Double.parseDouble(e.getElementsByTagName("lat").item(0).getTextContent());
                double lon = Double.parseDouble(e.getElementsByTagName("lon").item(0).getTextContent());

                double distance = calculateDistance(userLocation.getLatitude(), userLocation.getLongitude(), lat, lon);

                if (distance <= searchRadiusKm) {
                    spotsWithDistance.add(new ParkingSpotDistance(new ParkingSpot(name, lat, lon), distance));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sort by distance ascending
        spotsWithDistance.sort((a, b) -> Double.compare(a.distance, b.distance));

        // Extract sorted ParkingSpots
        List<ParkingSpot> sortedNearby = new ArrayList<>();
        for (ParkingSpotDistance psd : spotsWithDistance) {
            sortedNearby.add(psd.parkingSpot);
            System.out.println(psd.parkingSpot.getName() + " - Distance: " + psd.distance + " km");
        }

        return sortedNearby;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    // Helper class to store ParkingSpot + distance
    private static class ParkingSpotDistance {
        ParkingSpot parkingSpot;
        double distance;

        ParkingSpotDistance(ParkingSpot ps, double dist) {
            this.parkingSpot = ps;
            this.distance = dist;
        }
    }


    public static class ParkingSpot {
        public String name;
        public double lat;
        public double lon;

        public ParkingSpot(String name, double lat, double lon) {
            this.name = name;
            this.lat = lat;
            this.lon = lon;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name + " (" + lat + ", " + lon + ")";
        }
    }
}
