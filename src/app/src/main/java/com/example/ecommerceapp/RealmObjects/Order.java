package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Order extends RealmObject {
    @PrimaryKey
    private ObjectId id = new ObjectId();

    private Date createdDate;

    private String address;

    private String receiverName;

    private String state;

    private String phoneNumber;

    private float totalPrice;

    private User ownedUser;

    @LinkingObjects("order")
    private final RealmResults<OrderDetail> orderDetails = null;

    public Order(ObjectId id, Date createdDate, String address, String receiverName, String state, String phoneNumber, float totalPrice, User ownedUser) {
        this.id = id;
        this.createdDate = createdDate;
        this.address = address;
        this.receiverName = receiverName;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.ownedUser = ownedUser;
    }

    public RealmResults<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public ObjectId getId() {
        return id;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public User getOwnedUser() {
        return ownedUser;
    }
}
