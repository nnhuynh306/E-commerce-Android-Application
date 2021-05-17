package com.example.ecommerceapp.RealmObjects;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Account extends RealmObject {
    @PrimaryKey
    private String _id;

    private String password;

    private String name;

    private Date dob;

    private String email;

    private String address;

    private String phoneNumber;

    @LinkingObjects("account")
    private final RealmResults<Cart> cart = null;

    @LinkingObjects("ownedAccount")
    private final RealmResults<Order> orders = null;

    public Account() {
    }

    public Account(String userName, String password, String name, Date dob, String email, String address, String phoneNumber) {
        this._id = userName;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Account(String userName, String password) {
        this._id = userName;
        this.password = password;
    }

    public RealmResults<Cart> getCart() {
        return cart;
    }

    public RealmResults<Order> getOrders() {
        return orders;
    }

    public String getUserName() {
        return _id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String get_id() {
        return this._id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
