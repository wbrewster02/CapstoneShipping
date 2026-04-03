package com.capstoneshipping.DataBase;

// this class Stores SQL Queries as Constants to be used in the DB_Accessor and Result mapper.
public class DB_Queries {
    
    //CUSTOMER_QUERIES
    
    public static final String GET_CUSTOMER_BY_ID = 
        "GET * FROM " + DB_Constants.CUSTOMER_TABLE + 
        " WHERE" + DB_Constants.CUSTOMER_ID + " = ?";
        // Retrieve Customer by ID //

    public static final String INSERT_CUSTOMER =
        "INSERT INTO " + DB_Constants.CUSTOMER_TABLE +
        " (first_name, last_name, email) VALUES (?, ?, ?)";
        // Insert new customer(first_name, last_name, email) // 
    
    
    public static final String GET_CART_ITEMS_BY_CART = 
        "SELECT * FROM " + DB_Constants.SHOPPING_CART_TABLE + 
        " WHERE " + DB_Constants.SHOPPING_CART_CUSTOMER_ID + " = ?";
        // Retrieves Shopping cart items by CUSTOMER_ID //     
            
            
            
            
            
            
}