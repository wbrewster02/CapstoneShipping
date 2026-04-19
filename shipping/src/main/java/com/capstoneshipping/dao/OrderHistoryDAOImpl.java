// NEEDS FIXING


// package com.capstoneshipping.dao;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Timestamp;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import com.capstoneshipping.DataBase.DB_Connection;
// import com.capstoneshipping.DataBase.DB_Constants;
// import com.capstoneshipping.DataBase.DB_Queries;
// import com.capstoneshipping.model.FulfillmentStatus;
// import com.capstoneshipping.model.Order;
// import com.capstoneshipping.model.OrderHistory;
// import com.capstoneshipping.model.OrderStatus;
// public class OrderHistoryDAOImpl implements OrderHistoryDAO{
//     private Connection connection;

//     public List<Order> getAllOrders() {
//         List<Order> orders = new ArrayList<>();
        
//         if (this.connection == null){
//             this.connection = DB_Connection.getConnection();
//         }

//         try (PreparedStatement stmt = connection.prepareStatement(DB_Queries.GET_ALL_ORDERS);

//             ResultSet rs = stmt.executeQuery()) {

//             // while(rs.next()) {
//             //     // int orderHistoryId, int orderId, String oldOrderStatus, String newOrderStatus, String oldFulfillmentStatus, String newFulfillmentStatus, LocalDateTime changedAt, String notes
//             //     OrderHistory order = new OrderHistory(
//             //     rs.getInt(DB_Constants.ORDER_ID),
//             //     rs.getInt(DB_Constants.CUSTOMER_ID),
//             //     rs.getTimestamp(DB_Constants.ORDER_DATE).toLocalDateTime(),
//             //     rs.getString(DB_Constants.ORDER_STATUS),
//             //     rs.getString(DB_Constants.FULFILLMENT_STATUS),
//             //     rs.getTimestamp(DB_Constants.FULFILLED_AT) != null
//             //         ? rs.getTimestamp(DB_Constants.FULFILLED_AT).toLocalDateTime()
//             //         : null
//             //     );
//             //     orders.add(OrderHistory);

//             //     }
 
//             stmt.close();

//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//             return orders;
//     }
// }
