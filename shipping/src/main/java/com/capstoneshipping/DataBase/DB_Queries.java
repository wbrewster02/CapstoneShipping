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
    public static final String GET_ALL_ORDER_HISTORY = 
        "SELECT * FROM " + DB_Constants.ORDER_HISTORY_TABLE;
}