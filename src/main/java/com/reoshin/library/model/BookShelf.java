package com.reoshin.library.model;

import java.util.ArrayList;
import java.util.HashMap;

public class BookShelf implements LoanObserver {
    private HashMap<String, LibraryItem> items;
    private ArrayList<LibraryItem> loanedItems;
    
    public void addNewItem(LibraryItem item) {
        // stub
    }

    public LibraryItem findItemByTitle(String title) {
        return null; // stub
    }

    public ArrayList<LibraryItem> findItemByCategory(Category Category) {
        return null; // stub
    }

    public ArrayList<LibraryItem> findItemByAuthor(String author) {
        return null; // stub
    }

    public LibraryItem findItemByID(String ID) {
        return null;
    }

    @Override
    public void onItemLoaned(LibraryItem item, Member member) {
        loanedItems.add(item);
    }
}
