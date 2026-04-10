// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.Order version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.model;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private int customerId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private String fulfillmentStatus;
    private LocalDateTime fulfilledAt;

    public Order() {
        
    }

    public Order(int orderId, int customerId, LocalDateTime orderDate, String orderStatus, String fulfillmentStatus, LocalDateTime fulfilledAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.fulfillmentStatus = fulfillmentStatus;
        this.fulfilledAt = fulfilledAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public LocalDateTime getFulfilledAt() {
        return fulfilledAt;
    }

    public void setFulfilledAt(LocalDateTime fulfilledAt) {
        this.fulfilledAt = fulfilledAt;
    }
}


// @Override
// public String toString() {
//     return "Order{" +
//             "orderId=" + orderId +
//             ", customerId=" + customerId +
//             ", orderDate=" + orderDate +
//             ", orderStatus='" + orderStatus + '\'' +
//             ", fulfillmentStatus='" + fulfillmentStatus + '\'' +
//             ", fulfilledAt=" + fulfilledAt +
//             '}';
// }