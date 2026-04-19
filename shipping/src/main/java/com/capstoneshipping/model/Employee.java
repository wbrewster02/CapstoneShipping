// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.Employee version: 1.0
// Date Modified: 4/19/2026

package com.capstoneshipping.model;


public class Employee {
    private String Username;
    private String Password;
    
    // No-arg constructor required by Jackson
    public Employee() {}
    
    public Employee(String Username, String Password){
        setUsername(Username);
        setPassword(Password);

    }

    // get/set Username
    public String getUsername(){
        return this.Username;
    }
    public void setUsername(String Username){
        this.Username = Username;
    }

    // get/set Password
    public String getPassword(){
        return this.Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }

    
}
