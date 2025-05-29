public class Client {
    private String name;
    private String email;
    private PaymentMethodsScreen paymentMethodsScreen;

    public Client() {
        this.paymentMethodsScreen = new PaymentMethodsScreen();
    }

  }

public class PaymentMethodsScreen {
    private List<String> availableMethods; // π.χ. Visa, PayPal, MasterCard
    private ManageCardsClass manageCards;

    public PaymentMethodsScreen() {
        this.availableMethods = new ArrayList<>();
        this.manageCards = new ManageCardsClass();
    }

    public void selectMethod() {
        System.out.println("PaymentMethodsScreen: Ο πελάτης επιλέγει τρόπο πληρωμής.");
        manageCards.searchMethod();
    }
}


public class ManageCardsClass {
    private Cards cards;
    private BankAPI bankAPI;
    private boolean bankValidationStatus;

    public ManageCardsClass() {
        this.cards = new Cards();
        this.bankAPI = new BankAPI();
        this.bankValidationStatus = false;
    }

    public void searchMethod() {
        System.out.println("ManageCardsClass: Έλεγχος για αποθηκευμένες κάρτες...");
        boolean hasSavedMethods = cards.queryPersonalMethods();

        if (!hasSavedMethods) {
            AddCardScreen addCardScreen = new AddCardScreen();
            addCardScreen.display();
            addCardScreen.showNomethods();
            addCardScreen.addCard();
        }

        sendMessage();
    }

    public void sendMessage() {
        System.out.println("ManageCardsClass: Λαμβάνουμε τα στοιχεία από την τράπεζα...");

        bankAPI.transferToBank();
        this.bankValidationStatus = bankAPI.validateBankDetails();

        if (bankValidationStatus) {
            System.out.println("ManageCardsClass: Τα στοιχεία είναι έγκυρα.");
            System.out.println("BankAPI: " + bankAPI.getMessage());
        } else {
            System.out.println("ManageCardsClass: Τα στοιχεία ΔΕΝ είναι έγκυρα.");
        }
    }
}


public class AddCardScreen {
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;

    public void display() {
        System.out.println("AddCardScreen: Εμφάνιση οθόνης προσθήκης κάρτας.");
    }

    public void showNomethods() {
        System.out.println("AddCardScreen: Δεν υπάρχουν μέθοδοι πληρωμής.");
    }

    public void addCard() {
        System.out.println("AddCardScreen: Ο πελάτης προσθέτει νέα κάρτα.");
    }
}


public class BankAPI {
    private String transactionId;

    public void transferToBank() {
        System.out.println("BankAPI: Transferring request to bank...");
    }


    public String getMessage() {
        return "Transaction Approved";
    }
}


public class CardsStorageScreen {
    private boolean userWantsToSave;
    private String storageOption;

    public void display() {
        System.out.println("CardsStorageScreen: Showing storage options.");
    }

    public void selectSave() {
        this.userWantsToSave = true;
        System.out.println("CardsStorageScreen: User chose to save card.");
    }

    public void selectNoSave() {
        this.userWantsToSave = false;
        System.out.println("CardsStorageScreen: User chose NOT to save card.");
    }
}


public class Cards {
    private List<String> savedCards;

    public Cards() {
        this.savedCards = new ArrayList<>();
    }

    public boolean queryPersonalMethods() {
        System.out.println("Cards: Checking user’s personal card methods...");
        return !savedCards.isEmpty();
    }

    public void querySaveCard() {
        System.out.println("Cards: Checking save card preference...");
    }
}


public class PaymentCompletionScreen {
    private ManageBookingCompletionClass bookingManager;
    private String confirmationMessage;

    public PaymentCompletionScreen() {
        this.bookingManager = new ManageBookingCompletionClass();
        this.confirmationMessage = "Η πληρωμή ολοκληρώθηκε με επιτυχία.";
    }

    public void display() {
        System.out.println("PaymentCompletionScreen: Showing payment completed UI...");
    }

    public void selectPaymentCompletion() {
        System.out.println("PaymentCompletionScreen: Payment completed. Proceeding...");
        bookingManager.sendCompletionMessage();
    }
}


public class ManageBookingCompletionClass {
    private ManageEmailClass emailManager;

    public ManageBookingCompletionClass() {
        this.emailManager = new ManageEmailClass();
    }

    public void sendCompletionMessage() {
        System.out.println("ManageBookingCompletionClass: Sending completion message.");
        SuccessMessageScreen success = new SuccessMessageScreen();
        success.display();
        success.showSuccess();
        emailManager.sendEmailToClient();
    }

    public void sendErrorMessage() {
        System.out.println("ManageBookingCompletionClass: Sending error message.");
        ErrorMessageScreen error = new ErrorMessageScreen();
        error.display();
        error.showError();
        emailManager.sendEmailToEmployee();
    }
}

public class ManageEmailClass {
    private String clientEmail;
    private String employeeEmail;

    public void sendEmailToEmployee() {
        System.out.println("ManageEmailClass: Email sent to employee.");
    }

    public void sendEmailToClient() {
        System.out.println("ManageEmailClass: Email sent to client.");
    }
}

public class SuccessMessageScreen {
    private String message = "Η συναλλαγή ήταν επιτυχής.";

    public void display() {
        System.out.println("SuccessMessageScreen: Displaying success screen.");
    }

    public void showSuccess() {
        System.out.println("SuccessMessageScreen: " + message);
    }
}


public class ErrorMessageScreen {
    private String errorMessage = "Η συναλλαγή απέτυχε.";

    public void display() {
        System.out.println("ErrorMessageScreen: Showing error screen...");
    }

    public void showError() {
        System.out.println("ErrorMessageScreen: " + errorMessage);
    }
}
