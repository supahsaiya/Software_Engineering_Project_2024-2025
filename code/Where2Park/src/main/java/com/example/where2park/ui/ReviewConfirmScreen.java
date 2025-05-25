package com.example.where2park.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReviewConfirmScreen {

    public static void display(Runnable onConfirmCallback) {
        Stage stage = new Stage();
        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20");

        Label message = new Label("Your review has been saved.\nDo you want to confirm?");
        Button confirmButton = new Button("Confirm");

        confirmButton.setOnAction(e -> {
            stage.close();
            onConfirmCallback.run();  // Calls ManageReviewClass.confirmationDone()
        });

        root.getChildren().addAll(message, confirmButton);

        stage.setScene(new Scene(root, 300, 150));
        stage.setTitle("Confirm Review");
        stage.show();
    }
}
