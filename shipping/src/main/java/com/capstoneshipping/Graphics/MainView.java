// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.MainView version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.model.Order; //may not need import.

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.geometry.Insets;


public class MainView extends BorderPane {
        // ADDED proper Instantiation of buttons and search components outside of the constructor so they can be accessed in the listeners.
        private final Button ordersBtn;
        private final Button orderHistoryBtn;
        private final Button shippingBtn;
        private final Button shippingHistoryBtn;
        private final ChoiceBox<String> choiceBox;
        private final TextField searchField;

    public MainView() {
        ordersBtn = new Button("Orders");
        orderHistoryBtn = new Button("Order History");
        shippingBtn = new Button("Shipping");
        shippingHistoryBtn = new Button("Shipping History");

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
        buttonBar.getChildren().addAll(
            ordersBtn,
            orderHistoryBtn,
            shippingBtn,
            shippingHistoryBtn
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
        OrderView orderView = new OrderView();
        setCenter(orderView);

        // Button action (only Orders works for now)
        ordersBtn.setOnAction(e -> setCenter(new OrderView()));

        // Optional: disable others for now
        orderHistoryBtn.setDisable(true);
        shippingBtn.setDisable(true);
        shippingHistoryBtn.setDisable(true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            //System.out.println("Search text changed: " + newVal); //debugging line to confirm listener is working
            orderView.applySearch(choiceBox.getValue(), newVal);
        });

        choiceBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            //System.out.println("Search field changed: " + newVal); //debugging line to confirm listener is working
            orderView.applySearch(newVal, searchField.getText());
        });
    }
}
