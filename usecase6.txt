public class UseCase06 {

    // Κλάση 1: Client
    static class Client {
        private String clientId;
        private String name;
        private int totalPoints;

        public Client(String clientId, String name) {
            this.clientId = clientId;
            this.name = name;
            this.totalPoints = 0;
        }

        public String getClientId() {
            return clientId;
        }

        public String getName() {
            return name;
        }

        public int getTotalPoints() {
            return totalPoints;
        }

        public void addPoints(int points) {
            totalPoints += points;
            System.out.println("Client " + name + " now has " + totalPoints + " points.");
        }

        public void displayClientInfo() {
            System.out.println("Client ID: " + clientId);
            System.out.println("Name: " + name);
            System.out.println("Total Points: " + totalPoints);
        }
    }

    // Κλάση 2: PaymentCompletionScreen
    static class PaymentCompletionScreen {
        private ManageCalculatePointsClass pointManager;

        public PaymentCompletionScreen(ManageCalculatePointsClass manager) {
            this.pointManager = manager;
        }

        public void makePayment() {
            System.out.println("Payment completed.");
            pointManager.sendPaymentInfo();
        }
    }

    // Κλάση 3: ManageCalculatePointsClass
    static class ManageCalculatePointsClass {
        private DataStorageManager dataStorage;

        public ManageCalculatePointsClass(DataStorageManager manager) {
            this.dataStorage = manager;
        }

        public void sendPaymentInfo() {
            int points = calculatePoints();
            EarnedPointsScreen earnedPointsScreen = new EarnedPointsScreen();
            earnedPointsScreen.display();
            earnedPointsScreen.showEarnedPoints(points);

            confirmPointsAcceptance(points);
        }

        public int calculatePoints() {
            return 100; // Παράδειγμα
        }

        public void confirmPointsAcceptance(int points) {
            int total = dataStorage.updateTotalPoints(points);

            EarnedPointsScreen screen = new EarnedPointsScreen();
            screen.acceptPoints();
            screen.totalPointsCalculated(total);
        }
    }

    // Κλάση 4: EarnedPointsScreen
    static class EarnedPointsScreen {
        private int earnedPoints;

        public void display() {
            System.out.println("Displaying earned points screen...");
        }

        public void showEarnedPoints(int points) {
            this.earnedPoints = points;
            System.out.println("You have earned " + points + " points.");
        }

        public void acceptPoints() {
            System.out.println("Points accepted by user.");
        }

        public void totalPointsCalculated(int total) {
            System.out.println("Your new total points: " + total);
        }

        public void viewPointsBalanceAndHistory() {
            ViewPointsAndHistory viewScreen = new ViewPointsAndHistory();
            viewScreen.display();
        }

        public void skipPointsDisplay() {
            ClientHomeScreen home = new ClientHomeScreen();
            home.display();
        }
    }

    // Κλάση 5: DataStorageManager
    static class DataStorageManager {
        private int totalPoints = 0;

        public int updateTotalPoints(int earnedPoints) {
            totalPoints += earnedPoints;
            System.out.println("Points updated in database. New total: " + totalPoints);
            return totalPoints;
        }

        public int getTotalPoints() {
            return totalPoints;
        }
    }

    // Κλάση 6: ClientHomeScreen
    static class ClientHomeScreen {
        public void display() {
            System.out.println("Welcome to the Client Home Screen.");
        }
    }

    // Κλάση 7: ViewPointsAndHistory
    static class ViewPointsAndHistory {
        public void display() {
            System.out.println("Viewing points balance and history...");
        }
    }

    // Main για δοκιμή
    public static void main(String[] args) {
        Client client = new Client("C001", "Maria Papadopoulou");
        client.displayClientInfo();

        DataStorageManager db = new DataStorageManager();
        ManageCalculatePointsClass manager = new ManageCalculatePointsClass(db);
        PaymentCompletionScreen screen = new PaymentCompletionScreen(manager);

        screen.makePayment();

        client.addPoints(100); // Προσθήκη πόντων χειροκίνητα (ενδεικτικά)
    }
}
