package com.reoshin.library.ui;

import com.reoshin.library.model.Library;
import com.reoshin.library.model.Member;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryApplication extends Application {
    private Library lib = new Library();
    private VBox root = new VBox();
    private String location = "<Default Location>";

    private Member myUser = null;

    public LibraryApplication() {
        lib.newMember("Admin"); // 00000
        testEnvironment();

    }

    // This is only for Developer, to test whether the UI works fine or not.
    public void testEnvironment() {
        this.location = "West Vancouver Public Library";
        lib.newMember("John"); // 00001
        lib.newMember("Reo"); // 00002
        lib.newMember("Steve"); // 00003

        

        System.out.println("Test Environment has been successully set-up");
    }




    @Override
    public void start(Stage stage) {
        updateUI();

        Scene scene = new Scene(root, 400, 850);

        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public void updateUI() {
        root.getChildren().clear();

        if (myUser == null) {
            openGuestUI();
        } else if (myUser.getUserID().equals("00000")) {
            openAdminUI();
        } else {
            openMemberUI();
        }
    }

    public Boolean login(String userID) {
        try {
            myUser = lib.getMember(userID);
            updateUI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openAdminUI() {
        Label label = new Label("Library Management System");

        Label welcome = new Label("Admin Mode");
        Label welcome2 = new Label("Your current location is " + this.location);
        Button button1 = new Button("Add Book(s)");
        Button button2 = new Button("Manage My Member(s)");
        Button button3 = new Button("Change my Location");


        root.getChildren().addAll(label, welcome,welcome2, button1, button2, button3);
        root.setAlignment(Pos.CENTER);
    }

    public void openGuestUI() {
        Label label = new Label("Member Login");
        Label loginMessage = new Label();

        TextField idField = new TextField();
        idField.setPromptText("Enter Member ID");

        

        Button loginButton = new Button("Login");

        Button registerButton = new Button("Getting Started (New Member)");
        Label registerMessage = new Label("Not a member? Register Today.");

        loginButton.setOnAction(event -> {
            String userID = idField.getText();
            if (!login(userID)) {
                loginMessage.setText("Member ID not found.");
            }
        });

        registerButton.setOnAction(event -> {
            openRegisterUI();
        });

        this.root = new VBox(10, label, idField, loginButton, loginMessage, registerMessage, registerButton);

        this.root.setAlignment(Pos.CENTER);
    }

    public void openRegisterUI() {
        root.getChildren().clear();
        Label label = new Label("Register");
        Label label2 = new Label("Please Enter Your Name");

        Button submitButton = new Button("Submit");
        TextField nameField = new TextField();
        nameField.setPromptText("Your First name and Last name");
        Label errorLabel = new Label();

        submitButton.setOnAction(event -> {
            String inputName = nameField.getText();
            if (inputName.equals("Name") || inputName.equals("")) {
                errorLabel.setText("Your name should include at least one letter (a-Z).");
            } else {
                this.myUser = lib.newMember(inputName);
                updateUI();
            }
        });
        
        root.getChildren().addAll(label, label2, nameField, submitButton, errorLabel);
        root.setAlignment(Pos.CENTER);
    }

    public void openMemberUI() {
        Label label = new Label("Library System");

        Label welcome = new Label("Hello, " + myUser.getUserName());

        Button button1 = new Button("Edit my profile");
        Button button2 = new Button("Loan");
        Button button3 = new Button("Return");

        Label search = new Label("Item Search");

        Button button4 = new Button("Genre");
        Button button5 = new Button("Title");
        Button button6 = new Button("Author");


        root.getChildren().addAll(label, welcome, button1, button2, button3, search, button4, button5, button6);
        root.setAlignment(Pos.CENTER);
    }
       
}