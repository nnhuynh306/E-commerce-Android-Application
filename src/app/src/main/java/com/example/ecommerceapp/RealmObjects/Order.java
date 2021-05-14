package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Order extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private Date createdDate;

    private String address;

    private String receiverName;

    private String state;

    private String phoneNumber;

    private Double totalPrice;

    private Account ownedAccount;

    @LinkingObjects("order")
    private final RealmResults<OrderDetail> orderDetails = null;

    public Order(Date createdDate, String address, String receiverName, String state, String phoneNumber, Double totalPrice, Account ownedAccount) {
        this.createdDate = createdDate;
        this.address = address;
        this.receiverName = receiverName;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.ownedAccount = ownedAccount;
    }

    public RealmResults<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public ObjectId getId() {
        return _id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getAddress() {
        return address;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getState() {
        return state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Account getOwnedUser() {
        return ownedAccount;
    }
}
