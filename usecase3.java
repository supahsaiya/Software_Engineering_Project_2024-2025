//ManageParkingAvailabilityClass
public class ManageBookingClass {

    private DataBaseManager dataBaseManager;

    public ManageBookingClass(DataBaseManager dbManager) {
        this.dataBaseManager = dbManager;
    }

    public Client queryClientDetails(String userId) {
        return dataBaseManager.queryClientDetails(userId);
    }

    public double bookParkingSpot(ParkingSpot spot, String userId) {
        // Εκτελεί μόνο κράτηση και υπολογισμό ποσού
        spot.reserveParkingSpot(userId);
        return dataBaseManager.getAndCalculateAmount(spot);
    }
}

//BookingScreen

 public class BookingScreen {

    private String userId;
    private ManageBookingClass bookingManager;
    private ParkingSpot selectedSpot;

    public BookingScreen(String userId, ManageBookingClass bookingManager, ParkingSpot selectedSpot) {
        this.userId = userId;
        this.bookingManager = bookingManager;
        this.selectedSpot = selectedSpot;
    }

    public void display() {
       
    }

    public void showAvailabilityMessage(boolean isAvailable) {
        if (isAvailable) {
            System.out.println("Υπάρχουν διαθέσιμες θέσεις!");
        } else {
            System.out.println("Δεν υπάρχουν διαθέσιμες θέσεις.");
        }
    }

    public void startBooking() {
        // Αποστολή queryClientDetails() στο ManageBookingClass
        Client client = bookingManager.queryClientDetails(userId);
        System.out.println("Πελάτης: " + client.getName());

        // Αποστολή bookParkingSpot() στο ManageBookingClass
        double amount = bookingManager.bookParkingSpot(selectedSpot, userId);

        // Δημιουργία ConfirmBookingScreen (<<create>>)
        ConfirmBookingScreen confirmScreen = new ConfirmBookingScreen(userId, selectedSpot.getSpotId(), amount);
    }
}


//ConfirmBookingScreen

public class ConfirmBookingScreen {

    private String userId;
    private String spotId;
    private double amount;
    private Reservation reservation;

    public ConfirmBookingScreen(String userId, String spotId, double amount) {
        this.userId = userId;
        this.spotId = spotId;
        this.amount = amount;
        this.reservation = new Reservation(userId, spotId); // σύνθεση
    }

    public void display() {
        System.out.println("🧾 Επιβεβαίωση Κράτησης");
        System.out.println("Θέση: " + spotId);
        System.out.println("Χρήστης: " + userId);
    }

    public void showAmount() {
        System.out.println("Ποσό προς πληρωμή: " + amount + " €");
    }

    public void confirmBooking(boolean useRedemption) {
        if (useRedemption) {
            System.out.println("Επιβεβαίωση με εξαργύρωση πόντων.");
        } else {
            System.out.println("Επιβεβαίωση χωρίς εξαργύρωση.");
        }
        reservation.makeReservation();  // αποθηκεύεται η κράτηση
    }

    public void rejectRedemption() {
        System.out.println("Απόρριψη εξαργύρωσης. Προχωράμε χωρίς πόντους.");
        reservation.makeReservation();  // η κράτηση γίνεται κανονικά
    }
}

//ParkingSpot


public class ParkingSpot {

    private String spotId;
    private String zone; // π.χ. Standard, Premium
    private boolean isReserved = false;
    private String reservedByUserId;

    public ParkingSpot(String spotId, String zone) {
        this.spotId = spotId;
        this.zone = zone;
    }

    public boolean checkAvailability() {
        if (!isReserved) {
            System.out.println("Availability: YES for spot " + spotId);
            return true;
        } else {
            System.out.println("Availability: NO for spot " + spotId);
            return false;
        }
    }

    public void reserveParkingSpot(String userId) {
        this.isReserved = true;
        this.reservedByUserId = userId;
    }

    public void releaseBooking() {
        this.isReserved = false;
        this.reservedByUserId = null;
        System.out.println("Booking released for spot: " + spotId);
    }

    public String getSpotId() {
        return spotId;
    }

    public String getZone() {
        return zone;
    }

}


//DataBaseManager 
public class DataBaseManager {

    public Client queryClientDetails(String userId) {
       
    }

    public double getAndCalculateAmount(ParkingSpot spot) {
        String zone = spot.getZone();
        double baseAmount = 5.0;

        if (zone.equalsIgnoreCase("Premium")) {
            return baseAmount + 2.0;
        } else if (zone.equalsIgnoreCase("Economy")) {
            return baseAmount - 1.0;
        } else {
            return baseAmount; // Standard
        }
    }
}



//Client

public class Client {

    private String id;
    private String name;
    private int loyaltyPoints;

    public Client(String id, String name, int loyaltyPoints) {
        this.id = id;
        this.name = name;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
}


