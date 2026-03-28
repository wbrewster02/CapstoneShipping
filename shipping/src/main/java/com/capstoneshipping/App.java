package com.capstoneshipping;

import java.sql.ResultSet;
import java.sql.SQLException;


public class App 
{
    public static void main( String[] args )
    {
        DataBaseConnection dataBase = new DataBaseConnection();
        ResultSet resultSet = null;

        try{
            resultSet = dataBase.Execute("SELECT * FROM Customer");
            while (resultSet.next()){
                int id = resultSet.getInt("Customer_ID");
                String firstName = resultSet.getString("First_Name").trim();
                String lastName = resultSet.getString("Last_Name").trim();
                String Email = resultSet.getString("Email").trim();
                String Phone = resultSet.getString("Phone").trim();
 
 
                System.out.println("ID: " + id + "\nFirst Name: " + firstName + "\nLast Name: "+ lastName + "\nEmail: " + Email + "\nPhone: " + Phone);
            }
        } catch(SQLException e){
            System.err.println(e);
        }
    }
}
