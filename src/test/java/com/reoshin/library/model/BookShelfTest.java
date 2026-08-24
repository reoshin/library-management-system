package com.reoshin.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.reoshin.library.exceptions.NoSuchItemException;

public class BookShelfTest {
    BookShelf bookshelf;
    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    public void setUp() {
        bookshelf = new BookShelf();
        book1 = new Book("The Little Prince", "01010101", "Antoine de Saint-Exupéry", Category.FANTASY);
        book2 = new Book("A Tale Of Two Cities", "02020202", "Charles Dickens", Category.HISTORY);
        book3 = new Book("Harry Potter and the Sorcerer's Stone", "03030303", " J.K. Rowling", Category.FICTION);

        bookshelf.addItem(book1);
        bookshelf.addItem(book2);
        bookshelf.addItem(book3);
    }
    
    @Test
    public void searchByID() {
        try {
            LibraryItem result = bookshelf.searchByID("01010101");
            assertEquals(result, book1);
            result = bookshelf.searchByID("02020202");
            assertEquals(result, book2);
            result = bookshelf.searchByID("03030303");
            assertEquals(result, book3);

            bookshelf.searchByID("0");
        } catch (NoSuchItemException e) {
            System.out.println("OK");
        }
    }
}
