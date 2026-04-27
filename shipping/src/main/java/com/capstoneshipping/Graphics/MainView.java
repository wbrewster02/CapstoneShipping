// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.MainView version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping.Graphics;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class MainView extends BorderPane {
        // ADDED proper Instantiation of buttons and search components outside of the constructor so they can be accessed in the listeners.
        private final Button ordersBtn;
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
            ordersBtn.getStyleClass().add("nav-button");
            
            orderHistoryBtn = new Button("Order History");
            orderHistoryBtn.getStyleClass().add("nav-button");
            
            shippingBtn = new Button("Shipping");
            shippingBtn.getStyleClass().add("nav-button");

            shippingHistoryBtn = new Button("Shipping History");
            shippingHistoryBtn.getStyleClass().add("nav-button");

            logoutBtn = new Button("Logout");
            logoutBtn.setId("logout-btn");
            
            choiceBox = new ChoiceBox<>();
            choiceBox.getStyleClass().add("combo-box-popup");
            setOrderSearchFields(); // Default to order search fields, can be switched when navigating to shipping view.
            
            searchField = new TextField();
            searchField.setPromptText("Search...");
            
            //Hboxes for layout of buttons and search components. VBox to stack them vertically. 
            //BorderPane to place the main content in the center and everything else at the top.
            //left inside the constructor, local layount variables. We can adjust the spacing and padding as needed.
            HBox buttonBar = new HBox(10);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            buttonBar.getChildren().addAll(
                ordersBtn,
                orderHistoryBtn,
                shippingBtn,
                shippingHistoryBtn,
                spacer,
                logoutBtn
            );
            buttonBar.setId("button-bar");
            buttonBar.getStyleClass().add("button-bar");
            
            HBox searchBar = new HBox(10);
            searchBar.getChildren().addAll(
                    choiceBox,
                    searchField
            );

            VBox topContainer = new VBox(5); // spacing between rows
            topContainer.setPadding(new Insets(5));
            topContainer.getChildren().addAll(buttonBar, searchBar);

            setTop(topContainer);


            //Default view = Orders
            setCenter(this.viewController.getOrderView());
            // Button action (only Orders works for now)
            ordersBtn.setOnAction(e -> {
                setCenter(this.viewController.getOrderView());
                setOrderSearchFields();
                applySearchToCurrentView();
            });


            logoutBtn.setOnAction(e -> this.viewController.logout());
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

            searchField.textProperty().addListener((obs, oldVal, newVal) -> {
                applySearchToCurrentView();
            });

            choiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                applySearchToCurrentView();
            });
            
        } // End of Constructor

    public void setOrderSearchFields() {
        // Implementation for setting order search fields
        choiceBox.getItems().clear();

        choiceBox.getItems().addAll(
            "Order ID",
            "Customer ID",
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
            "Old Order Status",
            "New Order Status",
            "Old Fulfillment Status",
            "New Fulfillment Status"
         );

        // Optional: set default selection
        choiceBox.setValue("Order ID");
    }

    public void applySearchToCurrentView() {
        // Implementation for applying search to the current view
        Object currentView = getCenter();

        if (currentView instanceof SearchableView searchableView) {
        searchableView.applySearch(choiceBox.getValue(), searchField.getText());
        }
    }
}