package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void startSelect(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/select_osiagniecia.fxml"));

        try {
            StackPane stackPane = loader.load();
            Scene scene = new Scene(stackPane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Pierwsze okno");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startInsert(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/insert_osiagniecia.fxml"));

        try {
            StackPane stackPane = loader.load();
            Scene scene = new Scene(stackPane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Pierwsze okno");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
//        startInsert(primaryStage);
        startSelect(primaryStage);
    }
}

