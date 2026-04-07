// Daniel Munoz, William Brewster, Mikenzie Adkins.
// DataBase.DB_Config version: 1.0
// Date Modified: 4/3/2026

package com.capstoneshipping.DataBase;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Class;


// this file will handle DataBase configuration variables through a .properties file 
// located in the resources folder.
public class DB_Config {

    // Server Connection Attributes.
    private String DB_Host;
    private String DB_Port;
    private String DB_Name;
    private String DB_Url;

    // User Attributes
    private String DB_User;
    private String DB_Password;

    // Retrieves properties from src/main/resources/db.properties.
    public DB_Config() throws ClassNotFoundException {

        // MySql driver.
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        Properties props = new Properties();
        
        // Retrieving db.properties file location.
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new FileNotFoundException("db.properties not found on classpath. Put this file in src/main/resources.");
            }
            props.load(in);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        // DB_URL Properties: host, port, name
        setDB_Host(props.getProperty("db.host"));
        setDB_Port(props.getProperty("db.port"));
        setDB_Name(props.getProperty("db.name"));

        setDB_Url("jdbc:mysql://" + this.DB_Host + ":" + this.DB_Port + "/" + this.DB_Name + "?serverTimezone=UTC&useSSL=false");

        // User Access properties.
        setDB_User(props.getProperty("db.user"));
        setDB_Password(props.getProperty("db.password"));

        System.out.println("DB_Config.Complete");

    } // End Constructor

    // Host get/set.
    public String getDB_Host(){
        return this.DB_Host;
    }
    public void setDB_Host(String DB_Host){
        this.DB_Host = DB_Host;
    }

    // Port get/set.
    public String getDB_Port(){
        return this.DB_Port;
    }
    public void setDB_Port(String DB_Port){
        this.DB_Port = DB_Port;
    }

    // Name get/set.
    public String getDB_Name(){
        return this.DB_Name;
    }
    public void setDB_Name(String DB_Name){
        this.DB_Name = DB_Name;
    }

    // Url get/set.
    public String getDB_Url(){
        return this.DB_Url;
    }
    public void setDB_Url(String DB_URL){
        this.DB_Url = DB_URL;
    }

    // User get/set.
    public String getDB_User(){
        return this.DB_User;
    }
    public void setDB_User(String DB_User){
        this.DB_User = DB_User;
    }

    // Password get/set.
    public String getDB_Password(){
        return this.DB_Password;
    }
    public void setDB_Password(String DB_Password){
        this.DB_Password = DB_Password;
    }

}