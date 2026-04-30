// Daniel Munoz, William Brewster, Mikenzie Adkins.
// Graphics.LoginView version: 1.1
// Date Modified: 4/19/2026

package com.capstoneshipping.Graphics;

import com.capstoneshipping.DataBase.*;
import com.capstoneshipping.model.Employee;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
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

// Login Data Pulled from json to simulate database: database does not contain employee Tables.
// Login View Handles UI Layout and UI Elements for Login View.
public class LoginView extends VBox {

    // Stores Hashed Employee Credentials
    private ArrayList<Employee> Credentials = new ArrayList<>();
    
    private static DB_Connection database;
    
    // ViewController Handles Javafx Scene/layout changes.
    private ViewController viewController;
    
    public LoginView(ViewController controller) {
        // Elevate Retail Logo Background Image.
        Image bgImage = new Image(
            getClass().getClassLoader().getResourceAsStream("background.png")
        );
        
        BackgroundImage background = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                BackgroundSize.AUTO, 
                BackgroundSize.AUTO,
                false,
                false,
                true,
                true
                )
        );
        // setBackground(new Background(background));
        setAlignment(Pos.CENTER);
        
        // JavaFx ViewController
        this.viewController = controller;
        
        // Labels: title, subtitle, error: invalid login/login != "".
        Label titleLabel = new Label("Capstone Shipping");
        Label subtitleLabel = new Label("Employee sign in.");
        Label errorLabel = new Label();
        

        // Vertical box container that holds UI Elements for Login View.
        VBox form = new VBox(8);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(15));
        form.setMaxWidth(360);

        
        // Username field.
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(Double.MAX_VALUE);
        usernameField.setPrefHeight(40);
        
        
        // Password field.
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setPrefHeight(40);
        

        // Login Button.
        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(42);
        
        loginBtn.setOnAction(e -> {
            getCredentials();
            loginEmployee(usernameField, passwordField, errorLabel);
        });
        

        
        // Add style classes
        titleLabel.getStyleClass().add("title-label");
        subtitleLabel.getStyleClass().add("subtitle-label");
        errorLabel.getStyleClass().add("error-label");
        form.setId("login-form");

        // Add a separator between subtitle and fields
        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);

        // Add to form
        form.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            separator,
            usernameField,
            passwordField,
            loginBtn,
            errorLabel
        );
        getChildren().add(form);

    } // End of Constructor.
    

    // Handles Employee Login Logic, Initializes DB Connection and Switches to MainView Via Graphics.ViewController.showMainView() method.
    private void loginEmployee(TextField user, PasswordField pass, Label error) {
        
        // grab values
        String username = user.getText().trim();
        String password = pass.getText();
        
        // Validate not empty
        // if (username.isEmpty() || password.isEmpty()) {
        //     error.setText("Please fill in all fields.");
        //     return;
        // }
        
        // Validate credentials
        for (Employee employee : Credentials){

            // Validate Login, Pending Changes for hashed password validation.
            if (employee.getUsername().equals(username)) {
                // Authenticate/validate password
                if (employee.getPassword().equals(password)){

                    // If DB_Connection does not exist: (it shouldn't) assign new DB Connection.
                    if (database == null){
                        // Initialize database connection
                        database = new DB_Connection(); 
                    }
                    // Reset Text/Error fields.
                    user.setText("");
                    pass.setText("");
                    error.setText("");

                    // Switch to MainView.
                    this.viewController.showMainView();
                    return;
                }
            } 
            // Loop Termination signifies invalid user/password combination.
            error.setText("Invalid username or password.");
        }
    }

    // Retrieves Credentials from resources/credentials.json
    private void getCredentials(){
        
        // ObjectMapper maps Json values to appropriate class models.
        ObjectMapper mapper = new ObjectMapper();
        
        // Retrieve file.
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("credentials.json")) {
            if (in == null) {
                throw new FileNotFoundException("Credentials not found on classpath.");
            }
            // Maps data to class model.
            ArrayList<Employee> employees = mapper.readValue(in, new TypeReference<ArrayList<Employee>>() {});
            this.Credentials.addAll(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void createCredentials(String user, String access, String pass) {
        String hashed = BCrypt.hashpw(pass, BCrypt.gensalt()); // hash before storing
        
        ObjectMapper mapper = new ObjectMapper();
        
        // Read existing employees first
        List<Employee> employees = new ArrayList<>();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("credentials.json")) {
            if (in != null) {
                employees = mapper.readValue(in, new TypeReference<ArrayList<Employee>>() {});
                employees.add(new Employee(user, access, hashed));
                mapper.writerWithDefaultPrettyPrinter()
                      .writeValue(new File("src/main/resources/credentials.json"), employees);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
    