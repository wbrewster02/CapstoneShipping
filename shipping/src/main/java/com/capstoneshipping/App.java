// Daniel Munoz, William Brewster, Mikenzie Adkins.
// capstoneshipping.App version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping;
import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.Graphics.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage primaryStage){
        ViewController viewController = new ViewController(primaryStage);
                
        primaryStage.setTitle("Capstone Shipping - Orders");

        primaryStage.setOnCloseRequest(e -> {
        try {
            viewController.logout();
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

