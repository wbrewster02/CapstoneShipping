// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.Shipping version: 1.0
// Date Modified: 4/19/2026

package com.capstoneshipping.model;

import java.time.LocalDateTime;


public class Shipping {

    // Fields
    private int shippingId;
    private int orderId;
    private double cost;

    private LocalDateTime shippedOn;
    private LocalDateTime expectedBy;

    private ShippingStatus shipStatus;
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
    public Shipping(int shippingId, int orderId, double cost, LocalDateTime shippedOn, LocalDateTime expectedBy, ShippingStatus shipStatus, String carrier, String trackingNumber, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime statusUpdatedAt, String shipmentNotes, String returnReason, int shippingAddressId, int billingAddressId) {
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

    public ShippingStatus getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(ShippingStatus shipStatus) {
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
