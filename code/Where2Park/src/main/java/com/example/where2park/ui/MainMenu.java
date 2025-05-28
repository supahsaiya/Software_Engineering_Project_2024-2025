package com.example.where2park.ui;

import com.example.where2park.controller.ManageSearchCategoriesClass;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button editButton = new Button("Επεξεργασία Πληροφοριών Πάρκινγκ");
        editButton.setOnAction(e -> EditParkingScreen.display());

        VBox layout = new VBox(10, editButton);
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Κεντρικό Μενού");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void selectStatistics() {
        ManageSearchCategoriesClass searchCategories = new ManageSearchCategoriesClass();
        searchCategories.display(); // Step 2 of Use Case
    }

    public static void main(String[] args) {
        launch(args);
    }
}
