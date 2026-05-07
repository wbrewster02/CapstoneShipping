// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.ViewController version: 1.1
// Date Modified: 4/19/2026

package com.capstoneshipping.Graphics;

import java.io.File;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.dao.OrderHistoryDAOImpl;
import com.capstoneshipping.dao.ShippingDAOImpl;
import com.capstoneshipping.dao.ShippingHistoryDAOImpl;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.Shipping;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// ViewController manages the creation, modification, instantiation, and access of Graphics.Views.
public class ViewController {
    // Stages.
    private Stage primaryStage;
    private Stage orderDetailStage;
    private Stage shippingDetailStage;

    // Layouts.
    private BorderPane layout;
    private BorderPane orderDetailLayout;
    private BorderPane shippingDetailLayout;
    
    // View Attributes.
    private LoginView loginView;
    private MainView mainView;
    private OrderView orderView;
    private ShippingView shippingView;

    private OrderHistoryView orderHistoryView;
    private ShippingHistoryView shippingHistoryView;
    
    private OrderDetailView orderDetailView;
    private ShippingDetailView shippingDetailView;

    // Icon Image
    private Image image;

    public ViewController(Stage stage) {
        // javafx stage/Layout Assignment.
        this.primaryStage = stage;
        
        this.orderDetailStage = new Stage();
        this.shippingDetailStage = new Stage();
        
        this.layout = new BorderPane();
        this.orderDetailLayout = new BorderPane();
        this.shippingDetailLayout = new BorderPane();
        
        // Create Main Scene.
        Scene scene = new Scene(layout, 400, 200);
        System.out.println(getClass().getResource("styles.css"));  // null = wrong location
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        // Create Order Scene.
        Scene orderDetailScene = new Scene(orderDetailLayout, 300, 350);
        orderDetailScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // create Shipping Scene.
        Scene shippingDetailScene = new Scene(shippingDetailLayout, 300, 300);
        shippingDetailScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        orderDetailStage.setScene(orderDetailScene);
        shippingDetailStage.setScene(shippingDetailScene);

        // Views assignment.
        this.loginView = new LoginView(this);
        loginView.setId("Login");
        showLoginView();

        // Icon Image set to Elevate Retail logo.
        this.image = new Image(getClass().getClassLoader().getResourceAsStream("background.png"));
        stage.getIcons().add(image);

    }

    public void showLoginView() {
        // Set Login View as Center.
        layout.setCenter(loginView);

        // Size Properties.
        primaryStage.setWidth(600);
        primaryStage.setHeight(450);
        primaryStage.setResizable(false);
        
        primaryStage.centerOnScreen();

    }
    public void logout() {
        // reset views.
        mainView = null;

        orderView = null;
        orderHistoryView = null;
        
        shippingView = null;
        
        // Clear the DB connection.
        DB_Connection.resetConnection();

        showLoginView();

    } // End Of Constructor.

    // Call this only after successful DB login.
    public void showMainView() {
        primaryStage.setResizable(true);
        
        if (mainView == null){
            mainView = new MainView(this);
        }
        if (orderView == null) {
            orderView = new OrderView(this);
        }
        mainView.setId("Main");
        orderView.setId("Order");
        
        // Load Orders.
        orderView.loadOrders();
        
        // Set the mainView Default to orderView to center.
        mainView.setCenter(orderView);
        layout.setCenter(mainView);
        
        // assign Stage width/lenght.
        primaryStage.setMaximized(true);
    }
    public void showOrderHistory(){
        if (mainView == null){
            mainView = new MainView(this);
        }
        if (orderHistoryView == null){
            orderHistoryView = new OrderHistoryView(this);
        }
        
        mainView.setId("Main");
        orderHistoryView.setId("OrderHistory");

        orderHistoryView.loadOrders();

        mainView.setCenter(orderHistoryView);
        layout.setCenter(mainView);

        primaryStage.setMaximized(true);
    }

    public void openOrderDetailView(Order order){
        // Creates new Order Detail View Each time method is called, allows for Detail View to properly update.
        if (orderDetailView == null){
            orderDetailView = new OrderDetailView(this,
                order,
                orderDetailStage,
                // () -> this.orderView.getTableView().refresh(),
                () -> {
                    this.orderView.loadOrders();

                    if (this.orderHistoryView != null) {
                        this.orderHistoryView.loadOrders();
                    }
                },
                orderView.getOrderDAO(),
                new OrderHistoryDAOImpl(),
                new ShippingDAOImpl());
        }
        orderDetailView.setId("order-detail");
        
        orderDetailLayout.setCenter(this.orderDetailView);
        
        orderDetailStage.getIcons().add(this.image);
        
        orderDetailStage.setTitle("Order Details - #" + order.getOrderId());
        
        orderDetailStage.setResizable(false);

        orderDetailStage.setHeight(350);
        orderDetailStage.setWidth(410);

        orderDetailStage.show();

        // Reset Order Detail View.
        orderDetailView = null;
    }

    public void openShippingDetailView(Shipping shipping){
        shippingDetailView = new ShippingDetailView(
            this,
            shipping,
            shippingDetailStage,
            () -> this.shippingView.getTableView().refresh(),
            shippingView.getShippingDAO(),
            new ShippingHistoryDAOImpl()
        );
        shippingDetailView.setId("order-detail");

        shippingDetailLayout.setCenter(shippingDetailView);

        shippingDetailStage.getIcons().add(this.image);

        shippingDetailStage.setTitle("Shipping Details - #" + shipping.getShippingId());

        shippingDetailStage.setResizable(false);

        shippingDetailStage.show();

        shippingDetailStage.setHeight(300);
        shippingDetailStage.setWidth(350);

        shippingDetailView = null;

    }

    public String fileChooser(String filename){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(filename);

        File selectedFile = fileChooser.showSaveDialog(this.primaryStage);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            // Code to save the file at filePath
            return filePath;
        }
        return null;
    }
    public void refreshTables(){
        DB_Connection.getConnection();
        getOrderView();
        getShippingView();
        getOrderHistoryView();
        getShippingHistoryView();
    }
    // Get Methods.
    public OrderView getOrderView() {
        if (orderView == null) {
            orderView = new OrderView(this);
        }

        orderView.loadOrders();
        return orderView;
    }
    public OrderHistoryView getOrderHistoryView(){
        if (orderHistoryView == null) {
            orderHistoryView = new OrderHistoryView(this);
        }

        orderHistoryView.loadOrders();
        return orderHistoryView;
    }

    public ShippingView getShippingView() {
        if (shippingView == null) {
            shippingView = new ShippingView(this);
        }

            shippingView.loadShipments();
            return shippingView;
        }
    
    public MainView getMainView() {
        if (mainView == null) {
            mainView = new MainView(this);
        }

        return mainView;
    }

    public ShippingHistoryView getShippingHistoryView() {
        if (shippingHistoryView == null) {
            shippingHistoryView = new ShippingHistoryView(this);
        }
        shippingHistoryView.loadShippingHistory();
        return shippingHistoryView;
    }

}
