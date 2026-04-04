// Daniel Munoz, William Brewster, Mikenzie Adkins.
// dao.OrderDAO version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.dao;

import java.util.List;
import com.capstoneshipping.model.Order;

/**
 * This interface defines the contract for data access operations related to orders in the Capstone Shipping application. 
 * It includes methods for retrieving orders, filtering them by status, and updating their statuses. 
 * Implementations of this interface will handle the actual database interactions to perform these operations.
 */
public interface OrderDAO {

    //Get all orders
    List<Order> getAllOrders();

    //filter by order status
    List<Order> getOrdersByOrderStatus(String orderStatus);

    //Filter by fulfillment status
    List<Order> getOrdersByFulfillmentStatus(String fulfillmentStatus);

    //Update order status (ex:  Pending → Paid → Fulfilled)
    void updateOrderStatus(int orderId, String newStatus);

    //Update fulfillment status (Ex: Processing → Packed → ReadyToShip)
    void updateFulfillmentStatus(int orderId, String newStatus);
}
