package com.reoshin.library.model;

public interface LoanSubject {
    public void addObserver(LoanObserver o);
    public void removeObserver(LoanObserver o);
    public void notifyLoanObservers(LibraryItem item, Member member);
    public void notifyReturnObservers(LibraryItem item, Member member);
}
