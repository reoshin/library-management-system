package com.reoshin.library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryItemTest {
    LibraryItem libItem1;
    LibraryItem libItem2;
    
    @BeforeEach
    public void setUp() {
        libItem1 = new LibraryItem("Book1", "1111", "Ben", Category.BIOGRAPHY);
    }

    @Test
    public void LibraryItemConstructorTest() {
        assertEquals("Book1", libItem1.getTitle());
        assertEquals("1111", libItem1.getID());
    }

    @Test
    public void LibraryItemLoanTest() {
        assertTrue(libItem1.isAvailable());
        try {
            libItem1.loanItem();
        } catch (Exception e) {
            fail("No exception expected");
        }
        assertFalse(libItem1.isAvailable());
    }

    @Test
    public void LibraryItemReturnTest() {
        try {
            libItem1.loanItem();
            assertFalse(libItem1.isAvailable());
            libItem1.returnItem();
            assertTrue(libItem1.isAvailable());
        } catch (Exception e) {
            fail("no exception expected");
        }

        try {
            libItem1.returnItem();
        } catch (Exception e) {
            // expected
        }
        assertTrue(libItem1.isAvailable());
    }
}
