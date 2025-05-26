package com.example.where2park.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorScreen {
    public static void display(String errorMsg) {
        Stage window = new Stage();
        window.setTitle("Σφάλμα");

        Label error = new Label(errorMsg);
        Button retryButton = new Button("Επιστροφή");
        retryButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10, error, retryButton);
        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.show();
    }
}
