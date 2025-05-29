public class Employee {
    private String employeeId;
    private String name;
    private EmployeeHomeScreen homeScreen;

    public Employee(String employeeId, String name, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.homeScreen = new EmployeeHomeScreen();
    }

    public void login() {
        System.out.println("Welcome, " + name + " (Role: " + role + ")");
        homeScreen.display();
    }

    public void startClientSearch() {
        homeScreen.selectConnection();  // Εμφανίζει τους πελάτες με κρατήσεις
    }

    public void notifySelectedClient() {
        String selectedClientId = homeScreen.getSelectedClientId(); // παίρνουμε από το UI
        if (selectedClientId != null) {
            ClientProfileScreen profileScreen = new ClientProfileScreen();
            profileScreen.display();
            profileScreen.showProfile(selectedClientId);
            profileScreen.selectSendNotification(); // Αποστολή ειδοποίησης και έλεγχος κατάστασης
        } else {
            System.out.println("No client selected.");
        }
    }
}


public class EmployeeHomeScreen {
    public void display() {
        System.out.println("Employee Home Screen Displayed");
    }

    public void selectConnection() {
        ManageSearchListClass searchManager = new ManageSearchListClass();
        searchManager.searchClients();
    }
}


public class ManageSearchListClass {
    private DataStorageManager dataStorageManager = new DataStorageManager();
    private String selectedClientId;  // Νέο attribute

    public void searchClients() {
        var clients = dataStorageManager.queryClientsWithReservation();
        ReservationsListScreen screen = new ReservationsListScreen();
        screen.showClients(clients);
        selectedClientId = screen.selectClient();  // κρατάμε επιλεγμένο πελάτη
    }

    public void searchNumberPhone(String clientId) {
        String number = dataStorageManager.queryNumberPhone(clientId);
        ShareInfoScreen shareScreen = new ShareInfoScreen();
        shareScreen.display();
        shareScreen.showNumber(number);

        ManageNotificationClass notificationManager = new ManageNotificationClass();
        notificationManager.sharingYourInfo();
    }

    public String getSelectedClientId() {
        return selectedClientId;
    }
}

public class ReservationsListScreen {
    private List<String> clients;  // Νέο attribute

    public void display() {
        System.out.println("Displaying Reservations List Screen...");
    }

    public void showClients(List<String> clients) {
        this.clients = clients;
        display();
        System.out.println("List of clients with reservations:");
        for (String client : clients) {
            System.out.println("- " + client);
        }
    }

    public String selectClient() {
        System.out.println("Selecting client from the list...");
        return clients != null && !clients.isEmpty() ? clients.get(0) : "clientID";
    }
}


public class ClientProfileScreen {
    private DataStorageManager dataStorageManager = new DataStorageManager();
    private ManageNotificationClass notificationManager = new ManageNotificationClass();
    private String currentClientId;
    private String currentProfileInfo;

    public void display() {
        System.out.println("Client Profile Screen Displayed");
    }

    public void showProfile(String clientId) {
        this.currentClientId = clientId;
        this.currentProfileInfo = dataStorageManager.queryClientProfile(clientId);
        System.out.println("Profile Info: " + currentProfileInfo);
    }

    public void selectSendNotification() {
        String message = dataStorageManager.queryParkingDetailsAndNotification(currentClientId);
        notificationManager.sendMessage(message);

        ClientNotificationScreen notificationScreen = new ClientNotificationScreen();
        notificationScreen.display();

        if (notificationScreen.confirmStatus()) {
            notificationScreen.showDeliveredAndRead();
        } else {
            notificationScreen.showUnreadable();
        }
    }
}


public class ClientNotificationScreen {

    public void display() {
        System.out.println("Displaying Client Notification Screen...");
    }

    public void showDeliveredAndRead() {
        System.out.println(" Message was delivered and read.");
    }

    public void showUnreadable() {
        System.out.println(" Message was not read (unreadable).");
    }

    public boolean confirmStatus() {
        // Προσομοίωση ελέγχου κατάστασης ειδοποίησης
        // Θα μπορούσε να ελέγχει βάση δεδομένων, flags, logs κτλ.
        return true;  // ή false για άλλες περιπτώσεις
    }
}


import java.util.*;

public class DataStorageManager {
    public List<String> queryClientsWithReservation() {
        return Arrays.asList("Client A", "Client B");
    }

    public String queryClientProfile(String clientId) {
        return "Profile for " + clientId;
    }

    public String queryParkingDetailsAndNotification(String clientId) {
        return "Parking info and notification message";
    }

    public String queryNumberPhone(String clientId) {
        return "+302101112233";
    }
}

public class ManageNotificationClass {

    public void sendMessage(String message) {
        System.out.println("Sending message to client...");
        System.out.println("Message: " + message);
        // Εδώ μπορεί να υπάρχει λογική αποστολής SMS, email κ.λπ.
    }

    public void sharingYourInfo() {
        System.out.println("Notification has been shared with the client.");
        // Ενημερώνει ή καταγράφει την πράξη διαμοιρασμού πληροφορίας
    }
}

public class ShareInfoScreen {

    public void display() {
        System.out.println("Displaying Share Info Screen...");
    }

    public void showNumber(String phoneNumber) {
        System.out.println("Client Phone Number: " + phoneNumber);
    }
}

