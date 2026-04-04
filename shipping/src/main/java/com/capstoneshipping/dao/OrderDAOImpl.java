// Daniel Munoz, William Brewster, Mikenzie Adkins.
// dao.OrderDAOImpl version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.dao;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    private Connection connection;

    public OrderDAOImpl() {
        this.connection = DB_Connection.getConnection();
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        // Order is a reserved keyword in SQL, so we need to escape it with brackets. If it fails to work, try backticks (`Order`) instead.
        // MUST use backticks

        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_ORDERS);

            ResultSet rs = stmt.executeQuery()) {

                while(rs.next()) {

                    Order order = new Order(


                        rs.getInt(DB_Constants.ORDER_ID),
                        rs.getInt(DB_Constants.CUSTOMER_ID),
                        rs.getTimestamp(DB_Constants.ORDER_DATE).toLocalDateTime(),


                        null, // Placeholder for orderStatus
                        null, // Placeholder for fulfillmentStatus
                        null  // Placeholder for fulfilledAt
                        // rs.getString("Order_Status"),
                        // rs.getString("Fulfillment_Status"),
                        // rs.getTimestamp("Fulfilled_At") != null ? rs.getTimestamp("Fulfilled_At").toLocalDateTime() : null
                    );

                    orders.add(order);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return orders;
    }

    @Override
    public List<Order> getOrdersByOrderStatus(String orderStatus) {
        return new ArrayList<>();
    }

    @Override
    public List<Order> getOrdersByFulfillmentStatus(String fulfillmentStatus) {
        return new ArrayList<>();
    }

    @Override
    public void updateOrderStatus(int orderId, String newStatus) {

    }

    @Override
    public void updateFulfillmentStatus(int orderId, String newStatus) {

    }
}
