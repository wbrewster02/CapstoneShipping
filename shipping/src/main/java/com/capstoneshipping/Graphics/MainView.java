// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.MainView version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.Graphics;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;


public class MainView extends BorderPane {

    public MainView() {

        // Top navigation bar
        HBox navBar = new HBox(10);
        navBar.setPadding(new Insets(5)); //shift nav/buttons away from edge a bit for better aesthetics

        Button ordersBtn = new Button("Orders");
        Button orderHistoryBtn = new Button("Order History");
        Button shippingBtn = new Button("Shipping");
        Button shippingHistoryBtn = new Button("Shipping History");

        navBar.getChildren().addAll(
                ordersBtn,
                orderHistoryBtn,
                shippingBtn,
                shippingHistoryBtn
        );

        setTop(navBar);

        // Default view = Orders
        setCenter(new OrderView());

        // Button action (only Orders works for now)
        ordersBtn.setOnAction(e -> setCenter(new OrderView()));

        // Optional: disable others for now
        orderHistoryBtn.setDisable(true);
        shippingBtn.setDisable(true);
        shippingHistoryBtn.setDisable(true);
    }
}
