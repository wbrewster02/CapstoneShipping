
package com.capstoneshipping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capstoneshipping.DataBase.DB_Connection;
import com.capstoneshipping.DataBase.DB_Constants;
import com.capstoneshipping.DataBase.DB_Queries;
import com.capstoneshipping.model.OrderHistory;
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
                    rs.getString(DB_Constants.ORDER_HISTORY_NOTES)
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
    
}
