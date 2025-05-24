package com.example.where2park.service;

import com.example.where2park.model.Location;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DatabaseManager {

    // For now, we "save" by just printing
    public void querySaveLocation(Location userLocation) {
        System.out.println("Saving user location: " + userLocation);

        // Assuming you know the current user's ID and name:
        int userId = 1;  // or fetch dynamically
        String userName = "Alice";  // or fetch dynamically

        initializeOrUpdateUserData(userId, userName, userLocation);
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

    public void initializeOrUpdateUserData(int userId, String userName, Location location) {
        try {
            File file = new File("src/main/data/users.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            Element user;

            if (file.exists()) {
                doc = builder.parse(file);

                NodeList users = doc.getElementsByTagName("user");
                boolean userFound = false;

                for (int i = 0; i < users.getLength(); i++) {
                    Element u = (Element) users.item(i);
                    String idText = u.getElementsByTagName("id").item(0).getTextContent();
                    if (Integer.parseInt(idText) == userId) {
                        user = u;
                        userFound = true;

                        // Update location
                        Element locationElem = (Element) u.getElementsByTagName("location").item(0);
                        locationElem.setAttribute("status", "pending");
                        locationElem.getElementsByTagName("lat").item(0).setTextContent(String.valueOf(location.getLatitude()));
                        locationElem.getElementsByTagName("lon").item(0).setTextContent(String.valueOf(location.getLongitude()));
                        locationElem.getElementsByTagName("address").item(0).setTextContent(location.getAddress());

                        break;
                    }
                }
                if (!userFound) {
                    Element root = doc.getDocumentElement();

                    user = doc.createElement("user");
                    root.appendChild(user);

                    Element id = doc.createElement("id");
                    id.appendChild(doc.createTextNode(String.valueOf(userId)));
                    user.appendChild(id);

                    Element name = doc.createElement("name");
                    name.appendChild(doc.createTextNode(userName));
                    user.appendChild(name);

                    Element locationElem = doc.createElement("location");
                    locationElem.setAttribute("status", "pending");
                    user.appendChild(locationElem);

                    Element lat = doc.createElement("lat");
                    lat.appendChild(doc.createTextNode(String.valueOf(location.getLatitude())));
                    locationElem.appendChild(lat);

                    Element lon = doc.createElement("lon");
                    lon.appendChild(doc.createTextNode(String.valueOf(location.getLongitude())));
                    locationElem.appendChild(lon);

                    Element address = doc.createElement("address");
                    address.appendChild(doc.createTextNode(location.getAddress()));
                    locationElem.appendChild(address);
                }


                if (!userFound) {
                    System.out.println("⚠️ User ID not found.");
                }

            } else {
                // Create new document
                doc = builder.newDocument();
                Element root = doc.createElement("users");
                doc.appendChild(root);

                user = doc.createElement("user");
                root.appendChild(user);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(userId)));
                user.appendChild(id);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(userName));
                user.appendChild(name);

                Element locationElem = doc.createElement("location");
                locationElem.setAttribute("status", "pending");
                user.appendChild(locationElem);

                Element lat = doc.createElement("lat");
                lat.appendChild(doc.createTextNode(String.valueOf(location.getLatitude())));
                locationElem.appendChild(lat);

                Element lon = doc.createElement("lon");
                lon.appendChild(doc.createTextNode(String.valueOf(location.getLongitude())));
                locationElem.appendChild(lon);

                Element address = doc.createElement("address");
                address.appendChild(doc.createTextNode(location.getAddress()));
                locationElem.appendChild(address);
            }

            // Write changes to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("✅ users.xml initialized/updated with user ID " + userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
