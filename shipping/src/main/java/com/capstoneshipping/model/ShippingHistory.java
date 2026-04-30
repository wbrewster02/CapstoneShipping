// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.ShippingHistory version: 1.0
// Date Modified: 4/19/2026

package com.capstoneshipping.model;

import java.time.LocalDateTime;
//     Shipping_ID INT NOT NULL,
//     Old_Status VARCHAR(15) NULL,
//     New_Status VARCHAR(15) NOT NULL,
//     Changed_At DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
//     Notes VARCHAR(255) NULL,

public class ShippingHistory {
    //Create class: full constructor, no-argument constructor, getters and setters for all fields

    // Fields
    private int shippingHistoryId;
    private int shippingId;
    private int orderId;

    private String oldStatus;
    private String newStatus;
    private String carrier;
    private String trackingNumber;
    private ShippingStatus shippingStatus;

    private LocalDateTime shippedOn;
    private LocalDateTime expectedBy;
    private LocalDateTime changedAt;
    private String notes;

    // No-argument constructor
    public ShippingHistory() {
    }

    // Full constructor
    public ShippingHistory(
        int shippingHistoryId,
        int shippingId,
        String oldStatus,
        String newStatus,
        LocalDateTime changedAt,
        String notes,
        int orderId,
        String carrier,
        String trackingNumber,
        ShippingStatus shippingStatus,
        LocalDateTime shippedOn,
        LocalDateTime expectedBy
    ) {
        this.shippingHistoryId = shippingHistoryId;
        this.shippingId = shippingId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
        this.notes = notes;
        this.orderId = orderId;
        this.carrier = carrier;
        this.trackingNumber = trackingNumber;
        this.shippingStatus = shippingStatus;
        this.shippedOn = shippedOn;
        this.expectedBy = expectedBy;
    }

    // Getters and setters
    public int getShippingHistoryId() {
        return shippingHistoryId;
    }

    public void setShippingHistoryId(int shippingHistoryId) {
        this.shippingHistoryId = shippingHistoryId;
    }

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
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
}
