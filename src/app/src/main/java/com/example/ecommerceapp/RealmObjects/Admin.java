package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Admin  extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

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
