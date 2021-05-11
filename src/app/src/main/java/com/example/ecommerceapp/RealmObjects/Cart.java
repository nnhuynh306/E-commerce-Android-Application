package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Cart extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private Account account;

    @LinkingObjects("cart")
    private final RealmResults<CartDetail> cartDetails = null;

    public Cart(Account account) {
        this.account = account;
    }

    public Cart() {
    }

    public RealmResults<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public Object getId() {
        return _id;
    }

    public Account getUser() {
        return account;
    }
}
