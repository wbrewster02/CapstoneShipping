// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.ViewController version: 1.0
// Date Modified: 4/17/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.DataBase.DB_Connection;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ViewController {

    private Stage primaryStage;
    private BorderPane layout;
    
    private LoginView loginView;
    private MainView mainView;
    private OrderView orderView;
    

    public ViewController(Stage stage) {
        this.primaryStage = stage;
        this.layout = new BorderPane();

        Scene scene = new Scene(layout, 400, 200); // need this!
        primaryStage.setScene(scene);
        
        this.loginView = new LoginView(this);
        this.orderView = new OrderView(this);
        this.mainView = new MainView(this);          
        showLoginView();

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("background.png"));
        stage.getIcons().add(image);

        primaryStage.show();
    }

    public void showLoginView() {
        layout.setCenter(this.loginView);
        primaryStage.setWidth(700);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
    }
    public void logout() {
        // reset views.
        this.mainView = null;
        this.orderView = null;
        
        // Clear the DB connection
        DB_Connection.resetConnection();

        showLoginView();
    }

    // Call this only after successful DB login
    public void showMainView() {
        primaryStage.setResizable(true);
        
        if (this.mainView == null){
            this.mainView = new MainView(this);
        }
        if (orderView == null) {
            orderView = new OrderView(this);
        }
        orderView.loadOrders();
        
        mainView.setCenter(orderView);
        layout.setCenter(mainView);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
    }


 
    public OrderView getOrderView() {
        if (orderView == null) {
            orderView = new OrderView(this);
        }

        orderView.loadOrders();
        return orderView;
    }
    
    public MainView getMainView() {
        if (mainView == null) {
            mainView = new MainView(this);
        }

        return mainView;
    }
}
