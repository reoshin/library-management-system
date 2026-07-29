package com.reoshin.library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryItemTest {
    LibraryItem libItem1;
    LibraryItem libItem2;
    
    @BeforeEach
    public void setUp() {
        libItem1 = new LibraryItem("Book1", "1111");
    }

    @Test
    public void LibraryItemConstructorTest() {
        assertEquals("Book1", libItem1.getTitle());
        assertEquals("1111", libItem1.getID());
    }
}
