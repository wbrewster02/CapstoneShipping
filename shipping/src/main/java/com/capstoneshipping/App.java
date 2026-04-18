// Daniel Munoz, William Brewster, Mikenzie Adkins.
// capstoneshipping.App version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping;
import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.Graphics.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage primaryStage){
        new ViewController(primaryStage);
                
        primaryStage.setTitle("Capstone Shipping - Orders");

        primaryStage.setOnCloseRequest(e -> {
        try {
            DB_Connection.CloseConnections();
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

