package com.reoshin.library.ui;

import com.reoshin.library.exceptions.ItemNotAvailable;
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
        updateUI(Mode.HOME);

        Scene scene = new Scene(root, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public void updateUI(Mode mode) {
        root.getChildren().clear();

        if (mode == Mode.HOME) {
            openHomeUI();
        } else if (mode == Mode.LOGIN) {
            openLoginUI();
        } else if (mode == Mode.ADMIN) {
            openAdminUI();
        } else if (mode == Mode.LOAN) {
            openLoanUI();
        } else if (mode == Mode.REGISTER) {
            openRegisterUI();
        } else if (mode == Mode.RETURN) {
           openReturnUI();
        } else {
            openErrorUI();
        }
    }

    public Boolean login(String userID) {
        try {
            myUser = lib.getMember(userID);
            updateUI(Mode.HOME);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openAdminUI() {
        Label label = new Label("Library Management System");
        Label welcome = new Label("Admin Mode");

        Label welcome2 = new Label(
            "Your current location is " + lib.getLocation()
        );

        Button button1 = new Button("Add Book(s)");
        Button button2 = new Button("Manage My Member(s)");
        Button button3 = new Button("Change my Location");


        Button homeButton = new Button("Home");

        homeButton.setOnAction(event -> {
            this.myUser = null;
            updateUI(Mode.HOME);
        });


        root.getChildren().addAll(
            label,
            welcome,
            welcome2,
            button1,
            button2,
            button3,
            homeButton
        );

        root.setAlignment(Pos.CENTER);
    }

    public void openLoginUI() {
        Label title = new Label("Member Login");
        Label instruction = new Label(
            "Scan or enter Member ID\n💡Tip - You can manually enter your Member ID");

        TextField idField = new TextField();
        idField.setPromptText("Member ID");

        Button loginButton = new Button("Login");
        HBox barcodeBox = new HBox(5, idField, loginButton);
        Label scannerLabel = new Label("Scanner Status: ✅Ready to Scan");

        VBox scannerBox = new VBox(barcodeBox, scannerLabel);
        VBox loginBox = new VBox(7, title, instruction, scannerBox);


        Label registerMessage = new Label("Not a member?");
        Button registerButton = new Button("➡️ Getting Started (🆕 Register)");
        VBox registerBox = new VBox(registerMessage, registerButton);

        VBox loginUI = new VBox(30, loginBox, registerBox);


        loginButton.setOnAction(event -> {
            String userID = idField.getText();

            if (!login(userID)) {
                scannerLabel.setText("🚫 Member ID not found.");
            }
        });

        registerButton.setOnAction(event -> {
            updateUI(Mode.REGISTER);
        });


        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> {
            updateUI(Mode.HOME);
        });


        root.getChildren().addAll(loginUI, homeButton);

        root.setAlignment(Pos.CENTER);

    }

    public void openRegisterUI() {
        Label label = new Label("Register");
        Label newID = new Label(
            "Your new member ID:  " 
            + String.format("%05d", lib.getNumberOfMembers())
        );

        Label newID2 = new Label(
            "Notice: ID is assigned by system and cannot be changed."
        );

        Label label2 = new Label("Please Enter Your Name");

        TextField nameField = new TextField();
        nameField.setPromptText("Your First name and Last name");


        Button submitButton = new Button("Submit");

        Label errorLabel = new Label();

        submitButton.setOnAction(event -> {
            String inputName = nameField.getText();

            if (inputName.equals("Name") || inputName.equals("")) {
                errorLabel.setText(
                    "Your name should include at least one letter (a-Z)."
                );
            } else {
                this.myUser = lib.newMember(inputName);
                updateUI(Mode.HOME);
            }
        });


        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> {
            updateUI(Mode.HOME);
        });


        root.getChildren().addAll(
            label,
            newID,
            newID2,
            label2,
            nameField,
            submitButton,
            errorLabel,
            homeButton
        );

        root.setAlignment(Pos.CENTER);
    }

    public String getUserName() {
        if (myUser == null) {
            return "Guest";
        } else {
            return myUser.getUserName() + "(ID: " + myUser.getUserID() + ")";
        }
    }

    public void openHomeUI() {
        Label title = new Label("Library System");
        Label location = new Label(lib.getLocation());

        VBox headerBox = new VBox(title, location);

        Label profileName = new Label("Hello, " + getUserName());
        Button loginButtonGuest = new Button("⚠️ Login/Register");

        VBox guestProfileBox = new VBox(profileName, loginButtonGuest);
        Button profileButton = new Button("👤 View my profile");

        Button logoutButton = new Button("⚠️ Logout");
        VBox profileBox = new VBox(profileName, profileButton, logoutButton);

        Button loanButton = new Button("📖 Loan");
        Button returnButton = new Button("↩ Return");
        HBox buttonGroup1 = new HBox(10, loanButton, returnButton);


        Button searchButton = new Button("🔍 Item Search");
        Button activityButton = new Button("📣 Recent Activity");

        HBox buttonGroup2 = new HBox(10, searchButton, activityButton);

        VBox actionBox = new VBox(3, buttonGroup1, buttonGroup2);
        loanButton.setOnAction(event -> {
            updateUI(Mode.LOAN);
        });

        loginButtonGuest.setOnAction(event -> {
            updateUI(Mode.LOGIN);
        });

        returnButton.setOnAction(event -> {
            updateUI(Mode.RETURN);
        });

        searchButton.setOnAction(event -> {
            updateUI(Mode.SEARCH);
        });

        activityButton.setOnAction(event -> {
            updateUI(Mode.ACTIVITY);
        });

        profileButton.setOnAction(event -> {
            updateUI(Mode.PROFILE);
        });


        logoutButton.setOnAction(event -> {
            this.myUser = null;
            updateUI(Mode.HOME);
        });

        if (myUser == null) {
            root.getChildren().addAll(headerBox, guestProfileBox); // Guest
        } else {
            root.getChildren().addAll(headerBox, profileBox, actionBox); // Member
        }
        root.setAlignment(Pos.CENTER);
    }

    public void openLoanUI() {
        Label label = new Label("Loan Book/Video Tape(s)");
        Label instruction = new Label(
            "Please scan or manually type the barcode"
        );

        Label instruction2 = new Label(
            "Tip: If the scanner doesn't work, try to type 8-digit numbers"
        );

        TextField barcodeScanner = new TextField();
        barcodeScanner.setPromptText(
            "Barcode number is usually placed on the back of the book."
        );

        Label scannerLabel = new Label(
            "Scanner Status: Ready to scan"
        );

        Button readButton = new Button("Read");

        VBox barcodeScannerBox = new VBox(
            barcodeScanner,
            scannerLabel
        );

        HBox scannerBox = new HBox(
            barcodeScannerBox,
            readButton
        );

        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> {
            updateUI(Mode.HOME);
        });


        readButton.setOnAction(event -> {
            String barcode = barcodeScanner.getText();

            try {
                instruction.setText(
                    "Your Item: "
                    + lib.loanItem(
                        lib.searchByID(barcode),
                        myUser
                    )
                );

                label.setText("Loan Item Confirmation");

                instruction2.setText(
                    "Thanks for being our valueable member, "
                    + getUserName()
                );

                scannerLabel.setText("Scanner Status: Idle");

                root.getChildren().remove(scannerBox);

            } catch (NoSuchItemException e) {

                scannerLabel.setText(
                    "Scanner Status: Item not found. Please ask administar."
                );

                PauseTransition delay =
                    new PauseTransition(Duration.seconds(3));

                delay.setOnFinished(event2 ->
                    scannerLabel.setText(
                        "Scanner Status: Ready to Scan"
                    )
                );

                delay.play();

            } catch (ItemNotAvailable e) {

                scannerLabel.setText(
                    "Scanner Status: Sorry, this item is not available."
                );

                PauseTransition delay =
                    new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(event3 ->
                    scannerLabel.setText(
                        "Scanner Status: Ready to Scan"
                    )
                );
                delay.play();
            }
        });


        root.getChildren().addAll(
            label,
            instruction,
            instruction2,
            scannerBox,
            homeButton
        );

        root.setAlignment(Pos.CENTER);
    }

    public void openReturnUI() {
        Label heading = new Label("Return");
        Label instruction = new Label(
            "Please scan or manually type the barcode"
        );

        Label instruction2 = new Label(
            "Tip: If the scanner doesn't work, try to type 8-digit numbers"
        );

        TextField barcodeScanner = new TextField();
        barcodeScanner.setPromptText(
            "Barcode number is usually placed on the back of the book."
        );

        Label scannerLabel = new Label(
            "Scanner Status: Ready to scan"
        );

        Button readButton = new Button("Read");
        VBox barcodeScannerBox = new VBox(
            barcodeScanner,
            scannerLabel
        );

        HBox scannerBox = new HBox(
            barcodeScannerBox,
            readButton
        );

        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> {
            updateUI(Mode.HOME);
        });


        readButton.setOnAction(event -> {
            String barcode = barcodeScanner.getText();

            try {
                instruction.setText(
                    "Your Item: "
                    + lib.returnItem(
                        lib.searchByID(barcode),
                        myUser
                    )
                );

                heading.setText("Return Item Confirmation");

                instruction2.setText(
                    "Thanks for being our valueable member, "
                    + getUserName()
                );

                scannerLabel.setText("Scanner Status: Idle");

                root.getChildren().remove(scannerBox);

            } catch (NoSuchItemException e) {

                scannerLabel.setText(
                    "Scanner Status: Item not found. Please ask administar."
                );

                PauseTransition delay =
                    new PauseTransition(Duration.seconds(3));

                delay.setOnFinished(event2 ->
                    scannerLabel.setText(
                        "Scanner Status: Ready to Scan"
                    )
                );

                delay.play();

            } catch (ItemNotAvailable e) {

                scannerLabel.setText(
                    "Scanner Status: This item is available to loan."
                );

                PauseTransition delay =
                    new PauseTransition(Duration.seconds(3));

                delay.setOnFinished(event3 ->
                    scannerLabel.setText(
                        "Scanner Status: Ready to Scan"
                    )
                );

                delay.play();
            }
        });


        root.getChildren().addAll(
            heading,
            instruction,
            instruction2,
            scannerBox,
            homeButton
        );

        root.setAlignment(Pos.CENTER);
    }

    public void openErrorUI() {
        Label icon = new Label("⚠️");
        Label text = new Label(
            "Error: the page you're trying to reach is not found"
        );

        Label errorCode = new Label("(Code: 0X0X0X)");


        Button homeButton = new Button("Home");
        homeButton.setOnAction(event -> {
            updateUI(Mode.HOME);
        });


        root.getChildren().addAll(
            icon,
            text,
            errorCode,
            homeButton
        );

        root.setAlignment(Pos.CENTER);
    }


}