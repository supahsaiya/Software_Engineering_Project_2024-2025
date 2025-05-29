public class Client {
    private String id;
    private String name;

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void showBasicInformation(ParkingSpot spot) {
        ParkingDetailsScreen screen = new ParkingDetailsScreen(spot);
        screen.showBasicInformation();
    }

    public void checkAvailability(ParkingSpot spot) {
        ParkingDetailsScreen screen = new ParkingDetailsScreen(spot);
        screen.showCurrentAvailability();
    }

    public void askInformation(ParkingSpot spot) {
        ParkingDetailsScreen screen = new ParkingDetailsScreen(spot);
        screen.askInformation();
    }

    public void bookLocation(ParkingSpot spot) {
        ConfirmBookingLocationScreen booking = new ConfirmBookingLocationScreen(spot);
        booking.display();
        booking.validateLocation();
    }

    public void showLastAvailability(ParkingSpot spot) {
        ParkingDetailsScreen screen = new ParkingDetailsScreen(spot);
        screen.showLastAvailability();
    }

    public void displayMessage(String msg, ParkingSpot spot) {
        ParkingDetailsScreen screen = new ParkingDetailsScreen(spot);
        screen.displayMessage(msg);
    }
}


public class ConfirmBookingLocationScreen {
    private ParkingSpot spot;

    public ConfirmBookingLocationScreen(ParkingSpot spot) {
        this.spot = spot;
    }

    public void display() {
        System.out.println("Επιβεβαίωση τοποθεσίας για κράτηση: " + spot.getLocation());
    }

    public void validateLocation() {
        if (spot.isAvailable()) {
            ManageParkingAvailabilityClass manage = new ManageParkingAvailabilityClass();
            manage.sendLocationToValidate(spot);
            redirectToClientHomeScreen();
        } else {
            rejectLocation();
        }
    }

    public void rejectLocation() {
        System.out.println("Η κράτηση απορρίφθηκε. Η θέση δεν είναι διαθέσιμη.");
    }

    public void redirectToClientHomeScreen() {
        System.out.println("Επιστροφή στην αρχική σελίδα πελάτη.");
    }
}

import java.util.ArrayList;
import java.util.List;

public class DataStorageManager {
    private static List<ParkingSpot> allSpots = new ArrayList<>();

    public static void addSpot(ParkingSpot spot) {
        allSpots.add(spot);
    }

    public static List<ParkingSpot> getAllSpots() {
        return new ArrayList<>(allSpots);
    }
}


public class ManageAvailabilityUpdateClass {
    private ParkingSpot spot;

    public ManageAvailabilityUpdateClass(ParkingSpot spot) {
        this.spot = spot;
    }

    public boolean getCurrentAvailability() {
        return spot.isAvailable();
    }

    public void checkAvailabilityUpdate() {
        // υποτιθέμενη ανανέωση κατάστασης
    }
}


public class ManageInformationClass {
    private ParkingSpot spot;

    public ManageInformationClass(ParkingSpot spot) {
        this.spot = spot;
    }

    public String retrieveBasicInformation() {
        return "Τοποθεσία: " + spot.getLocation();
    }

    public String retrieveAdditionalInformation() {
        return spot.getAdditionalInfo();
    }


public class ManageParkingAvailabilityClass {
    private ParkingSpot reservedSpot;

    public void sendLocationToValidate(ParkingSpot spot) {
        if (spot.isAvailable()) {
            reservedSpot = spot;
            spot.setAvailable(false);
            System.out.println("ΚΡΑΤΗΣΗ ΘΕΣΗΣ ΕΠΙΒΕΒΑΙΩΘΗΚΕ: " + spot.getLocation());
        } else {
            System.out.println("Η θέση δεν είναι πλέον διαθέσιμη.");
        }
    }
}


public class ParkingDetailsScreen {
    private ParkingSpot spot;

    public ParkingDetailsScreen(ParkingSpot spot) {
        this.spot = spot;
    }

    public void showBasicInformation() {
        ManageInformationClass info = new ManageInformationClass(spot);
        System.out.println(info.retrieveBasicInformation());
    }

    public void askInformation() {
        searchInformation();
    }

    public void searchInformation() {
        ManageInformationClass info = new ManageInformationClass(spot);
        showAdditionalInformation(info.retrieveAdditionalInformation());
    }

    public void showAdditionalInformation(String info) {
        System.out.println("Επιπλέον πληροφορία: " + info);
    }

    public void showCurrentAvailability() {
        ManageAvailabilityUpdateClass availability = new ManageAvailabilityUpdateClass(spot);
        boolean isAvailable = availability.getCurrentAvailability();
        System.out.println("Διαθεσιμότητα: " + (isAvailable ? "Ναι" : "Όχι"));
    }

    public void showLastAvailability() {
        System.out.println("Τελευταία γνωστή διαθεσιμότητα: " + (spot.isAvailable() ? "Ναι" : "Όχι"));
    }

    public void displayMessage(String msg) {
        System.out.println("Μήνυμα: " + msg);
    }
}


public class ParkingSpot {
    private String id;
    private String location;
    private boolean isAvailable;
    private String additionalInfo;

    public ParkingSpot(String id, String location, boolean isAvailable, String additionalInfo) {
        this.id = id;
        this.location = location;
        this.isAvailable = isAvailable;
        this.additionalInfo = additionalInfo;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getLocation() {
        return location;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getId() {
        return id;
    }
}
