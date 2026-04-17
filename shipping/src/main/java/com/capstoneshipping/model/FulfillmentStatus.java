package com.capstoneshipping.model;

public enum FulfillmentStatus {
    PENDING,
    PROCESSING,
    PACKED,
    READY_TO_SHIP, //DB USES 'ReadyToShip'
    FULFILLED;

    public FulfillmentStatus next() {
        return switch (this) {
            case PENDING -> PROCESSING;
            case PROCESSING -> PACKED;
            case PACKED -> READY_TO_SHIP;
            case READY_TO_SHIP -> FULFILLED;
            case FULFILLED -> FULFILLED; // No next status after fulfilled
        };
    }

    public String toDbValue() {
        return switch (this) {
            case PENDING -> "Pending";
            case PROCESSING -> "Processing";
            case PACKED -> "Packed";
            case READY_TO_SHIP -> "ReadyToShip";
            case FULFILLED -> "Fulfilled";
        };
    }
}
