// Daniel Munoz, William Brewster, Mikenzie Adkins.
// dao.OrderDAO version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.dao;

//import java.time.LocalDateTime;

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

    //List<Order> getOrderById(int orderId); 
    //? unsure if needed. We can also just retrieve the order from the full list of orders we get from getAllOrders() and filter it in the UI.

    //void updateOrderState(int orderID, String orderStatus, String fulfillmentStatus, LocalDateTime fulfilledAt); 

    //state change can be updating order status (ex: Pending → Paid → Fulfilled)
    //  or updating fulfillment status (Ex: Processing → Packed → ReadyToShip). 
    // We can have one method that updates both since they will often be updated together, 
    // but we can also have separate methods if we want more granular control. 
    // For now, I'll just include one method that can update both statuses and the fulfilledAt timestamp, 
    // and we can adjust as needed when we implement the UI and see how we want to handle status updates there.

    //These filter methods are currently unused, but we may want to implement them in the future to allow for more specific searching/filtering in the UI. 
    //For now, the UI search will filter on the full list of orders retrieved by getAllOrders() and won't rely on these methods.
    // //filter by order status
    // List<Order> getOrdersByOrderStatus(String orderStatus);

    // //Filter by fulfillment status
    // List<Order> getOrdersByFulfillmentStatus(String fulfillmentStatus);

    //Update order status (ex:  Pending → Paid → Fulfilled)
    void updateOrderStatus(int orderId, String newStatus);

    //Update fulfillment status (Ex: Processing → Packed → ReadyToShip)
    void updateFulfillmentStatus(int orderId, String newStatus);
}
