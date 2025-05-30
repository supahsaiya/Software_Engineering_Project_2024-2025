package com.example.where2park.model;


public class ParkingLot {
    private String name;
    private double lat;
    private double lon;
    private String address;
    private String tel;
    private int totalSpots;
    private int currentlyAvailable;

    public ParkingLot(String name, double lat, double lon, String address, String tel, int totalSpots, int currentlyAvailable ) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.tel = tel;
        this.totalSpots = totalSpots;
        this.currentlyAvailable = currentlyAvailable;
    }

    /*
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
        return -1; // Error or not found
    }
    */
    /*
    public int updateTemporarySpotsList(int newAvailable) {
        this.setCurrentlyAvailable(newAvailable);
        return newAvailable;
    }
    */

    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public String getAddress() { return address; }
    public String getTel() { return tel; }
    public int getTotalSpots() { return totalSpots; }
    public int getCurrentlyAvailable() { return currentlyAvailable; }


    public void setCurrentlyAvailable(int currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }

    public void updateInfo() {

        System.out.println("updateInfo() called");
    }


     /*

    //HELPER CLASSES

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
        return null; // Not found or error
    }

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
*/
}
