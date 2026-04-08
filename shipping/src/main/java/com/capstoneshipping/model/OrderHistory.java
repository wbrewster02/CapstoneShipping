package com.capstoneshipping.model;
    // Order_ID INT NOT NU
import java.time.LocalDateTime;

    // Old_Order_Status VARCHAR(50) NULL,
    // New_Order_Status VARCHAR(50) NOT NULL,

    // Old_Fulfillment_Status VARCHAR(50) NULL,
    // New_Fulfillment_Status VARCHAR(50) NULL,

    //Changed_At DATETIME2 NOT NULL DEFAULT GETUTCDATE(),
    //Notes VARCHAR(255) NULL,

public class OrderHistory {
    //Create class: full constructor, no-argument constructor, getters and setters for all fields

    // Fields
    private int orderHistoryId;
    private int orderId;

    private String oldOrderStatus;
    private String newOrderStatus;

    private String oldFulfillmentStatus;
    private String newFulfillmentStatus;

    private LocalDateTime changedAt;
    private String notes;

    // No-argument constructor
    public OrderHistory() {
    }

    // Full constructor
    public OrderHistory(int orderHistoryId, int orderId, String oldOrderStatus, String newOrderStatus, String oldFulfillmentStatus, String newFulfillmentStatus, LocalDateTime changedAt, String notes) {
        this.orderHistoryId = orderHistoryId;
        this.orderId = orderId;
        this.oldOrderStatus = oldOrderStatus;
        this.newOrderStatus = newOrderStatus;
        this.oldFulfillmentStatus = oldFulfillmentStatus;
        this.newFulfillmentStatus = newFulfillmentStatus;
        this.changedAt = changedAt;
        this.notes = notes;
    }

    // Getters and setters
    public int getOrderHistoryId() {
        return orderHistoryId;
    } 

    public void setOrderHistoryId(int orderHistoryId) {
        this.orderHistoryId = orderHistoryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOldOrderStatus() {
        return oldOrderStatus;
    }

    public void setOldOrderStatus(String oldOrderStatus) {
        this.oldOrderStatus = oldOrderStatus;
    }

    public String getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(String newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    public String getOldFulfillmentStatus() {
        return oldFulfillmentStatus;
    }

    public void setOldFulfillmentStatus(String oldFulfillmentStatus) {
        this.oldFulfillmentStatus = oldFulfillmentStatus;
    }  

    public String getNewFulfillmentStatus() {
        return newFulfillmentStatus;
    }

    public void setNewFulfillmentStatus(String newFulfillmentStatus) {
        this.newFulfillmentStatus = newFulfillmentStatus;
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
