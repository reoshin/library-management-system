package com.reoshin.library.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.reoshin.library.exceptions.NoSuchItemException;

public class BookShelf implements LoanObserver {
    private HashMap<Category, LibraryItem> items = new HashMap<>();
    private ArrayList<LibraryItem> loanedItems;
    

    // EFFECTS: add item into BookShelf, sorted by category
    public void addItem(LibraryItem item) {
        items.put(item.getCategory(), item);
    }

    public LibraryItem findItemByTitle(String title) {
        return null; // stub
    }

    public ArrayList<LibraryItem> searchByCategory(Category Category) {
        return null; // stub
    }

    public ArrayList<LibraryItem> searchByAuthor(String author) {
        return null; // stub
    }

    public LibraryItem searchByID(String ID) throws NoSuchItemException {
        ArrayList<LibraryItem> allItems = new ArrayList<LibraryItem>(items.values());
        for (LibraryItem item : allItems) {
            if (item.getID().equals(ID)) {
                return item;
            }
        }
        throw new NoSuchItemException();
    }

    @Override
    public void onItemLoaned(LibraryItem item, Member member) {
        loanedItems.add(item);
    }

    @Override
    public void onItemReturned(LibraryItem item, Member member) {
        if (loanedItems.contains(item)) {
            loanedItems.remove(item);
        }
    }
}
