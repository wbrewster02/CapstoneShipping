// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.OrderHistory version: 1.0
// Date Modified: 4/19/2026


package com.capstoneshipping.model;

import java.time.LocalDateTime;


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

    //Order Table fields, used for display purposes in the OrderHistoryView
    private int customerId;
    private LocalDateTime orderDate;
    private LocalDateTime fulfilledAt;


    // No-argument constructor
    public OrderHistory() {
    }

    // Full constructor
    public OrderHistory(
        int orderHistoryId, int orderId, 
        String oldOrderStatus, String newOrderStatus, 
        String oldFulfillmentStatus, String newFulfillmentStatus, 
        LocalDateTime changedAt, String notes,
        int customerId, LocalDateTime orderDate, 
        LocalDateTime fulfilledAt
            ) 
        {
        this.orderHistoryId = orderHistoryId;
        this.orderId = orderId;
        this.oldOrderStatus = oldOrderStatus;
        this.newOrderStatus = newOrderStatus;
        this.oldFulfillmentStatus = oldFulfillmentStatus;
        this.newFulfillmentStatus = newFulfillmentStatus;
        this.changedAt = changedAt;
        this.notes = notes;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.fulfilledAt = fulfilledAt;

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getFulfilledAt() {
        return fulfilledAt;
    }

    public void setFulfilledAt(LocalDateTime fulfilledAt) {
        this.fulfilledAt = fulfilledAt;
    }
}
