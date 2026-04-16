// Daniel Munoz, William Brewster, Mikenzie Adkins.
// capstoneshipping.App version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping;

import java.net.ConnectException;
import java.sql.SQLException;

import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.DataBase.DB_ExceptionHandler.ConnectionException;
import com.capstoneshipping.Graphics.LoginView;
import com.capstoneshipping.Graphics.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application
{

    @Override
    public void start(Stage primaryStage){
        
        DB_Connection database = new DB_Connection(); // Initialize database connection
        
        LoginView loginView = new LoginView();
        BorderPane root = new BorderPane();
        
        Scene scene = new Scene(root, 550, 250);
        
        
        primaryStage.setTitle("Capstone Shipping - Orders");
        primaryStage.setScene(scene);
        root.setCenter(loginView);

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

