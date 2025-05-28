package com.example.where2park.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VerifyChangesScreen {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Επιβεβαίωση");

        VBox layout = showMessage(window); // Step: showMessage()

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.show();

        verifyChanges(); // Step: verifyChanges()
    }

    public static VBox showMessage(Stage window) {
        Label message = new Label("Οι αλλαγές αποθηκεύτηκαν με επιτυχία.");
        Button okButton = new Button("ΟΚ");
        okButton.setOnAction(e -> window.close());

        return new VBox(10, message, okButton);
    }

    public static void verifyChanges() {
        System.out.println("Οι αλλαγές επιβεβαιώθηκαν."); // Placeholder action

    }
}
