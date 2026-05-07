// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.OrderDetailView version: 1.1
// Date Modified: 4/19/2026

package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.capstoneshipping.dao.OrderDAO;
import com.capstoneshipping.dao.OrderHistoryDAO;
import com.capstoneshipping.dao.ShippingDAO;
import com.capstoneshipping.dao.ShippingDAOImpl;
import com.capstoneshipping.model.FulfillmentStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderHistory;
import com.capstoneshipping.model.OrderStatus;
import com.capstoneshipping.model.ShippingStatus;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class OrderDetailView extends VBox {
    private ViewController viewController;
    private final Order order;
    private final Stage stage;
    private final Runnable onUpdate;

    private final OrderDAO orderDAO;
    private final OrderHistoryDAO orderHistoryDAO;
    private final ShippingDAO shippingDAO;

    //choiceboxes for editing status
    private final ChoiceBox<OrderStatus> orderStatusBox = new ChoiceBox<>();
    private final ChoiceBox<FulfillmentStatus> fulfillmentBox = new ChoiceBox<>();

    // Button for applying status
    private final Button applyButton = new Button("Apply");
    // Button for confirming changed and submitting to records
    private final Button submitButton = new Button("Submit");
    // Button for creating a shipping label if one doesnt exist already for an order, only usable if shipping does not contain a valid entry.
    private final Button createShippingButton = new Button("Create Shipping Label");

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");


    public OrderDetailView(ViewController viewController, Order order, Stage stage, Runnable onUpdate, OrderDAO orderDAO, OrderHistoryDAO orderHistoryDAO, ShippingDAO shippingDAO) {
        this.viewController = viewController;
        this.order = order;
        this.stage = stage;
        this.onUpdate = onUpdate;
        this.orderDAO = orderDAO;
        this.orderHistoryDAO = orderHistoryDAO;
        this.shippingDAO = shippingDAO;

        setSpacing(10);
        setPadding(new Insets(5));

        buildUI();

        // listeners for buttons (apply would save changes to the order object, submit would move to orderhistory)
        applyButton.setOnAction(e -> handleApply());
        submitButton.setOnAction(e -> handleSubmit());
        createShippingButton.setOnAction(e -> handleShipping());
    }

    private void buildUI() {

        // READ ONLY INFO
        Label id = new Label("Order ID: " + order.getOrderId());
        Label customer = new Label("Customer ID: " + order.getCustomerId());
        Label customerName = new Label("Customer Name: " + order.getCustomerName());
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

        HBox buttonRow = new HBox(5);
        buttonRow.getChildren().add(applyButton);
        buttonRow.getChildren().add(submitButton);
        buttonRow.getChildren().add(createShippingButton);
        createShippingButton.setDisable(true);
        ShippingDAOImpl shipments = new ShippingDAOImpl();
        
        if (current == OrderStatus.FULFILLED && currentFulfillment == FulfillmentStatus.FULFILLED){
            createShippingButton.setDisable(false);
        }

        if (shipments.shippingExistsForOrder(order.getOrderId())){
            createShippingButton.setDisable(true);
        }
        


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
                customerName,
                date,
                orderStatusLabel,
                orderStatusBox,
                fulfillmentStatusLabel,
                fulfillmentBox
        );
        getChildren().addAll(
            buttonRow
        );
    }


    private void handleApply() {
        OrderStatus selectedOrderStatus = orderStatusBox.getValue();
        FulfillmentStatus selectedFulfillmentStatus = fulfillmentBox.getValue();

        if (selectedOrderStatus != null && selectedFulfillmentStatus != null) {
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

    private void handleSubmit() {

        OrderStatus selectedOrderStatus = orderStatusBox.getValue();
        FulfillmentStatus selectedFulfillmentStatus = fulfillmentBox.getValue();

        if (selectedOrderStatus != OrderStatus.FULFILLED ||
            selectedFulfillmentStatus != FulfillmentStatus.FULFILLED) {

            System.out.println("Order must be fully fulfilled before submitting to Order History.");
            return;
        }
        
        OrderHistory history = new OrderHistory(
            0, // orderHistoryId, DB will assign this
            order.getOrderId(),

            order.getOrderStatus() != null
                ? order.getOrderStatus().toDbValue()
                : null,

            orderStatusBox.getValue() != null
                ? orderStatusBox.getValue().toDbValue()
                : null,

            order.getFulfillmentStatus() != null
                ? order.getFulfillmentStatus().toDbValue()
                : null,

            fulfillmentBox.getValue() != null
                ? fulfillmentBox.getValue().toDbValue()
                : null,

            LocalDateTime.now(),
            "Submitted from OrderDetailView",

            // display-only joined fields
            order.getCustomerId(),
            order.getOrderDate(),
            order.getFulfilledAt()
        );

        //orderHistoryDAO.insertOrderHistory(history);
        // Check if order history already exists for this order ID before inserting
        if (!orderHistoryDAO.orderHistoryExists(order.getOrderId())) {
            orderHistoryDAO.insertOrderHistory(history);
            System.out.println("Order history submitted for Order ID: " + order.getOrderId());
        } else {
            System.out.println("Order history already exists for Order ID: " + order.getOrderId());
        }

        shippingDAO.updateShippingStatusByOrderId(
            order.getOrderId(),
            ShippingStatus.PENDING
        );

        //potential addition to have reset timestamp for when shipping status updated and add "expected by" date to sync better (which would be handled in shippingDetailView -> apply).
        // shippingDAO.resetShippingForOrder(order.getOrderId());

        //check shipping exists for this order (DEBUG LINE, can remove later)
        // if (shippingDAO.shippingExistsForOrder(order.getOrderId())) {
        //     System.out.println("Shipping exists");
        // } else {
        //     System.out.println("Shipping not created yet");
        // }

        if (onUpdate != null) {
            onUpdate.run(); // Notify the main view to refresh the order list
        }

        if (stage != null) {
            stage.close();
        }
    }
    private void handleShipping(){

    }

}