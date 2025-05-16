package com.example.where2park.ui;

import java.util.Scanner;

public class ConfirmLocationScreen {

    private String location; // stored location
    private Scanner scanner = new Scanner(System.in);

    public void setLocation(String location) {
        this.location = location;
    }

    // Ask user if the detected location is correct
    public void sendConfirmLocation(String location) {
        System.out.println("Is this your location? " + location + " (y/n)");
    }

    public void display() {
        System.out.println("Displaying confirm location screen...");
    }

    // User confirms location
    public void confirm() {
        System.out.println("Location confirmed!");
        // Additional save logic here
    }

    // User rejects location
    public void rejectLocation() {
        System.out.println("Location rejected by user.");
        // Additional logic to show input screen for manual address
    }

    // New method to ask user and handle input
    public void askUserConfirmation() {
        sendConfirmLocation(location);
        String input = scanner.nextLine().trim().toLowerCase();

        if (input.equals("y") || input.equals("yes")) {
            confirm();
        } else if (input.equals("n") || input.equals("no")) {
            rejectLocation();
        } else {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            askUserConfirmation(); // ask again recursively until valid input
        }
    }
}
