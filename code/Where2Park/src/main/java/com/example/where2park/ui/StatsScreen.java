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

        VBox layout = showStatistics(statsData);  // Diagram step 2
        Scene scene = new Scene(layout, 400, 300);

        window.setScene(scene);
        window.show();
    }

    public static VBox showStatistics(String statsData) {
        Label statsLabel = new Label("Στατιστικά:\n" + statsData);

        Button backButton = new Button("Επιστροφή");
        backButton.setOnAction(e -> {
            completesStats(); // Step 3 in diagram
            ((Stage) backButton.getScene().getWindow()).close();
        });

        VBox layout = new VBox(10, statsLabel, backButton);
        layout.setStyle("-fx-padding: 20;");
        return layout;
    }

    public static void completesStats() {
        EmployeeHomeScreen.display(); // Back to main menu
    }
}
