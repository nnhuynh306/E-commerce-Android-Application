package com.example.ecommerceapp.RealmObjects;

public class Admin {
    User user;

    public Admin(User user) {
        this.user = user;
    }

    public Admin() {
    }

    public User getUser() {
        return user;
    }
}
