package com.example.where2park.controller;

import com.example.where2park.model.ParkingLot;
import com.example.where2park.service.DataStorageManager;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ManageInfoClass {
    private static final String FILE_PATH = "src/main/data/parking.xml";

    // searchInfo() - loads current data
    public static ParkingLot searchInfo() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_PATH));
            doc.getDocumentElement().normalize();

            return new ParkingLot(
                    DataStorageManager.querySearch(doc, "name"),
                    Double.parseDouble(DataStorageManager.querySearch(doc, "lat")),
                    Double.parseDouble(DataStorageManager.querySearch(doc, "lon")),
                    DataStorageManager.querySearch(doc, "address"),
                    DataStorageManager.querySearch(doc, "tel"),
                    Integer.parseInt(DataStorageManager.querySearch(doc, "totalSpots")),
                    Integer.parseInt(DataStorageManager.querySearch(doc, "currentlyAvailable"))
            );
        } catch (Exception e) {
            errorFound("Σφάλμα κατά τη φόρτωση των πληροφοριών: " + e.getMessage());
            return null;
        }
    }


    // updateInfo() - saves modified data
    public static boolean updateInfo(ParkingLot data) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_PATH));

            DataStorageManager.queryUpdate(doc, "name", data.getName());
            DataStorageManager.queryUpdate(doc, "address", data.getAddress());
            DataStorageManager.queryUpdate(doc, "tel", data.getTel());
            DataStorageManager.queryUpdate(doc, "totalSpots", String.valueOf(data.getTotalSpots()));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FILE_PATH)));

            return true;
        } catch (Exception e) {
            errorFound("Αποτυχία αποθήκευσης: " + e.getMessage());
            return false;
        }
    }


    // errorFound() - handles and prints errors (optionally log/alert)
    private static void errorFound(String message) {
        System.err.println("[ManageInfoClass] " + message);
        // Future: log to file or alert UI
    }

    // Helpers
    /*
    private static String getText(Document doc, String tag) {
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }

    private static void updateText(Document doc, String tag, String newValue) {
        doc.getElementsByTagName(tag).item(0).setTextContent(newValue);
    }

     */
}
