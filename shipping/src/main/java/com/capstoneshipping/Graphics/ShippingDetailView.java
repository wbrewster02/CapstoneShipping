package com.capstoneshipping.Graphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.capstoneshipping.dao.ShippingDAO;
import com.capstoneshipping.model.ShippingStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.Shipping;
import com.capstoneshipping.dao.OrderDAO;
import com.capstoneshipping.dao.ShippingHistoryDAO;
import com.capstoneshipping.dao.ShippingHistoryDAOImpl;
import com.capstoneshipping.model.ShippingHistory;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShippingDetailView extends VBox {
    private ViewController viewController;

    private final Shipping shipping;
    private final Stage stage;
    private final Runnable onUpdate;

    private final ShippingDAO shippingDAO;
    private final ShippingHistoryDAO shippingHistoryDAO;

    //choicebox for editing shipping status
    private final ChoiceBox<ShippingStatus> shippingStatusBox = new ChoiceBox<>();

    //button for applying status
    private final Button applyButton = new Button("Apply");
    //button for confirming changed and submitting to records
    private final Button submitButton = new Button("Submit");

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

    public ShippingDetailView(ViewController viewController, Shipping shipping, Stage stage, Runnable onUpdate, ShippingDAO shippingDAO, ShippingHistoryDAO shippingHistoryDAO) {
        this.viewController = viewController;
        this.shipping = shipping;
        this.stage = stage;
        this.onUpdate = onUpdate;
        this.shippingDAO = shippingDAO;
        this.shippingHistoryDAO = shippingHistoryDAO;


        setSpacing(10);
        setPadding(new Insets(10));

        buildUI();

        // listeners for buttons (apply would save changes to the order object, submit would move to orderhistory)
        applyButton.setOnAction(e -> handleApply());
        submitButton.setOnAction(e -> handleSubmit());
    }

    private void buildUI() {
        //read only labels for shipping details
        Label shippingIdLabel = new Label("Shipping ID: " + shipping.getShippingId());
        Label orderIdLabel = new Label("Order ID: " + shipping.getOrderId());

        //Label shippedOnLabel = new Label("Shipped On: " + shipping.getShippedOn().format(FORMATTER));
        //Label expectedByLabel = new Label("Expected By: " + shipping.getExpectedBy().format(FORMATTER));

        Label shippedOnLabel = new Label("Shipped On: " +
            (shipping.getShippedOn() != null ? shipping.getShippedOn().format(FORMATTER) : "N/A"));

        Label expectedByLabel = new Label("Expected By: " +
            (shipping.getExpectedBy() != null ? shipping.getExpectedBy().format(FORMATTER) : "N/A"));


        //choicebox for shipping status + label
        Label shippingStatusLabel = new Label("Shipping Status:");

        ShippingStatus current = shipping.getShipStatus();
        ShippingStatus next = current.next();

        if (current == next) {
            shippingStatusBox.getItems().setAll(current);
        } else {
            shippingStatusBox.getItems().setAll(current, next);
        }

        shippingStatusBox.setValue(current);

        //add to ui
        getChildren().addAll(
            shippingIdLabel, 
            orderIdLabel, 
            shippedOnLabel, 
            expectedByLabel, 
            shippingStatusLabel, 
            shippingStatusBox, 
            applyButton,
            submitButton
        );
    }

    private void handleApply() {
        ShippingStatus selectedShippingStatus = shippingStatusBox.getValue();

        if (selectedShippingStatus != null) {
            // Update the shipping status
            shipping.setShipStatus(selectedShippingStatus);
            shippingDAO.updateShippingStatus(shipping.getShippingId(), selectedShippingStatus);

            if (selectedShippingStatus == ShippingStatus.SHIPPED) {
                // Handle shipped status specific logic
                LocalDateTime now = LocalDateTime.now();
                //LocalDateTime expectedBy = calculateExpectedBy(shipping.getCarrier(), now);

                shipping.setShippedOn(now);
                //shipping.setExpectedBy(expectedBy);

                shippingDAO.updateShippedOn(shipping.getShippingId(), now);
                //shippingDAO.updateExpectedBy(shipping.getShippingId(), expectedBy);
            }

            if (onUpdate != null) {
                onUpdate.run(); // Notify the main view to refresh the order list
            }

            if (stage != null) {
                stage.close(); // Close the detail view after applying changes
            }
        }
    }

    private void handleSubmit() {
        ShippingStatus selectedStatus = shippingStatusBox.getValue();

        if (selectedStatus != ShippingStatus.DELIVERED) {
            System.out.println("Shipping must be delivered before submitting to Shipping History.");
            return;
        }


        if (selectedStatus == null) {
            System.out.println("No shipping status selected.");
            return;
        }

        ShippingHistory history = new ShippingHistory(
            0, // DB assigns ID
            shipping.getShippingId(),

            shipping.getShipStatus() != null
                ? shipping.getShipStatus().toDbValue()
                : null,

            selectedStatus.toDbValue(),

            LocalDateTime.now(),
            "Submitted from ShippingDetailView",

            shipping.getOrderId(),
            shipping.getCarrier(),
            shipping.getTrackingNumber(),
            selectedStatus,
            shipping.getShippedOn(),
            shipping.getExpectedBy()
        );

        if (!shippingHistoryDAO.shippingHistoryExists(shipping.getShippingId())) {
            shippingHistoryDAO.insertShippingHistory(history);
            System.out.println("Shipping history submitted for Shipping ID: " + shipping.getShippingId());
        } else {
            System.out.println("Shipping history already exists for Shipping ID: " + shipping.getShippingId());
        }

        if (onUpdate != null) {
            onUpdate.run();
        }

        if (stage != null) {
            stage.close();
        }
    }

    private LocalDateTime calculateExpectedBy(String carrier, LocalDateTime shippedOn) {
        if (carrier == null) {
            return shippedOn.plusDays(5);
        }

        return switch (carrier.toLowerCase()) {
            case "ups" -> shippedOn.plusDays(3);
            case "fedex" -> shippedOn.plusDays(2);
            case "usps" -> shippedOn.plusDays(5);
            case "dhl" -> shippedOn.plusDays(4);
            default -> shippedOn.plusDays(5);
        };
    }
}
