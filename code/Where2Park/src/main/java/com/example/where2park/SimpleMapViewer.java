package com.example.where2park;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SimpleMapViewer extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // OpenStreetMap direct link
        String url = "https://www.openstreetmap.org/#map=15/37.9838/23.7275";
        webEngine.load(url);

        BorderPane root = new BorderPane(webView);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("OpenStreetMap in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
