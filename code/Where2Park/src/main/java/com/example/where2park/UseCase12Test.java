package com.example.where2park;

import com.example.where2park.ui.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class UseCase12Test extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.selectStatistics(); // Step 1 of Use Case
    }

    public static void main(String[] args) {
        launch(args);
    }
}
