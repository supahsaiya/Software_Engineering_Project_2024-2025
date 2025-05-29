public class BookingArrivalConfirmationScreen {
    private Reservation selectedReservation;

    public BookingArrivalConfirmationScreen(Reservation reservation) {
        this.selectedReservation = reservation;
    }

    public void display() {
        System.out.println("Επιβεβαίωση Άφιξης για κράτηση: " + selectedReservation.getId());
    }

    public Reservation getReservation() {
        return selectedReservation;
    }

    public void showResults() {
        System.out.println("Αποτελέσματα για: " + selectedReservation.getId());
    }
}


import java.util.ArrayList;
import java.util.List;

public class DataStorageManager {
    private static List<Reservation> reservations = new ArrayList<>();

    public static void addReservation(Reservation r) {
        reservations.add(r);
    }

    public static List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public static void removeReservation(String id) {
        reservations.removeIf(r -> r.getId().equals(id));
    }
}

import java.util.List;
public class Employee {
    private String id;
    private String name;
    private Reservation currentReservation;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void manageReservationList() {
        MainMenu menu = new MainMenu();
        List<Reservation> reservations = menu.returnSelection();

        ReservationsListScreen screen = new ReservationsListScreen(reservations);
        screen.display();

        // simulate choosing one
        if (!reservations.isEmpty()) {
            chooseReservation(reservations.get(0));
        }
    }

    public void chooseReservation(Reservation reservation) {
        this.currentReservation = reservation;
        BookingArrivalConfirmationScreen screen = new BookingArrivalConfirmationScreen(reservation);
        screen.display();
        validateReservation();
    }

    public void validateReservation() {
        ManageReservationClass manager = new ManageReservationClass();
        Reservation reservation = manager.getReservation(currentReservation.getId());

        if (reservation != null && reservation.isValid()) {
            manager.decreasePosition();
            manager.removeReservationFromList(reservation.getId());
        } else {
            reportedProblem(reservation);
        }
    }

    public void reportedProblem(Reservation reservation) {
        ManageSearchListClass search = new ManageSearchListClass();
        search.sendMessage(reservation.getCustomerName());
        rejectReservation();
    }

    public void rejectReservation() {
        ManageReservationClass manager = new ManageReservationClass();
        manager.updateSystem();
        RejectBookingScreen screen = new RejectBookingScreen();
        screen.display();
    }
}

import java.util.List;
public class MainMenu {
    public List<Reservation> returnSelection() {
        return DataStorageManager.getReservations();
    }
}

import java.util.List;

public class ManageReservationClass {
    private List<Reservation> reservationList;

    public ManageReservationClass() {
        this.reservationList = DataStorageManager.getReservations();
    }

    public List<Reservation> getReservations() {
        return reservationList;
    }

    public Reservation getReservation(String id) {
        for (Reservation r : reservationList) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    public void decreasePosition() {
        System.out.println("Μείωση θέσης στη λίστα αναμονής.");
    }

    public void removeReservationFromList(String id) {
        DataStorageManager.removeReservation(id);
        System.out.println("Η κράτηση αφαιρέθηκε.");
    }

    public void updateSystem() {
        System.out.println("Το σύστημα ενημερώθηκε.");
    }
}

public class ManageSearchListClass {
    public void sendMessage(String customerName) {
        System.out.println("ΕΙΔΟΠΟΙΗΣΗ ΠΕΛΑΤΗ: " + customerName);
    }
}

public class RejectBookingScreen {
    public void display() {
        System.out.println("ΑΚΥΡΩΣΗ ΚΡΑΤΗΣΗΣ (ΔΕΝ ΕΧΕΙ ΥΛΟΠΟΙΗΘΕΙ Ή ΔΕΝ ΕΦΤΑΣΕ)");
    }
}

public class Reservation {
    private String id;
    private String customerName;
    private boolean valid;

    public Reservation(String id, String customerName, boolean valid) {
        this.id = id;
        this.customerName = customerName;
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }
}

import java.util.List;
public class ReservationsListScreen {
    private List<Reservation> reservations;

    public ReservationsListScreen(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void display() {
        showResults();
    }

    public void showResults() {
        for (Reservation r : reservations) {
            System.out.println("Κράτηση: " + r.getId() + " - " + r.getCustomerName());
        }
    }
}
