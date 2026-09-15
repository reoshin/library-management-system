package com.reoshin.library.model;

public interface LoanObserver {
    public void onItemLoaned(LibraryItem item, Member member);

    public void onItemReturned(LibraryItem item, Member member);
}
