package com.capstoneshipping.dao;

import java.util.List;

import com.capstoneshipping.model.OrderHistory;

public interface OrderHistoryDAO {
    
    //Get all orders
    List<OrderHistory> getAllOrders();

    
    //insert orders to order history when fulfilled
    void insertOrderHistory(OrderHistory orderHistory);

    //check if order history exists for a given order ID
    boolean orderHistoryExists(int orderId);

}

