// Daniel Munoz, William Brewster, Mikenzie Adkins.
// DataBase.DB_Connection version: 1.1
// Date Modified: 4/17/2026

package com.capstoneshipping.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
// Creates a single MySql Connection.
public class DB_Connection{

    // Connection/Config Attributes
    private static Connection connection = null;
    private static DB_Config config = null;
    
    // Establish DB_Connection
    public DB_Connection(){
        
        try{
            if (config == null){
                // Establish Configuration values.
                config = new DB_Config();

                System.out.println("DB_Config.Instance.Complete");
            }
            if (connection == null || connection.isClosed()) {
                // Establish Connection, utilizing DB_Config Attributes: (DB_Url, DB_User, DB_Password).
                connection = DriverManager.getConnection(config.getDB_Url(), 
                                                         config.getDB_User(), 
                                                         config.getDB_Password()
                                                        );
                System.out.println("DB connection established");
            }
            // Establish Connection, utilizing DB_Config Attributes: (DB_Url, DB_User, DB_Password).
            
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }

       
    }

    // Get Connection.   
    public static Connection getConnection(){
        try {
            // Check if connection is null, closed, or no longer valid (2 sec timeout)
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {
                System.out.println("Connection invalid or timed out. Reconnecting...");
                new DB_Connection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new DB_Connection();
        }
        System.out.println("Connection Stable.");
        return connection;
    }
    public static void resetConnection() {
        try {
            if (connection != null) {
                CloseConnections();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    // Close opened connections.   
    public static void CloseConnections() throws SQLException {
        // Close Connection if != null
        if (connection != null){
            connection.close();
            System.out.println(connection + " Closed.");
        }

    }

 
}