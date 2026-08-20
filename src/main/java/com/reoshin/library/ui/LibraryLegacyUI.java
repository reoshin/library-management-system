package com.reoshin.library.ui;

import java.util.Scanner;

import com.reoshin.library.model.Library;
import com.reoshin.library.model.Member;

public class LibraryLegacyUI {
    private Library lib = new Library();
    private Scanner scanner;
    private Member myUser = null;
    
    public LibraryLegacyUI() {
        lib.newMember("Admin"); // 00000
        testEnvironment();
        runApp();
    }

    // This is only for Developer, to test whether the UI works fine or not.
    public void testEnvironment() {
        lib.newMember("John"); // 00001
        lib.newMember("Reo"); // 00002
        lib.newMember("Steve"); // 00003

        

        System.out.println("Test Environment has been successully set-up");
    }

    public void runApp() {
        Boolean keepGoing = true;
        String input = null;
        scanner = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            input = scanner.next();
            input = input.toLowerCase();
            processCommand(input);
        }
    }

    public void login() {
        System.out.println("Enter your ID");
        String id = scanner.next();
        try {
            myUser = lib.getMember(id);
        } catch (Exception e) {
            System.out.println("Login failed.");
            System.out.println("Please Check your ID again!");
            login();
            return;
        }
        if (myUser.getUserID().equals("00000")) {
            myUser = null;
            adminLogin();
        }
    }


    // Developer's setting.
    public void openAdminUI() {
        System.out.println("'a' - add new item to library");
        System.out.println("'x' - add new item to library");
    }

    public void openGuestUI() {
        System.out.println("Hello, Guest");
        System.out.println("Enter 'l' to Login");
        System.out.println("Not a member? Type 'r' to get started!");
    }

    public void openMemberUI() {
        System.out.println("Hello, " + myUser.getUserName() + "(ID: " + myUser.getUserID() + ")");
    }

    public void adminLogin() {
        System.out.println("Maintenance Mode");
        System.out.println("Type 'q' if you didn't mean to login as admin.");
        String passcode = "";
        while (! passcode.equals("1234")) {
            System.out.println("Passcode: ");
            passcode = scanner.next();
            if (passcode.equals("q")) {
                System.out.println("Login failed.");
                return;
            }
            System.out.println("Try again.");
        }
        myUser = lib.getMember("00000");
    }

    public void displayMenu() {
        if (myUser == null) {
            openGuestUI();
        } else if (myUser.getUserID().equals("00000")) {
            openAdminUI();
        } else {
            openMemberUI();
        }
    }

    public void processCommand(String input) {
        if (input.equals("l")) {
            login();
        } else if (input.equals("r")) {
            register();
        }
    }

    public void register() {
        System.out.println("Registration");
        System.out.println("Please Enter your Name");
        String userName = scanner.next();
        myUser = lib.newMember(userName);
        System.out.println("Registration Registration.");
        System.out.println("Welcome, " + myUser.getUserName());
        System.out.println("Your new ID: "+ myUser.getUserID());
    }
}
