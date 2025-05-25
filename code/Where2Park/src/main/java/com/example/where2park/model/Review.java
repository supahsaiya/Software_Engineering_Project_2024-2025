package com.example.where2park.model;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Review {
    private String parkingSpot;
    private int userId;
    private String bookingDate;
    private int stars;
    private String text;

    public Review(String parkingSpot, int userId, String bookingDate, int stars, String text) {
        this.parkingSpot = parkingSpot;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.stars = stars;
        this.text = text;
    }

    // Getters
    public String getParkingSpot() { return parkingSpot; }
    public int getUserId() { return userId; }
    public String getBookingDate() { return bookingDate; }
    public int getStars() { return stars; }
    public String getText() { return text; }

    //Moved here as per sequence diagram
    public boolean updateReviewList() {
        try {
            File file = new File("src/main/data/reviews.xml");

            Document doc;
            Element root;

            if (!file.exists()) {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                root = doc.createElement("reviews");
                doc.appendChild(root);
            } else {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                root = doc.getDocumentElement();
            }

            Element reviewElem = doc.createElement("review");

            Element parkingElem = doc.createElement("parkingSpot");
            parkingElem.setTextContent(parkingSpot);
            reviewElem.appendChild(parkingElem);

            Element userElem = doc.createElement("userId");
            userElem.setTextContent(String.valueOf(userId));
            reviewElem.appendChild(userElem);

            Element dateElem = doc.createElement("bookingDate");
            dateElem.setTextContent(bookingDate);
            reviewElem.appendChild(dateElem);

            Element starsElem = doc.createElement("stars");
            starsElem.setTextContent(String.valueOf(stars));
            reviewElem.appendChild(starsElem);

            Element textElem = doc.createElement("text");
            textElem.setTextContent(text);
            reviewElem.appendChild(textElem);

            root.appendChild(reviewElem);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(file));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
