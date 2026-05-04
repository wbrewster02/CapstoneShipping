// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.MainView version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.DataBase.DB_Connection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class MainView extends BorderPane {
        // ADDED proper Instantiation of buttons and search components outside of the constructor so they can be accessed in the listeners.
        private final Button ordersBtn;
        private final Button refreshBtn;
        private final Button orderHistoryBtn;
        private final Button shippingBtn;
        private final Button shippingHistoryBtn;
        private final Button logoutBtn;
        private final ChoiceBox<String> choiceBox;
        private final TextField searchField;

        private ViewController viewController;

        public MainView(ViewController viewController) {
        this.viewController = viewController;
        
        ordersBtn = new Button("Orders");
        refreshBtn = new Button("Refresh dataBase");

        orderHistoryBtn = new Button("Order History");
        shippingBtn = new Button("Shipping");
        shippingHistoryBtn = new Button("Shipping History");
        logoutBtn = new Button("Logout");


        // Nav button styles — transparent with border, green on hover
        ordersBtn.getStyleClass().add("nav-button");
        orderHistoryBtn.getStyleClass().add("nav-button");
        shippingBtn.getStyleClass().add("nav-button");
        shippingHistoryBtn.getStyleClass().add("nav-button");

        logoutBtn.setId("logout-btn");

        // MinWidth prevents choicebox collapsing to just the arrow
        choiceBox = new ChoiceBox<>();
        choiceBox.setMinWidth(120);

        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setId("search-field");

        // HBoxes for layout of buttons and search components. VBox to stack them vertically.
        // BorderPane to place the main content in the center and everything else at the top.
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER_LEFT); // vertically center nav items

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // pushes logout to the right
        buttonBar.getStyleClass().add("button-bar");

        buttonBar.getChildren().addAll(
            ordersBtn,
            orderHistoryBtn,
            shippingBtn,
            shippingHistoryBtn,
            spacer,
            logoutBtn
        );

        HBox searchBar = new HBox(10);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS); // pushes logout to the right

        searchBar.setAlignment(Pos.CENTER_LEFT); // vertically center search items
        searchBar.getChildren().addAll(choiceBox, searchField, spacer2, refreshBtn);
        searchBar.getStyleClass().add("search-bar");

        // VBox spacing 0 and no padding — prevents gap between nav bar and search bar
        VBox topContainer = new VBox();
        VBox leftContainer = new VBox(5);
        VBox rightContainer = new VBox(5);

        leftContainer.setAlignment(Pos.CENTER_LEFT);
        leftContainer.setPadding(new Insets(7.5));  
        leftContainer.setPrefHeight(30);
        leftContainer.setId("side-bar");

        rightContainer.setAlignment(Pos.CENTER_RIGHT);
        rightContainer.setPadding(new Insets(7.5));  
        rightContainer.setPrefHeight(30);
        rightContainer.setId("side-bar");
        
        topContainer.getChildren().addAll(buttonBar, searchBar);

        setTop(topContainer);

        setLeft(leftContainer);
        setRight(rightContainer);

        // Default search fields and view on startup
        setOrderSearchFields();
        setCenter(this.viewController.getOrderView());

        // Button actions — switch center view and update search fields accordingly
        ordersBtn.setOnAction(e -> {
            setCenter(this.viewController.getOrderView());
            setOrderSearchFields();
            applySearchToCurrentView();
        });
        refreshBtn.setOnAction(e -> {
            viewController.refreshTables();
            DB_Connection connection = new DB_Connection();    
        });

        orderHistoryBtn.setOnAction(e -> {
            setCenter(this.viewController.getOrderHistoryView());
            setOrderHistorySearchFields();
            applySearchToCurrentView();
        });

        shippingBtn.setOnAction(e -> {
            setCenter(this.viewController.getShippingView());
            setShippingSearchFields();
            applySearchToCurrentView();
        });

        shippingHistoryBtn.setOnAction(e -> {
            setCenter(viewController.getShippingHistoryView());
            setShippingHistorySearchFields();
        });

        logoutBtn.setOnAction(e -> this.viewController.logout());

        // Re-apply search on every keystroke or choicebox change
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applySearchToCurrentView());

        choiceBox.valueProperty().addListener((obs, oldVal, newVal) -> applySearchToCurrentView());
}   

    public void setOrderSearchFields() {
        // Implementation for setting order search fields
        choiceBox.getItems().clear();

        choiceBox.getItems().addAll(
            "Order ID",
            "Customer ID",
            "Customer Name",
            "Order Status",
            "Fulfillment Status"
        );

        // Optional: set default selection
        choiceBox.setValue("Order ID");
    }

    public void setShippingSearchFields() {
        // Implementation for setting shipping search fields
        choiceBox.getItems().clear();

        choiceBox.getItems().addAll(
            "Shipping ID",
            "Tracking Number",
            "Order ID",
            "Carrier",
            "Ship Status"
        );

        // Optional: set default selection
        choiceBox.setValue("Shipping ID");

    }

    //double check here
    public void setOrderHistorySearchFields() {
        choiceBox.getItems().clear();

        choiceBox.getItems().addAll(
            "Order ID",
            "Customer ID",
            "Order Date",
            "Fulfilled At"
         );

        // Optional: set default selection
        choiceBox.setValue("Order ID");
    }

    public void setShippingHistorySearchFields() {
        choiceBox.getItems().clear();

        choiceBox.getItems().addAll(
            "Shipping ID",
            "Order ID",
            "Carrier",
            "Tracking Number",
            "Ship Status"
         );

        // Optional: set default selection
        choiceBox.setValue("Shipping ID");
    }

    public void applySearchToCurrentView() {
        // Implementation for applying search to the current view
        Object currentView = getCenter();

        if (currentView instanceof SearchableView searchableView) {
        searchableView.applySearch(choiceBox.getValue(), searchField.getText());
        }
    }
}