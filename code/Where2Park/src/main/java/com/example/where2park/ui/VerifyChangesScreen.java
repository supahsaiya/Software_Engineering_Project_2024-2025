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

        Label message = new Label("Οι αλλαγές αποθηκεύτηκαν με επιτυχία.");
        Button okButton = new Button("ΟΚ");
        okButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10, message, okButton);
        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.show();
    }
}
