// Daniel Munoz, William Brewster, Mikenzie Adkins.
// model.Employee version: 1.0
// Date Modified: 4/19/2026

package com.capstoneshipping.model;


public class Employee {
    private String Username;
    private String Password;
    private String Access;
    // No-arg constructor required by Jackson
    public Employee() {}
    
    public Employee(String Username, String Access, String Password){
        setUsername(Username);
        setPassword(Password);
        setAccess(Access);

    }

    // get/set Username
    public String getUsername(){
        return this.Username;
    }
    public void setUsername(String Username){
        this.Username = Username;
    }
    // set permissions/access.
    public String getAccess(){
        return this.Access;
    }
    public void setAccess(String Access){
        this.Access = Access;
    }


    // get/set Password
    public String getPassword(){
        return this.Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }

    
}
