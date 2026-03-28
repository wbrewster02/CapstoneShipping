package com.capstoneshipping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Class;

 
 
 
 
public class DataBaseConnection{

    private static Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    public DataBaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (in == null) {
                    throw new FileNotFoundException("db.properties not found on classpath. Put this file in src/main/resources.");
                }
                props.load(in);
            }

            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String dbname = props.getProperty("db.name");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?serverTimezone=UTC&useSSL=false";

            System.out.println("Connecting to DB URL: " + url + " user: " + user);
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("DB connection established");

        } catch (SQLException exception){
            System.err.println(exception);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultSet Execute(String query) throws SQLException{
        if (connection == null) {
            throw new SQLException("Cannot execute query: database connection is not initialized.");
        }

        this.statement = connection.createStatement();
        this.resultSet = statement.executeQuery(query);

        return resultSet;

    }
    


    public void CloseConnections() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

 
}