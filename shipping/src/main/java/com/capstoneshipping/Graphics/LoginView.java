package com.capstoneshipping.Graphics;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

// LoginView.java
public class LoginView extends VBox {
    private Map<String, String> Credentials;


    public LoginView() {
        setAlignment(Pos.CENTER);

        Credentials = new HashMap<>(){{
            put("admin", "admin4321");
            put("daniel", "munoz2026");
            put("william", "brewster2026");
            put("mikenzie", "adkins2026");
            put("naye", "Hairston2026");

        }};


        VBox form = new VBox(12);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(40));
        form.setPrefWidth(400);
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setMaxHeight(40);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setMaxHeight(40);
        
        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(300);
        loginBtn.setMaxHeight(40);
        Label errorLabel = new Label();

        loginBtn.setOnAction(e -> handleLogin(usernameField, passwordField, errorLabel));

        getChildren().addAll(usernameField, passwordField, loginBtn, errorLabel);
    }

    private void handleLogin(TextField user, PasswordField pass, Label error) {

        // grab values
        String username = user.getText().trim();
        String password = pass.getText();

        // validate not empty
        if (username.isEmpty() || password.isEmpty()) {
            error.setText("Please fill in all fields.");
            return;
        }

        // check credentials // change logic to a sep file
        if (Credentials.containsKey(username)) {
            if (Credentials.get(username).equals(password)){
                
                // get the root BorderPane from the scene
                BorderPane root = (BorderPane) getScene().getRoot();

                // swap center to MainView
                root.setCenter(new MainView());
            }
        } else {
            error.setText("Invalid username or password.");
        }
    }
}