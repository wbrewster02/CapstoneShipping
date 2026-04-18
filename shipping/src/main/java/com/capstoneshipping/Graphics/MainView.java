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
        orderHistoryBtn = new Button("Order History");
        shippingBtn = new Button("Shipping");
        shippingHistoryBtn = new Button("Shipping History");
        logoutBtn = new Button("Logout");

        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(
        "Order ID",
            "Customer ID",
            "Order Status",
            "Fulfillment Status"
        );

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
        ordersBtn.setOnAction(e -> setCenter(this.viewController.getOrderView()));
        logoutBtn.setOnAction(e -> this.viewController.logout());

        // orderHistoryBtn.setOnAction(e -> setCenter(viewController.getOrderHistoryView()));
        // shippingBtn.setOnAction(e -> setCenter(viewController.getShippingView()));
        // shippingHistoryBtn.setOnAction(e -> setCenter(viewController.getShippingHistoryView()));
        
        // Optional: disable others for now
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            OrderView ov = this.viewController.getOrderView();
            if (ov != null) {
                ov.applySearch(choiceBox.getValue(), newVal);
            }
        });

        choiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            OrderView ov = this.viewController.getOrderView();
            if (ov != null) {
                ov.applySearch(newVal, searchField.getText());
            }
        });
    }
}
