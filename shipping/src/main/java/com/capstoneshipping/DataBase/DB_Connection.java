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
    private DB_Config config = null;
    
    // Establish DB_Connection
    public DB_Connection(){
        
        try{
            // Establish Configuration values.
            this.config = new DB_Config();
            System.out.println("DB_Config.Instance.Complete");

            // Establish Connection, utilizing DB_Config Attributes: (DB_Url, DB_User, DB_Password).
            connection = DriverManager.getConnection(this.config.getDB_Url(), this.config.getDB_User(), this.config.getDB_Password());
            System.out.println("DB connection established");
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
        // Get Connection if != null.
        if (connection != null){
            return connection;
        }
        
        new DB_Connection();
        
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
            connection.
            System.out.println(connection + " Closed.");
        }

    }

 
}