package com.reoshin.library.model;

import java.util.ArrayList;

public class Member implements LoanObserver {
    private String userID;
    private String userName;
    private ArrayList<LibraryItem> loanList;
    private Boolean activated;

    // EFFECTS: constructs user with given name and userNumber.
    public Member(int userNumber, String name) {
        this.userID = String.format("%05d", userNumber);
        this.loanList = new ArrayList<>();
        this.userName = name;
        this.activated = true;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public Boolean isActivated() {
        return this.activated;
    }

    @Override
    public void onItemLoaned(LibraryItem item, Member member) {
        loanList.add(item);
    }

    
}
