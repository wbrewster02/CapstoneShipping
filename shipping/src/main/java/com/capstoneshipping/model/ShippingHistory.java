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

    private String oldStatus;
    private String newStatus;

    private LocalDateTime changedAt;
    private String notes;

    // No-argument constructor
    public ShippingHistory() {
    }

    // Full constructor
    public ShippingHistory(int shippingHistoryId, int shippingId, String oldStatus, String newStatus, LocalDateTime changedAt, String notes) {
        this.shippingHistoryId = shippingHistoryId;
        this.shippingId = shippingId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
        this.notes = notes;
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
}
