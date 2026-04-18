// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.LoginView version: 1.0
// Date Modified: 4/17/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.model.Employee;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import tools.jackson.databind.*;
import tools.jackson.core.type.TypeReference;

public class LoginView extends VBox {
    private ArrayList<Employee> Credentials = new ArrayList<>();
    private static DB_Connection database;
    private ViewController controller;

    public LoginView(ViewController controller) {

        this.controller = controller;
        setAlignment(Pos.CENTER);

        Image bgImage = new Image(getClass().getClassLoader().getResourceAsStream("Background.png"));
        
        BackgroundImage background = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        setBackground(new Background(background));
        setAlignment(Pos.CENTER);

        VBox form = new VBox(12);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(40));
        form.setMaxWidth(360);

        Label titleLabel = new Label("Capstone Shipping");

        Label subtitleLabel = new Label("Employee sign in.");
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(Double.MAX_VALUE);
        usernameField.setPrefHeight(40);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setPrefHeight(40);
        
        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(42);

        Label errorLabel = new Label();


        loginBtn.setOnAction(e -> {
            getCredentials();
            loginEmployee(usernameField, passwordField, errorLabel);
        });
        
        form.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            usernameField,
            passwordField,
            loginBtn,
            errorLabel
        );
        getChildren().add(form);

    } // End of Constructor
    
    private void loginEmployee(TextField user, PasswordField pass, Label error) {
        
        // grab values
        String username = user.getText().trim();
        String password = pass.getText();
        
        // validate not empty
        if (username.isEmpty() || password.isEmpty()) {
            error.setText("Please fill in all fields.");
            return;
        }
        
        // check credentials // change logic to a sep file
        for (Employee employee : Credentials){
            
            if (employee.getUsername().equals(username)) {
                if (employee.getPassword().equals(password)){
                    if (database == null){
                        database = new DB_Connection(); // Initialize database connection
                    }
                    
                    user.setText("");
                    pass.setText("");
                    error.setText("");

                    this.controller.showMainView();
                    return;
                }
            } 

            error.setText("Invalid username or password.");
        }
    }

    private void getCredentials(){
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("Credentials.json")) {
            if (in == null) {
                throw new FileNotFoundException("Credentials not found on classpath.");
            }

            ArrayList<Employee> employees = mapper.readValue(in, new TypeReference<ArrayList<Employee>>() {});
            this.Credentials.addAll(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}