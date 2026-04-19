// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderDetailView version: 1.1
// Date Modified: 4/19/2026

package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.capstoneshipping.dao.OrderDAO;
import com.capstoneshipping.model.FulfillmentStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderStatus;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class OrderDetailView extends VBox {
    private ViewController viewController;
    private final Order order;
    private final Stage stage;
    private final Runnable onUpdate;

    private final OrderDAO orderDAO;

    //choiceboxes for editing status
    private final ChoiceBox<OrderStatus> orderStatusBox = new ChoiceBox<>();
    private final ChoiceBox<FulfillmentStatus> fulfillmentBox = new ChoiceBox<>();

    //button for applying status
    private final Button applyButton = new Button("Apply");
    //button for confirming changed and submitting to records
    private final Button submitButton = new Button("Submit");

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");


    public OrderDetailView(ViewController viewController, Order order, Stage stage, Runnable onUpdate, OrderDAO orderDAO) {
        this.viewController = viewController;
        this.order = order;
        this.stage = stage;
        this.onUpdate = onUpdate;
        this.orderDAO = orderDAO;

        setSpacing(10);
        setPadding(new Insets(10));

        buildUI();

        // listeners for buttons (apply would save changes to the order object, submit would move to orderhistory)
        applyButton.setOnAction(e -> handleApply());
        //submitButton.setOnAction(e -> handleSubmit());
    }

    private void buildUI() {

        // READ ONLY INFO
        Label id = new Label("Order ID: " + order.getOrderId());
        Label customer = new Label("Customer ID: " + order.getCustomerId());
        Label date = new Label("Order Date: " + (order.getOrderDate() != null ? order.getOrderDate().format(FORMATTER) : ""));

        //choiceboxes to edit status + label
        Label orderStatusLabel = new Label("Order Status: ");

        //orderStatusBox.getItems().setAll(OrderStatus.values()); 
        // this changed to whats between comments. It only allows "work flow" to move forward.

        //----------------
        OrderStatus current = order.getOrderStatus();
        OrderStatus next = current.next();

        if (current == next) {
            // terminal state (FULFILLED or CANCELLED)
            orderStatusBox.getItems().setAll(current);
            } else {
            orderStatusBox.getItems().setAll(current, next);
            }

        orderStatusBox.setValue(current); // set current status as default selection (CHHANGES HERE))
        //----------------

        //orderStatusBox.setValue(order.getOrderStatus()); // set current status as default selection

        System.out.println("Current Order Status: " + order.getOrderStatus()); // Debugging line to check current order status

        orderStatusBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> { // Listener to track changes in order status selection
            System.out.println("Selected Order Status: " + newValue);
        });

        Label fulfillmentStatusLabel = new Label("Fulfillment Status: ");
        //fulfillmentBox.getItems().setAll(FulfillmentStatus.values());
        //fulfillmentBox.setValue(order.getFulfillmentStatus()); // set current status as default selection

        //-----------------
        // Similar logic for fulfillment status to only allow valid next steps based on current status
        FulfillmentStatus currentFulfillment = order.getFulfillmentStatus();
        FulfillmentStatus nextFulfillment = currentFulfillment.next();

        if (currentFulfillment == nextFulfillment) {
            // terminal state (FULFILLED)
            fulfillmentBox.getItems().setAll(currentFulfillment);
            } else {
            fulfillmentBox.getItems().setAll(currentFulfillment, nextFulfillment);
            }
        
        fulfillmentBox.setValue(currentFulfillment); // set current status as default selection
        //-----------------


        // ADD TO UI
        getChildren().addAll(
                id,
                customer,
                date,
                orderStatusLabel,
                orderStatusBox,
                fulfillmentStatusLabel,
                fulfillmentBox
        );
        getChildren().addAll(
            applyButton,
            submitButton
        );
    }


    private void handleApply() {
        OrderStatus selectedOrderStatus = orderStatusBox.getValue();
        FulfillmentStatus selectedFulfillmentStatus = fulfillmentBox.getValue();

        if (selectedOrderStatus != null) {
            // Process the selected order status
            order.setOrderStatus(selectedOrderStatus);
            orderDAO.updateOrderStatus(order.getOrderId(), selectedOrderStatus); // Update in database
            
            if (selectedOrderStatus == OrderStatus.FULFILLED) {
                LocalDateTime now = LocalDateTime.now();
                order.setFulfilledAt(now);
                orderDAO.updateFulfilledAt(order.getOrderId(), now); // Update fulfilledAt timestamp in database
            }
        }

        if (selectedFulfillmentStatus != null) {
            // Process the selected fulfillment status
            order.setFulfillmentStatus(selectedFulfillmentStatus);
            orderDAO.updateFulfillmentStatus(order.getOrderId(), selectedFulfillmentStatus); // Update in database //here
        }

        if (onUpdate != null) {
            onUpdate.run(); // Notify the main view to refresh the order list
        }

        if (stage != null) {
            stage.close(); // Close the detail view after applying changes
        }
    }

}



//For labels horizontal with choiceboxes, we can use HBoxes to group them together. Then add those HBoxes to the main VBox. Example below:
//HBox orderStatusRow = new HBox(10, orderStatusLabel, orderStatusBox);
// HBox fulfillmentRow = new HBox(10, fulfillmentStatusLabel, fulfillmentBox);

// getChildren().addAll(
//     id,
//     customer,
//     date,
//     orderStatusRow,
//     fulfillmentRow
// );