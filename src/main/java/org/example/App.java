package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        try{
        Parent parent = FXMLLoader.load(App.class.getResource("/App.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.setMinWidth(1000);
        stage.setMinHeight(680);
        } catch (java.io.IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

}