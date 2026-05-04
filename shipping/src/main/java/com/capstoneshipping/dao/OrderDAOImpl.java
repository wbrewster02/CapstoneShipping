// Daniel Munoz, William Brewster, Mikenzie Adkins.
// dao.OrderDAOImpl version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.FulfillmentStatus;
import com.capstoneshipping.model.Order;
import com.capstoneshipping.model.OrderStatus;

public class OrderDAOImpl implements OrderDAO {

    private Connection connection;

    public OrderDAOImpl() {
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }
            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_ORDERS);

                ResultSet rs = stmt.executeQuery()) {

                while(rs.next()) {
                    String firstName = rs.getString("First_Name");
                    String lastName = rs.getString("Last_Name");
                    String customerName = firstName.charAt(0) + ". " + lastName;
                    
                    Order order = new Order(
                    rs.getInt(DB_Constants.ORDER_ID),
                    rs.getInt(DB_Constants.CUSTOMER_ID),
                    customerName,
                    rs.getTimestamp(DB_Constants.ORDER_DATE).toLocalDateTime(),
                    mapOrderStatus(rs.getString(DB_Constants.ORDER_STATUS)),
                    mapFulfillmentStatus(rs.getString(DB_Constants.FULFILLMENT_STATUS)),
                    rs.getTimestamp(DB_Constants.FULFILLED_AT) != null
                        ? rs.getTimestamp(DB_Constants.FULFILLED_AT).toLocalDateTime()
                        : null
                    );

                    orders.add(order);

                }
    
                stmt.close();
                rs.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return orders;
    }

    private OrderStatus mapOrderStatus(String dbStatus) {
        return switch (dbStatus) {
            case "Pending" -> OrderStatus.PENDING;
            case "Paid" -> OrderStatus.PAID;
            case "ReadyForFulfillment" -> OrderStatus.READY_FOR_FULFILLMENT;
            case "Fulfilled" -> OrderStatus.FULFILLED;
            case "Cancelled" -> OrderStatus.CANCELLED;
            default -> throw new IllegalArgumentException("Unknown order status: " + dbStatus);
        };
    }

    private FulfillmentStatus mapFulfillmentStatus(String dbStatus) {
        return switch (dbStatus) {
            case "Pending" -> FulfillmentStatus.PENDING;
            case "Processing" -> FulfillmentStatus.PROCESSING;
            case "Packed" -> FulfillmentStatus.PACKED;
            case "ReadyToShip" -> FulfillmentStatus.READY_TO_SHIP;
            case "Fulfilled" -> FulfillmentStatus.FULFILLED;
            default -> throw new IllegalArgumentException("Unknown fulfillment status: " + dbStatus);
        };
    }


    @Override
    public void updateOrderStatus(int orderId, OrderStatus newStatus) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.UPDATE_ORDER_STATUS)) {
            stmt.setString(1, newStatus.toDbValue());
            stmt.setInt(2, orderId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFulfillmentStatus(int orderId, FulfillmentStatus newStatus) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.UPDATE_FULFILLMENT_STATUS)) {
            stmt.setString(1, newStatus.toDbValue()); //here
            stmt.setInt(2, orderId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFulfilledAt(int orderId, LocalDateTime fulfilledAt) {
        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.UPDATE_FULFILLED_AT)) {
            stmt.setTimestamp(1, Timestamp.valueOf(fulfilledAt));
            stmt.setInt(2, orderId);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




    //Check OrderDAO for clarity on why these methods are currently unused and commented out.
    // @Override
    // public List<Order> getOrdersByOrderStatus(String orderStatus) {
    //     return new ArrayList<>();
    // }

    // @Override
    // public List<Order> getOrdersByFulfillmentStatus(String fulfillmentStatus) {
    //     return new ArrayList<>();
    // }