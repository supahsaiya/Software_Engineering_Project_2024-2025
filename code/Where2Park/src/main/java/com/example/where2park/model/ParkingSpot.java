package com.example.where2park.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ParkingSpot {


    // Update the in-memory availability
    public static int updateTemporarySpotList(Parking parking, int newAvailable) {
        parking.setCurrentlyAvailable(newAvailable);
        return newAvailable;
    }

    // Load parking by name from XML
    public static Parking loadFromXML(String parkingName) {
        try {
            File file = new File("src/main/data/parking.xml");
            if (!file.exists()) return null;

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();
                if (name.equalsIgnoreCase(parkingName)) {
                    double lat = Double.parseDouble(el.getElementsByTagName("lat").item(0).getTextContent());
                    double lon = Double.parseDouble(el.getElementsByTagName("lon").item(0).getTextContent());
                    String address = el.getElementsByTagName("address").item(0).getTextContent();
                    String tel = el.getElementsByTagName("tel").item(0).getTextContent();
                    int totalSpots = Integer.parseInt(el.getElementsByTagName("totalSpots").item(0).getTextContent());
                    int currentlyAvailable = Integer.parseInt(el.getElementsByTagName("currentlyAvailable").item(0).getTextContent());

                    return new Parking(name, lat, lon, address, tel, totalSpots, currentlyAvailable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fetch current availability only
    public static int fetchCurrentAvailability(String parkingName) {
        try {
            File file = new File("src/main/data/parkings.xml");
            if (!file.exists()) return -1;

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();
                if (name.equalsIgnoreCase(parkingName)) {
                    String availableStr = el.getElementsByTagName("currentlyAvailable").item(0).getTextContent();
                    return Integer.parseInt(availableStr);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Get all parking names
    public static List<String> getAllParkingNames() {
        List<String> parkingNames = new ArrayList<>();
        try {
            File file = new File("src/main/data/parking.xml");
            if (!file.exists()) return parkingNames;

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();
                parkingNames.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parkingNames;
    }

}
