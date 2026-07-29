package com.reoshin.library.model;

import com.reoshin.library.exceptions.ItemNotAvailable;

public class LibraryItem implements Loanable {
    private String title;
    private String ID;
    private boolean available;

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
