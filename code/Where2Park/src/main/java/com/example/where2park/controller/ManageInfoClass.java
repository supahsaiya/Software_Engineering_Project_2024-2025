package com.example.where2park.controller;

import com.example.where2park.model.Parking;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ManageInfoClass {
    private static final String FILE_PATH = "src/main/data/parking.xml";

    // searchInfo() - loads current data
    public static Parking searchInfo() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_PATH));
            doc.getDocumentElement().normalize();

            return new Parking(
                    getText(doc, "name"),
                    Double.parseDouble(getText(doc, "lat")),
                    Double.parseDouble(getText(doc, "lon")),
                    getText(doc, "address"),
                    getText(doc, "tel"),
                    Integer.parseInt(getText(doc, "totalSpots")),
                    Integer.parseInt(getText(doc, "currentlyAvailable"))
            );
        } catch (Exception e) {
            errorFound("Σφάλμα κατά τη φόρτωση των πληροφοριών: " + e.getMessage());
            return null;
        }
    }

    // updateInfo() - saves modified data
    public static boolean updateInfo(Parking data) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_PATH));

            updateText(doc, "name", data.getName());
            updateText(doc, "address", data.getAddress());
            updateText(doc, "tel", data.getTel());
            updateText(doc, "totalSpots", String.valueOf(data.getTotalSpots()));

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
    private static String getText(Document doc, String tag) {
        return doc.getElementsByTagName(tag).item(0).getTextContent();
    }

    private static void updateText(Document doc, String tag, String newValue) {
        doc.getElementsByTagName(tag).item(0).setTextContent(newValue);
    }
}
