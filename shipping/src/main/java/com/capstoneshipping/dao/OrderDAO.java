// Daniel Munoz, William Brewster, Mikenzie Adkins.
// dao.OrderDAO version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.capstoneshipping.model.FulfillmentStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderStatus;

/**
 * This interface defines the contract for data access operations related to orders in the Capstone Shipping application. 
 * It includes methods for retrieving orders, filtering them by status, and updating their statuses. 
 * Implementations of this interface will handle the actual database interactions to perform these operations.
 */
public interface OrderDAO {

    //Get all orders
    List<Order> getAllOrders();

    //Update order status (ex:  Pending → Paid → Fulfilled)
    void updateOrderStatus(int orderId, OrderStatus newStatus);

    //Update fulfillment status (Ex: Processing → Packed → ReadyToShip)
    void updateFulfillmentStatus(int orderId, FulfillmentStatus newStatus);

    // update fulfilledAt timestamp when order is marked as fulfilled
    void updateFulfilledAt(int orderId, LocalDateTime fulfilledAt);
}