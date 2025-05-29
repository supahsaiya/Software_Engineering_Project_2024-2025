public class Client {
    private int clientId;
    private String name;
    private ConfirmBookingScreen bookingScreen;

    public Client(int clientId, String name, String reservationId, String date, double amount, DataStorageManager storage) {
        this.clientId = clientId;
        this.name = name;
        this.bookingScreen = new ConfirmBookingScreen(reservationId, date, amount, storage);
    }

    public void selectRedemption() {
        System.out.println("Ο πελάτης " + name + " ξεκινά εξαργύρωση πόντων.");
        bookingScreen.selectRedemption(clientId);
    }

    public int getClientId() {
        return clientId;
    }
}

public class ConfirmBookingScreen {
    private Reservation reservation; // Composition
    private ManagePointClass pointManager;

    public ConfirmBookingScreen(String reservationId, String date, double amount, DataStorageManager storage) {
        this.reservation = new Reservation(reservationId, date, amount);
        this.pointManager = new ManagePointClass(this.reservation, storage); // composition to PaymentMethodScreen
    }

    public void selectRedemption(int clientId) {
        System.out.println("Επιλέχθηκε εξαργύρωση για κράτηση: " + reservation.getReservationId());
        pointManager.checkPoints(clientId);
    }

    public class Reservation {
        private String reservationId;
        private String date;
        private double amount;

        public Reservation(String reservationId, String date, double amount) {
            this.reservationId = reservationId;
            this.date = date;
            this.amount = amount;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }
    }
}

public class ManagePointClass {
    private PaymentMethodScreen paymentScreen;
    private DataStorageManager storage;
    private ConfirmBookingScreen.Reservation reservation;

    public ManagePointClass(ConfirmBookingScreen.Reservation reservation, DataStorageManager storage) {
        this.reservation = reservation;
        this.storage = storage;
        this.paymentScreen = new PaymentMethodScreen(reservation); // composition
    }

    public void checkPoints(int clientId) {
        int points = storage.queryPoints(clientId);

        if (points > 0) {
            ViewPointsScreen viewScreen = new ViewPointsScreen();
            viewScreen.display();
            viewScreen.showPoints(points);
            int usedPoints = viewScreen.selectNumberOfPoints();
            int updatedPoints = storage.queryUpdatePoints(clientId, usedPoints);
            viewScreen.showNewPoints(updatedPoints);

            CalculateCostScreen costScreen = new CalculateCostScreen();
            costScreen.display();
            costScreen.showNewCost(usedPoints);

            paymentScreen.selectMethod(); // σύνδεση με επόμενο βήμα πληρωμής
        } else {
            ErrorMessageScreen error = new ErrorMessageScreen(reservation.getAmount());
            error.display();
            error.retrieveOldCost();
            error.showOldCost();
            paymentScreen.selectMethod(); // fallback σε πληρωμή χωρίς πόντους
        }
    }
}

public class ViewPointsScreen {
    public void display() {
        System.out.println("Εμφάνιση διαθέσιμων πόντων...");
    }

    public void showPoints(int points) {
        System.out.println("Διαθέσιμοι πόντοι: " + points);
    }

    public int selectNumberOfPoints() {
        int used = 50;
        System.out.println("Επιλέχθηκαν " + used + " πόντοι.");
        return used;
    }

    public void showNewPoints(int updated) {
        System.out.println("Υπόλοιπο πόντων μετά την εξαργύρωση: " + updated);
    }
}

public class CalculateCostScreen {
    public void display() {
        System.out.println("Υπολογισμός κόστους...");
    }

    public double showNewCost(int points) {
        double discount = points * 0.1;
        double finalCost = 100.0 - discount;
        System.out.println("Κόστος μετά την εξαργύρωση: " + finalCost + "€");
        return finalCost;
    }

    public void showOldCost(double cost) {
        System.out.println("Επιστροφή σε αρχικό κόστος: " + cost + "€");
    }
}

public class ErrorMessageScreen {
    private double oldCost;

    public ErrorMessageScreen(double oldCost) {
        this.oldCost = oldCost;
    }

    public void display() {
        System.out.println("⚠ Σφάλμα: Δεν υπάρχουν διαθέσιμοι πόντοι.");
    }

    public double retrieveOldCost() {
        return oldCost;
    }

}

import java.util.HashMap;
import java.util.Map;

public class DataStorageManager {
    private Map<Integer, Integer> clientPoints;

    public DataStorageManager() {
        clientPoints = new HashMap<>();
        clientPoints.put(1, 100); // Client με 100 πόντους
    }

    public int queryPoints(int clientId) {
        return clientPoints.getOrDefault(clientId, 0);
    }

    public int queryUpdatePoints(int clientId, int usedPoints) {
        int remaining = clientPoints.getOrDefault(clientId, 0) - usedPoints;
        clientPoints.put(clientId, remaining);
        return remaining;
    }


public class PaymentMethodScreen {
    private ConfirmBookingScreen.Reservation reservation;

    public PaymentMethodScreen(ConfirmBookingScreen.Reservation reservation) {
        this.reservation = reservation;
    }

    public void selectMethod() {
        System.out.println("Επιλογή μεθόδου πληρωμής για κράτηση: " + reservation.getReservationId());
    }
}
