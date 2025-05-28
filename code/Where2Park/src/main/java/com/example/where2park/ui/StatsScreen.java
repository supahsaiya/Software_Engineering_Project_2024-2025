package com.example.where2park.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatsScreen {

    public static void display(String statsData) {
        Stage window = new Stage();
        window.setTitle("Στατιστικά");

        Label statsLabel = new Label("Στατιστικά:\n" + statsData);

        Button backButton = new Button("Επιστροφή");
        backButton.setOnAction(e -> {
            completesStats(); // Step 8
            window.close();
        });

        VBox layout = new VBox(10, statsLabel, backButton);
        layout.setStyle("-fx-padding: 20;");
        window.setScene(new Scene(layout, 400, 300));
        window.show();
    }

    public static void completesStats() {
        EmployeeHomeScreen.display(); // Go back to employee home screen
    }
}
