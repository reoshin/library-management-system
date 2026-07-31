package com.reoshin.library.model;

import com.reoshin.library.exceptions.ItemNotAvailable;

public class LibraryItem implements Loanable {
    private String title;
    private String ID;
    private boolean available;
    private String author;
    private Category category;

    // EFFECTS: set a title as title, ID as well and set the availability to True as default.
    public LibraryItem(String title, String ID, String author, Category category) {
        this.available  = true;
        this.title = title;
        this.ID = ID;
        this.author = author;
        this.category = category;
    }

    // EFFECTS: change availability to true if it has loaned,
    //          throws ItemNotAvailable if it isAvailable is true.
    public void returnItem() throws ItemNotAvailable {
        if (this.available == true) {
            throw new ItemNotAvailable();
        }
        this.available = true;
    }

    // EFFECTS: change availability to false if it is available,
    //          throws ItemNotAvailable if it is not available
    public void loanItem() throws ItemNotAvailable {
        if (this.available == false) {
            throw new ItemNotAvailable();
        }
        this.available = false;
    }

    public String getTitle() {
        return this.title;
    }

    public String getID() {
        return this.ID;
    }
    
    public String getAuthor() {
        return this.author;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
