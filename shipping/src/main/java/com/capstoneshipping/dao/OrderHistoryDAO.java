package com.capstoneshipping.dao;

import java.util.List;

import com.capstoneshipping.model.OrderHistory;

public interface OrderHistoryDAO {
    
    //Get all orders
    List<OrderHistory> getAllOrders();

}

