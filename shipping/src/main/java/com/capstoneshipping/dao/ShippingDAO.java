package com.capstoneshipping.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.capstoneshipping.model.Shipping;
import com.capstoneshipping.model.ShippingStatus;

public interface ShippingDAO {
    List<Shipping> getAllShipments();

    void updateShippingStatus(int shippingId, ShippingStatus status);

    void updateShippedOn(int shippingId, LocalDateTime shippedOn);

    void updateExpectedBy(int shippingId, LocalDateTime expectedBy);

    boolean shippingExistsForOrder(int orderId); //method to check if shipping object exists for a given order ID

    void updateShippingStatusByOrderId(int orderId, ShippingStatus status);

    void resetShippingForOrder(int orderId);
}
