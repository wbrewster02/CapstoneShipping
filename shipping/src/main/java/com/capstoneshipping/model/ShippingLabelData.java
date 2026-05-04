package com.capstoneshipping.model;

public class ShippingLabelData {

    private int orderId;
    private String customerName;
    private String shippingAddress;
    private String returnAddress;
    private String trackingNumber;
    private String carrier;

    // Constructor
    public ShippingLabelData(int orderId, String customerName, String shippingAddress,
        String returnAddress, String trackingNumber, String carrier) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.returnAddress = returnAddress;
        this.trackingNumber = trackingNumber;
        this.carrier = carrier;

    }

    // Getters
    public int getOrderId() { return orderId; }

    public String getCustomerName() { return customerName; }

    public String getShippingAddress() { return shippingAddress; }

    public String getReturnAddress() { return returnAddress; }

    public String getTrackingNumber() { return trackingNumber; }
    
    public String getCarrier() { return carrier; }
}
