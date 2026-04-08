package com.capstoneshipping.model;

import java.time.LocalDateTime;

//using these column names from the database to create the Order class, 
// which will be used to map the data from the database to Java objects.
    // Shipping_ID INT IDENTITY(1,1) PRIMARY KEY,
    // Order_ID INT NOT NULL,
    // Cost DECIMAL(8,2) NOT NULL CHECK (Cost >= 0),

    // Shipped_On DATETIME2 NULL,
    // Expected_By DATETIME2 NULL,

    // Ship_Status VARCHAR(15) NOT NULL,
    // Carrier VARCHAR(100) NOT NULL,
    // Tracking_Number VARCHAR(50) NOT NULL,

    // Created_At DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    // Updated_At DATETIME2 NULL,

    // Status_Updated_At DATETIME2 NULL,
    // Shipment_Notes VARCHAR(255) NULL,
    // Return_Reason VARCHAR(255) NULL,

    // Shipping_Address_ID INT NOT NULL,
    // Billing_Address_ID INT NOT NULL,


public class Shipping {
    //Create class: full constructor, no-argument constructor, getters and setters for all fields. DO this one first.

    // Fields
    private int shippingId;
    private int orderId;
    private double cost;

    private LocalDateTime shippedOn;
    private LocalDateTime expectedBy;

    private String shipStatus;
    private String carrier;
    private String trackingNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime statusUpdatedAt;
    private String shipmentNotes;
    private String returnReason;

    private int shippingAddressId;
    private int billingAddressId;

    // No-argument constructor
    public Shipping() {
    }

    // Full constructor
    public Shipping(int shippingId, int orderId, double cost, LocalDateTime shippedOn, LocalDateTime expectedBy, String shipStatus, String carrier, String trackingNumber, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime statusUpdatedAt, String shipmentNotes, String returnReason, int shippingAddressId, int billingAddressId) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.cost = cost;
        this.shippedOn = shippedOn;
        this.expectedBy = expectedBy;
        this.shipStatus = shipStatus;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusUpdatedAt = statusUpdatedAt;
        this.shipmentNotes = shipmentNotes;
        this.returnReason = returnReason;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
    }

    // Getters and setters

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        if (cost >= 0) {
          this.cost = cost;  
        }
    }

    public LocalDateTime getShippedOn() {
        return shippedOn;
    }

    public void setShippedOn(LocalDateTime shippedOn) {
        this.shippedOn = shippedOn;
    }

    public LocalDateTime getExpectedBy() {
        return expectedBy;
    }

    public void setExpectedBy(LocalDateTime expectedBy) {
        this.expectedBy = expectedBy;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public String getShipmentNotes() {
        return shipmentNotes;
    }

    public void setShipmentNotes(String shipmentNotes) {
        this.shipmentNotes = shipmentNotes;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public int getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(int billingAddressId) {
        this.billingAddressId = billingAddressId;
    }
}
