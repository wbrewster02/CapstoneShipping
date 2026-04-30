package com.capstoneshipping.dao;

import java.util.List;

import com.capstoneshipping.model.ShippingHistory;

public interface ShippingHistoryDAO{
    List<ShippingHistory> getAllShippingHistory();

    void insertShippingHistory(ShippingHistory history);

    boolean shippingHistoryExists(int shippingId);
}
