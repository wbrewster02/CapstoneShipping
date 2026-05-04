package com.capstoneshipping.Graphics;


import com.capstoneshipping.dao.ShippingDAO;
import com.capstoneshipping.model.ShippingStatus;
import com.capstoneshipping.model.Shipping;
import com.capstoneshipping.dao.ShippingHistoryDAO;
import com.capstoneshipping.model.ShippingHistory;
import com.capstoneshipping.model.ShippingLabelData;
import com.capstoneshipping.util.WebhookService;
import com.capstoneshipping.util.ShippingLabelPDFUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
    //button for generating shipping label (bonus feature)
    private final Button shippingLabelButton = new Button("Print Label");

    //webhook service for notifying mobile app of status changes
    private final WebhookService webhookService = new WebhookService();

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
        setPadding(new Insets(5));

        buildUI();

        // listeners for buttons (apply would save changes to the order object, submit would move to orderhistory)
        applyButton.setOnAction(e -> handleApply());
        submitButton.setOnAction(e -> handleSubmit());

        //shipping label button
        shippingLabelButton.setOnAction(e -> handleShippingLabel());
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

        HBox buttonRow = new HBox(5);
        buttonRow.getChildren().add(applyButton);
        buttonRow.getChildren().add(submitButton);
        buttonRow.getChildren().add(shippingLabelButton);

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
            buttonRow
        );
    }

    private void handleApply() {
        ShippingStatus selectedShippingStatus = shippingStatusBox.getValue();

        if (selectedShippingStatus != null) {
            // Update the shipping status
            shipping.setShipStatus(selectedShippingStatus);
            shippingDAO.updateShippingStatus(shipping.getShippingId(), selectedShippingStatus);

            
            //push notification to webhook for mobile app
            webhookService.notifyShippingStatusUpdated(shipping.getOrderId());

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

    private void handleShippingLabel() {
        ShippingLabelData labelData = shippingDAO.getShippingLabelData(shipping.getShippingId());

        if (labelData == null) {
            System.out.println("No shipping label data found for Shipping ID: " + shipping.getShippingId());
            return;
        }

        File pdf = ShippingLabelPDFUtil.createLabel(labelData);
        if (pdf != null) {
            ShippingLabelPDFUtil.openPDF(pdf);
            System.out.println("Shipping label PDF generated and opened for Shipping ID: " + shipping.getShippingId());
        }


        //console print for testing
        // if (labelData == null) {
        //     System.out.println("No shipping label data found for Shipping ID: " + shipping.getShippingId());
        //     return;
        // }

        // System.out.println("===== SHIPPING LABEL =====");
        // System.out.println("FROM:");
        // System.out.println(labelData.getReturnAddress());

        // System.out.println("\nTO:");
        // System.out.println(labelData.getCustomerName());
        // System.out.println(labelData.getShippingAddress());

        // System.out.println("\nORDER ID: " + labelData.getOrderId());
        // System.out.println("CARRIER: " + labelData.getCarrier());
        // System.out.println("TRACKING #: " + labelData.getTrackingNumber());
        // System.out.println("==========================");
    }



    // private LocalDateTime calculateExpectedBy(String carrier, LocalDateTime shippedOn) {
    //     if (carrier == null) {
    //         return shippedOn.plusDays(5);
    //     }

    //     return switch (carrier.toLowerCase()) {
    //         case "ups" -> shippedOn.plusDays(3);
    //         case "fedex" -> shippedOn.plusDays(2);
    //         case "usps" -> shippedOn.plusDays(5);
    //         case "dhl" -> shippedOn.plusDays(4);
    //         default -> shippedOn.plusDays(5);
    //     };
    // }
}
