package com.example.where2park.service;

import com.example.where2park.model.Client;
import com.example.where2park.model.Location;
import com.example.where2park.model.Booking;

import com.example.where2park.model.Parking;
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

    public static List<String> queryStatisticalCategories() {
        return Arrays.asList("Κρατήσεις", "Έσοδα");
    }

    public static String queryStatistics(String category, String filter) {
        if (category.equals("Κρατήσεις") && filter.equals("Ημέρα")) {
            return "Στατιστικά Κρατήσεων ανά Ημέρα:\nΔευτέρα: 10\nΤρίτη: 12\nΤετάρτη: 15";
        } else if (category.equals("Έσοδα") && filter.equals("Μήνας")) {
            return "Στατιστικά Εσόδων ανά Μήνα:\nΙανουάριος: 800€\nΦεβρουάριος: 950€";
        }
        return null; // Simulate no data (Step 5a.1)
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

    public static List<Booking> queryUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();

        try {
            File file = new File("src/main/data/bookings.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList list = doc.getElementsByTagName("booking");

            for (int i = 0; i < list.getLength(); i++) {
                Element el = (Element) list.item(i);

                int uid = Integer.parseInt(el.getElementsByTagName("userId").item(0).getTextContent());
                String spot = el.getElementsByTagName("parkingSpot").item(0).getTextContent();
                String date = el.getElementsByTagName("date").item(0).getTextContent();

                if (uid == userId && !hasReview(uid, spot, date)) {
                    bookings.add(new Booking(spot, date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }


    public static String queryReviewDetails(String parkingName) {
        StringBuilder sb = new StringBuilder();

        try {
            File xmlFile = new File("src/main/data/bookings.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList list = doc.getElementsByTagName("parking");

            for (int i = 0; i < list.getLength(); i++) {
                Element parking = (Element) list.item(i);
                String name = parking.getElementsByTagName("name").item(0).getTextContent();

                if (name.equals(parkingName)) {
                    String lat = parking.getElementsByTagName("lat").item(0).getTextContent();
                    String lon = parking.getElementsByTagName("lon").item(0).getTextContent();
                    String address = parking.getElementsByTagName("address").item(0).getTextContent();

                    sb.append("Name: ").append(name).append("\n")
                            .append("Address: ").append(address).append("\n")
                            .append("Latitude: ").append(lat).append("\n")
                            .append("Longitude: ").append(lon).append("\n");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static void saveReview(String parkingName, int userId, String bookingDate, int stars, String text) {
        try {
            File file = new File("src/main/data/reviews.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc;
            Element root;

            if (!file.exists()) {
                doc = builder.newDocument();
                root = doc.createElement("reviews");
                doc.appendChild(root);
            } else {
                doc = builder.parse(file);
                root = doc.getDocumentElement();
            }

            Element review = doc.createElement("review");

            Element uid = doc.createElement("userId");
            uid.setTextContent(String.valueOf(userId));
            review.appendChild(uid);

            Element name = doc.createElement("parkingName");
            name.setTextContent(parkingName);
            review.appendChild(name);

            Element date = doc.createElement("bookingDate");
            date.setTextContent(bookingDate);
            review.appendChild(date);

            Element starEl = doc.createElement("stars");
            starEl.setTextContent(String.valueOf(stars));
            review.appendChild(starEl);

            Element txt = doc.createElement("text");
            txt.setTextContent(text);
            review.appendChild(txt);

            root.appendChild(review);

            // Save with indentation
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.transform(new DOMSource(doc), new StreamResult(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean hasReview(int userId, String parkingName, String date) {
        try {
            File file = new File("src/main/data/reviews.xml");
            if (!file.exists()) return false;

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList reviews = doc.getElementsByTagName("review");

            for (int i = 0; i < reviews.getLength(); i++) {
                Element el = (Element) reviews.item(i);

                int uid = Integer.parseInt(el.getElementsByTagName("userId").item(0).getTextContent());
                String pName = el.getElementsByTagName("parkingName").item(0).getTextContent();
                String bDate = el.getElementsByTagName("bookingDate").item(0).getTextContent();

                if (uid == userId && pName.equals(parkingName) && bDate.equals(date)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Parking getParkingByName(String name) {
        try {
            File file = new File("src/main/data/parking.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList list = doc.getElementsByTagName("parking");

            for (int i = 0; i < list.getLength(); i++) {
                Element el = (Element) list.item(i);
                String pName = el.getElementsByTagName("name").item(0).getTextContent();

                if (pName.equals(name)) {
                    double lat = Double.parseDouble(el.getElementsByTagName("lat").item(0).getTextContent());
                    double lon = Double.parseDouble(el.getElementsByTagName("lon").item(0).getTextContent());
                    String address = el.getElementsByTagName("address").item(0).getTextContent();
                    String tel = el.getElementsByTagName("tel").item(0).getTextContent();

                    int totalSpots = Integer.parseInt(el.getElementsByTagName("totalSpots").item(0).getTextContent());
                    int currentlyAvailable = Integer.parseInt(el.getElementsByTagName("currentlyAvailable").item(0).getTextContent());

                    return new Parking(pName, lat, lon, address, tel, totalSpots, currentlyAvailable);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Client getClientById(int userId) {
        try {
            File file = new File("src/main/data/users.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element userElem = (Element) users.item(i);

                int id = Integer.parseInt(userElem.getElementsByTagName("id").item(0).getTextContent());
                if (id == userId) {
                    String name = userElem.getElementsByTagName("name").item(0).getTextContent();
                    return new Client(id, name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public static List<Parking> loadParkings() {
        List<Parking> parkingList = new ArrayList<>();
        try {
            File file = new File("src/main/data/parking.xml");
            if (!file.exists()) return parkingList;

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();
                String address = el.getElementsByTagName("address").item(0).getTextContent();
                String tel = el.getElementsByTagName("tel").item(0).getTextContent();
                int totalSpots = Integer.parseInt(el.getElementsByTagName("totalSpots").item(0).getTextContent());
                int available = Integer.parseInt(el.getElementsByTagName("currentlyAvailable").item(0).getTextContent());
                double lat = 0.0, lon = 0.0; // You can extract from XML if needed

                Parking p = new Parking(name, lat, lon, address, tel, totalSpots, available);
                parkingList.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingList;
    }

    //Additional classes

    // Helper class to store ParkingSpot + distance
    private static class ParkingSpotDistance {
        ParkingSpot parkingSpot;
        double distance;

        ParkingSpotDistance(ParkingSpot ps, double dist) {
            this.parkingSpot = ps;
            this.distance = dist;
        }
    }

    //NOT IN USE ANYMORE
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
