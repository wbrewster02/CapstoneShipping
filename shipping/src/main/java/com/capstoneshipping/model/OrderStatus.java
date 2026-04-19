// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.OrderStatus version: 1.0
// Date Modified: 4/19/2026

package com.capstoneshipping.model;

public enum OrderStatus {
    PENDING,
    PAID,
    READY_FOR_FULFILLMENT, //DB USES 'ReadyForFulfillment'
    FULFILLED,
    CANCELLED;

    public OrderStatus next() {
        return switch (this) {
            case PENDING -> PAID;
            case PAID -> READY_FOR_FULFILLMENT;
            case READY_FOR_FULFILLMENT -> FULFILLED;
            case FULFILLED, CANCELLED -> this; // No next status after fulfilled or cancelled
        };
    }

    public String toDbValue() {
        return switch (this) {
            case PENDING -> "Pending";
            case PAID -> "Paid";
            case READY_FOR_FULFILLMENT -> "ReadyForFulfillment";
            case FULFILLED -> "Fulfilled";
            case CANCELLED -> "Cancelled";
        };
    }
}
