package com.reoshin.library.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.reoshin.library.exceptions.NoSuchItemException;


// main user's interface having bookshelf(s) and its user(s)
public class Library implements LoanSubject {
    private LocalDate today = LocalDate.now();
    private ArrayList<Member> members = new ArrayList<>();
    private BookShelf bookshelf;
    private ArrayList<LoanObserver> observers;


    public void newMember(String name) {
        members.add(new Member(members.size(), name));
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void loanItem(LibraryItem item, Member m) throws Exception {
        item.loanItem();
        
        notifyLoanObservers(item, m);
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
