package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Cart extends RealmObject {
    @PrimaryKey
    private ObjectId id = new ObjectId();

    private User user;

    @LinkingObjects("cart")
    private final RealmResults<CartDetail> cartDetails = null;

    public Cart(User user) {
        this.user = user;
    }

    public Cart() {
    }

    public RealmResults<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public Object getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
