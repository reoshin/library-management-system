package com.reoshin.library.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.reoshin.library.exceptions.NoSuchItemException;


// main user's interface having bookshelf(s) and its user(s)
public class Library implements LoanSubject {
    private LocalDate today = LocalDate.now();
    private ArrayList<Member> members = new ArrayList<>();
    private BookShelf bookshelf = new BookShelf();
    private ArrayList<LoanObserver> observers = new ArrayList<>();
    private String location = "<Default Location>";


    // EFFECTS: register new member with given String name, then return new registered member.
    public Member newMember(String name) {
        Member newMember = new Member(members.size(), name);
        members.add(newMember);
        return newMember;
    }

    public LibraryItem searchByID(String ID) throws NoSuchItemException {
        return bookshelf.searchByID(ID);
    }

    public void addItem(LibraryItem item) {
        this.bookshelf.addItem(item);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public int getNumberOfMembers() {
        return members.size();
    }

    public String loanItem(LibraryItem item, Member m) {
        item.loanItem();
        
        notifyLoanObservers(item, m);
        return item.getTitle() + " by " + item.getAuthor();
    }

    @Override
    public void addObserver(LoanObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(LoanObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyLoanObservers(LibraryItem item, Member member) {
        for (LoanObserver observer : observers) {
            observer.onItemLoaned(item, member);
        }
    }

    public Member getMember(String id) throws NoSuchItemException {
        for (Member member : members) {
            if (member.getUserID().equals(id)) {
                return member;
            }
        }
        throw new NoSuchItemException();
    }
}
