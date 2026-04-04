// Daniel Munoz, William Brewster, Mikenzie Adkins.
// capstoneshipping.App version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping;

import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.Graphics.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage primaryStage) {
        
        DB_Connection database = new DB_Connection(); // Initialize database connection
        
        MainView mainView = new MainView();
        
        Scene scene = new Scene(mainView, 900, 400);

        
        primaryStage.setTitle("Capstone Shipping - Orders");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
        try {
            database.CloseConnections();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

