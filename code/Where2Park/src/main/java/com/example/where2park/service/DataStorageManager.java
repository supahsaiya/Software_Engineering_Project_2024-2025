package com.example.where2park.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class DataStorageManager {

    public static int queryAvailableSpots(String parkingName) {
        try {
            File file = new File("src/main/data/parking.xml");
            if (!file.exists()) return 0;

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            NodeList parkings = doc.getElementsByTagName("parking");

            for (int i = 0; i < parkings.getLength(); i++) {
                Element el = (Element) parkings.item(i);
                String name = el.getElementsByTagName("name").item(0).getTextContent();

                if (name.equals(parkingName)) {
                    return Integer.parseInt(el.getElementsByTagName("totalSpots").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}

