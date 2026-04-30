// Daniel Munoz, William Brewster, Mikenzie Adkins.
// DataBase.DB_Queries version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.DataBase;

// this class Stores SQL Queries as Constants to be used within java.com.capstoneshipping.dao.* .
public class DB_Queries {

    // ORDER_QUERIES
    public static final String GET_ALL_ORDERS = 
        "SELECT * FROM " + DB_Constants.ORDER_TABLE;

    public static final String UPDATE_ORDER_STATUS =
        "UPDATE `Order` SET Order_Status = ? WHERE Order_ID = ?";

    public static final String UPDATE_FULFILLMENT_STATUS =
        "UPDATE `Order` SET Fulfillment_Status = ? WHERE Order_ID = ?";

    public static final String UPDATE_FULFILLED_AT =
        "UPDATE `Order` SET Fulfilled_At = ? WHERE Order_ID = ?";
    
    //

    // shipping QUERIES
    public static final String GET_ALL_SHIPPING = 
        "SELECT * FROM " + DB_Constants.SHIPPING_TABLE;

    public static final String UPDATE_SHIPPING_STATUS =
        "UPDATE " + DB_Constants.SHIPPING_TABLE + " SET " + DB_Constants.SHIPPING_STATUS + 
        " = ?, " + DB_Constants.SHIPPING_STATUS_UPDATED_AT + " = ? WHERE " + DB_Constants.SHIPPING_ID + " = ?";

    public static final String SHIPPING_EXISTS =
        "SELECT COUNT(*) FROM " + DB_Constants.SHIPPING_TABLE + " WHERE " + DB_Constants.ORDER_ID + " = ?";

    public static final String UPDATE_SHIPPING_STATUS_BY_ORDER_ID =
        "UPDATE " + DB_Constants.SHIPPING_TABLE +
        " SET " + DB_Constants.SHIPPING_STATUS + " = ?, " +
        DB_Constants.SHIPPING_STATUS_UPDATED_AT + " = ? " +
        "WHERE " + DB_Constants.SHIPPING_ORDER_ID + " = ?";

    public static final String RESET_SHIPPING_BY_ORDER_ID =
        "UPDATE " + DB_Constants.SHIPPING_TABLE + " SET " +
        DB_Constants.SHIPPING_STATUS + " = ?, " +
        DB_Constants.SHIPPING_SHIPPED_ON + " = ?, " +
        DB_Constants.SHIPPING_EXPECTED_BY + " = ?, " +
        DB_Constants.SHIPPING_STATUS_UPDATED_AT + " = ? " +
        "WHERE " + DB_Constants.SHIPPING_ORDER_ID + " = ?";
    //

    // CUSTOMER_QUERIES.
    public static final String GET_CUSTOMER_BY_ID = 
        "SELECT * FROM " + DB_Constants.CUSTOMER_TABLE + 
        " WHERE " + DB_Constants.CUSTOMER_ID + " = ?";
        // Retrieve Customer by ID.
    
    public static final String INSERT_CUSTOMER =
        "INSERT INTO " + DB_Constants.CUSTOMER_TABLE +
        " (first_name, last_name, email) VALUES (?, ?, ?)";
        // Insert new customer(first_name, last_name, email). 
    //

    // SHOPPING_CART_QUERIES.
    public static final String GET_CART_ITEMS_BY_CART = 
        "SELECT * FROM " + DB_Constants.SHOPPING_CART_TABLE + 
        " WHERE " + DB_Constants.SHOPPING_CART_CUSTOMER_ID + " = ?";
        // Retrieves Shopping cart items by CUSTOMER_ID.     
    //          
    

    // ORDER_HISTORY
    // public static final String GET_ALL_ORDER_HISTORY = 
    //     "SELECT * FROM " + DB_Constants.ORDER_HISTORY_TABLE;

    public static final String GET_ALL_ORDER_HISTORY =
        "SELECT " +
        "oh.Order_History_ID, " +
        "oh.Order_ID, " +
        "oh.Old_Order_Status, " +
        "oh.New_Order_Status, " +
        "oh.Old_Fulfillment_Status, " +
        "oh.New_Fulfillment_Status, " +
        "oh.Changed_At, " +
        "oh.Notes, " +
        "o.Customer_ID, " +
        "o.Order_Date, " +
        "o.Fulfilled_At " +
        "FROM " + DB_Constants.ORDER_HISTORY_TABLE + " oh " +
        "JOIN " + DB_Constants.ORDER_TABLE + " o " +
        "ON oh.Order_ID = o.Order_ID";


    public static final String INSERT_ORDER_HISTORY =
        "INSERT INTO " + DB_Constants.ORDER_HISTORY_TABLE + " (" +
        DB_Constants.ORDER_HISTORY_ORDER_ID + ", " +
        DB_Constants.ORDER_HISTORY_OLD_ORDER_STATUS + ", " +
        DB_Constants.ORDER_HISTORY_NEW_ORDER_STATUS + ", " +
        DB_Constants.ORDER_HISTORY_OLD_FULFILLMENT_STATUS + ", " +
        DB_Constants.ORDER_HISTORY_NEW_FULFILLMENT_STATUS + ", " +
        DB_Constants.ORDER_HISTORY_CHANGED_AT + ", " +
        DB_Constants.ORDER_HISTORY_NOTES +
        ") VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String ORDER_HISTORY_EXISTS =
        "SELECT COUNT(*) FROM " + DB_Constants.ORDER_HISTORY_TABLE +
        " WHERE " + DB_Constants.ORDER_HISTORY_ORDER_ID + " = ?";
    //

    // SHIPPING_HISTORY

    public static final String GET_ALL_SHIPPING_HISTORY =
        "SELECT " +
        "sh.Shipping_History_ID, " +
        "sh.Shipping_ID, " +
        "sh.Old_Status, " +
        "sh.New_Status, " +
        "sh.Changed_At, " +
        "sh.Notes, " +
        "s.Order_ID, " +
        "s.Carrier, " +
        "s.Tracking_Number, " +
        "s.Ship_Status, " +
        "s.Shipped_On, " +
        "s.Expected_By " +
        "FROM " + DB_Constants.SHIPPING_HISTORY_TABLE + " sh " +
        "JOIN " + DB_Constants.SHIPPING_TABLE + " s " +
        "ON sh.Shipping_ID = s.Shipping_ID";


    public static final String INSERT_SHIPPING_HISTORY =
        "INSERT INTO " + DB_Constants.SHIPPING_HISTORY_TABLE + " (" +
        DB_Constants.SHIPPING_HISTORY_SHIPPING_ID + ", " +
        DB_Constants.SHIPPING_HISTORY_OLD_STATUS + ", " +
        DB_Constants.SHIPPING_HISTORY_NEW_STATUS + ", " +
        DB_Constants.SHIPPING_HISTORY_CHANGED_AT + ", " +
        DB_Constants.SHIPPING_HISTORY_NOTES +
        ") VALUES (?, ?, ?, ?, ?)";

    public static final String SHIPPING_HISTORY_EXISTS =
        "SELECT COUNT(*) FROM " + DB_Constants.SHIPPING_HISTORY_TABLE +
        " WHERE " + DB_Constants.SHIPPING_HISTORY_SHIPPING_ID + " = ?";
    //
}