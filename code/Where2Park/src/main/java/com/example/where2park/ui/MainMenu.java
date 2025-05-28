package com.example.where2park.ui;

import com.example.where2park.controller.ManageSearchCategoriesClass;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        display(); // Still entry point
    }

    public void display() {
        Button editButton = new Button("Επεξεργασία Πληροφοριών Πάρκινγκ");
        editButton.setOnAction(e -> selectEdit()); // Step now moved

        Button statsButton = new Button("Προβολή Στατιστικών");
        statsButton.setOnAction(e -> selectStatistics());

        VBox layout = new VBox(10, editButton, statsButton);
        Scene scene = new Scene(layout, 300, 200);
        mainStage.setTitle("Κεντρικό Μενού");
        mainStage.setScene(scene);
        mainStage.show();
    }

    //Called by edit button — matches diagram
    public void selectEdit() {
        EditParkingScreen.display();
    }

    public void selectStatistics() {
        ManageSearchCategoriesClass searchCategories = new ManageSearchCategoriesClass();
        searchCategories.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
