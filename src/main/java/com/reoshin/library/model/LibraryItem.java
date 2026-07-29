package com.reoshin.library.model;

import com.reoshin.library.exceptions.ItemNotAvailable;

public class LibraryItem implements Loanable {
    private String title;
    private String ID;
    private boolean available;

    // EFFECTS: set a title as title, ID as well and set the availability to True as default.
    public LibraryItem(String title, String ID) {
        this.available  = true;
        this.title = title;
        this.ID = ID;
    }

    // EFFECTS: change availability to true if it has loaned
    // REQUIRES: IsAvaliable() to be true
    public void returnItem() {
        
    }

    // EFFECTS: change availability to false if it is available,
    //          throws ItemNotAvailable if it is not available
    public void loanItem() throws ItemNotAvailable {

    }

    public String getTitle() {
        return null;
    }

    public String getID() {
        return null;
    }

    public boolean isAvailable() {
        return false;
    }
}
