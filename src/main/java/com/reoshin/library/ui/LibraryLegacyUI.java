package com.reoshin.library.ui;

import java.util.Scanner;

import com.reoshin.library.model.Library;
import com.reoshin.library.model.Member;

public class LibraryLegacyUI {
    private Library lib = new Library();
    private Scanner scanner;
    
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
        String input = null;
        scanner = new Scanner(System.in);


        displayMemu();
        input = scanner.next();
        input = input.toLowerCase();
        processCommand(input);
    }

    public void login() {
        
        System.out.println("Enter your ID");
        String id = scanner.next();
        Member myUser = null;
        try {
            myUser = lib.getMember(id);
        } catch (Exception e) {
            System.out.println("Login Failed!");
            System.out.println("Moving back to the main screen...");
            login();
            return;
        }
        if (myUser.getUserID().equals("00000")) {
            openAdminUI();
        } else {
            loginWithUser(myUser);
        }
    }

    public void loginWithUser(Member myUser) {
        System.out.println("Login Success.");
        System.out.println("Hello, " + myUser.getUserName());
    }

    public void openAdminUI() {
        System.out.println("Maintainerance Mode");
        System.out.println("");
    }

    public void displayMemu() {
        
    }

    public void processCommand(String input) {
        if (input.equals("l")) {
            login();
        } else if (input.equals("r")) {
            register();
        }
    }

    public void register() {

    }
}
