package com.example.ecommerceapp.RealmObjects;

public class Admin {
    Account account;

    public Admin(Account account) {
        this.account = account;
    }

    public Admin() {
    }

    public Account getUser() {
        return account;
    }
}
