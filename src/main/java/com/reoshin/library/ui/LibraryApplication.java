package com.reoshin.library.ui;

import com.reoshin.library.exceptions.NoSuchItemException;
import com.reoshin.library.model.Book;
import com.reoshin.library.model.Category;
import com.reoshin.library.model.Library;
import com.reoshin.library.model.Member;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LibraryApplication extends Application {
    private Library lib = new Library();
    private VBox root = new VBox();

    private Member myUser = null;

    public LibraryApplication() {
        lib.newMember("Admin"); // 00000
        testEnvironment();

    }

    // This is only for Developer, to test whether the UI works fine or not.
    public void testEnvironment() {
        lib.setLocation("West Vancouver Public Library");
        lib.newMember("John Lee"); // 00001
        lib.newMember("Reo Steven"); // 00002
        lib.newMember("Steve Jobs"); // 00003

        Book book1 = new Book("The Little Prince", "01010101", "Antoine de Saint-Exupéry", Category.FANTASY);
        Book book2 = new Book("A Tale Of Two Cities", "02020202", "Charles Dickens", Category.HISTORY);
        Book book3 = new Book("Harry Potter and the Sorcerer's Stone", "03030303", " J.K. Rowling", Category.FICTION);
        
        lib.addItem(book1);
        lib.addItem(book2);
        lib.addItem(book3);

        System.out.println("Test Environment has been successully set-up");
    }




    @Override
    public void start(Stage stage) {
        updateUI(Mode.GUEST);

        Scene scene = new Scene(root, 400, 850);

        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public void updateUI(Mode mode) {
        root.getChildren().clear();

        if (mode == Mode.GUEST) {
            openGuestUI();
        } else if (mode == Mode.MEMBER) {
            openMemberUI();
        } else if (mode == Mode.ADMIN) {
            openAdminUI();
        } else if (mode == Mode.LOAN) {
            openLoanUI();
        } else if (mode == Mode.REGISTER) {
            openRegisterUI();
        }
    }

    public Boolean login(String userID) {
        try {
            myUser = lib.getMember(userID);
            updateUI(Mode.MEMBER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openAdminUI() {
        Label label = new Label("Library Management System");

        Label welcome = new Label("Admin Mode");
        Label welcome2 = new Label("Your current location is " + lib.getLocation());
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
        idField.setPromptText("Scan or enter Member ID");

        

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
            updateUI(Mode.REGISTER);
        });

        this.root = new VBox(10, label, idField, loginButton, loginMessage, registerMessage, registerButton);

        this.root.setAlignment(Pos.CENTER);
    }

    public void openRegisterUI() {
        root.getChildren().clear();
        Label label = new Label("Register");
        Label newID = new Label("Your new member ID:  " + String.format("%05d", lib.getNumberOfMembers()));
        Label newID2 = new Label("Notice: ID is assigned by system and cannot be changed.");
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
                updateUI(Mode.MEMBER);
            }
        });

        root.getChildren().addAll(label, newID, newID2, label2, nameField, submitButton, errorLabel);
        root.setAlignment(Pos.CENTER);
    }

    public void openMemberUI() {
        Label label = new Label("Library System");

        Label welcome = new Label("Hello, " + myUser.getUserName() + "(ID: " + myUser.getUserID() + ")");

        Button editButton = new Button("Edit my profile");
        Button loanButton = new Button("Loan");
        Button returnButton = new Button("Return");

        Label search = new Label("Item Search");

        Button genreButton = new Button("Genre");
        Button titleButton = new Button("Title");
        Button authorButton = new Button("Author");

        loanButton.setOnAction(event -> {
            updateUI(Mode.LOAN);
        });

        root.getChildren().addAll(label, welcome, editButton, loanButton, returnButton, search, genreButton, titleButton, authorButton);
        root.setAlignment(Pos.CENTER);
    }

    public void openLoanUI() {
        Label label = new Label("Loan Book/Video Tape(s)");
        Label instruction = new Label("Please scan or manually type the barcode");
        Label instruction2 = new Label("Tip: If the scanner doesn't work, try to type 8-digit numbers");
        TextField barcodeScanner = new TextField();
        barcodeScanner.setPromptText("Barcode number is usually placed on the back of the book.");
        Label scannerLabel = new Label("Scanner Status: Ready to scan");
        Button readButton = new Button("Read");
        VBox barcodeScannerBox = new VBox(barcodeScanner, scannerLabel);

        HBox scannerBox = new HBox(barcodeScannerBox, readButton); // all
        Button closeButton = new Button("Home");

        closeButton.setOnAction(event -> {
            updateUI(Mode.MEMBER);
        });

        readButton.setOnAction(event -> {
            String barcode = barcodeScanner.getText();
            try {
                instruction.setText("Your Item: " + lib.loanItem(lib.searchByID(barcode), myUser));
                label.setText("Loan Item Confirmation");
                instruction2.setText("Thanks for being our valueable member, " + myUser.getUserName() + "(ID: " + myUser.getUserID() + ")");
                scannerLabel.setText("Scanner Status: Idle");
                root.getChildren().add(closeButton);
                root.getChildren().remove(scannerBox);
            } catch (NoSuchItemException e) {
                scannerLabel.setText("Scanner Status: Item not found. To loan this item, ask administar to register to the system.");
                
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event2 -> scannerLabel.setText("Scanner Status: Ready to Scan"));
                delay.play();
            }
        });


        root.getChildren().addAll(label, instruction, instruction2, scannerBox);
        root.setAlignment(Pos.CENTER);
    }
}