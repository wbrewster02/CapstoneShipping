
package com.capstoneshipping.dao;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.OrderHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDAOImpl implements OrderHistoryDAO{
    private Connection connection;

    public List<OrderHistory> getAllOrders() {
        List<OrderHistory> ordersHistory = new ArrayList<>();
        
        if (this.connection == null){
            this.connection = DB_Connection.getConnection();
        }

        try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_ORDER_HISTORY);

            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                
                OrderHistory order = new OrderHistory(
                    rs.getInt(DB_Constants.ORDER_HISTORY_ID),
                    rs.getInt(DB_Constants.ORDER_HISTORY_ORDER_ID),
                    rs.getString(DB_Constants.ORDER_HISTORY_OLD_ORDER_STATUS),
                    rs.getString(DB_Constants.ORDER_HISTORY_NEW_ORDER_STATUS),
                    rs.getString(DB_Constants.ORDER_HISTORY_OLD_FULFILLMENT_STATUS),
                    rs.getString(DB_Constants.ORDER_HISTORY_NEW_FULFILLMENT_STATUS),
                        rs.getTimestamp(DB_Constants.ORDER_HISTORY_CHANGED_AT) != null
                    ? rs.getTimestamp(DB_Constants.ORDER_HISTORY_CHANGED_AT).toLocalDateTime()
                    : null,
                    rs.getString(DB_Constants.ORDER_HISTORY_NOTES),

                    // These fields are from the Order table, included in the query for display purposes in the OrderHistoryView
                    rs.getInt(DB_Constants.ORDER_CUSTOMER_ID),
                    rs.getTimestamp(DB_Constants.ORDER_DATE) != null
                    ? rs.getTimestamp(DB_Constants.ORDER_DATE).toLocalDateTime()
                    : null,
                    rs.getTimestamp(DB_Constants.FULFILLED_AT) != null
                    ? rs.getTimestamp(DB_Constants.FULFILLED_AT).toLocalDateTime()
                    : null
                    );

                ordersHistory.add(order);

                }
 
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return ordersHistory;
    }

    @Override
    public void insertOrderHistory(OrderHistory orderHistory) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.INSERT_ORDER_HISTORY)) {

                stmt.setInt(1, orderHistory.getOrderId());
                stmt.setString(2, orderHistory.getOldOrderStatus());
                stmt.setString(3, orderHistory.getNewOrderStatus());
                stmt.setString(4, orderHistory.getOldFulfillmentStatus());
                stmt.setString(5, orderHistory.getNewFulfillmentStatus());

                if (orderHistory.getChangedAt() != null) {
                    stmt.setTimestamp(6, Timestamp.valueOf(orderHistory.getChangedAt()));
                } else {
                    stmt.setNull(6, Types.TIMESTAMP);
                }

                stmt.setString(7, orderHistory.getNotes());

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean orderHistoryExists(int orderId) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = DB_Connection.getConnection();
            }

            try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.ORDER_HISTORY_EXISTS)) {
                stmt.setInt(1, orderId);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    
}
