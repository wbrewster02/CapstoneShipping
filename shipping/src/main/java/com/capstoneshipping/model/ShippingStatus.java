package com.capstoneshipping.model;

public enum ShippingStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    RETURNED;

    public ShippingStatus next() {
        return switch (this) {
            case PENDING -> SHIPPED;
            case SHIPPED -> DELIVERED;
            case DELIVERED -> RETURNED;
            case RETURNED -> this; // No next status after returned
        };
    }

    public String toDbValue() {
        return switch (this) {
            case PENDING -> "Pending";
            case SHIPPED -> "Shipped";
            case DELIVERED -> "Delivered";
            case RETURNED -> "Returned";
        };
    }

    public static ShippingStatus fromDbValue(String value) {
        return switch (value.toUpperCase()) {
            case "PENDING" -> PENDING;
            case "SHIPPED" -> SHIPPED;
            case "DELIVERED" -> DELIVERED;
            case "RETURNED" -> RETURNED;
            default -> throw new IllegalArgumentException("Unknown status: " + value);
        };
    }
}




    // public OrderStatus next() {
    //     return switch (this) {
    //         case PENDING -> PAID;
    //         case PAID -> READY_FOR_FULFILLMENT;
    //         case READY_FOR_FULFILLMENT -> FULFILLED;
    //         case FULFILLED, CANCELLED -> this; // No next status after fulfilled or cancelled
    //     };
    // }

    // public String toDbValue() {
    //     return switch (this) {
    //         case PENDING -> "Pending";
    //         case PAID -> "Paid";
    //         case READY_FOR_FULFILLMENT -> "ReadyForFulfillment";
    //         case FULFILLED -> "Fulfilled";
    //         case CANCELLED -> "Cancelled";
    //     };
    // }
