package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class ProductCategory extends RealmObject {
    @PrimaryKey
    private ObjectId id = new ObjectId();

    private String name;

    @LinkingObjects("category")
    private final RealmResults<Product> products = null;

    public ProductCategory() {

    }

    public ProductCategory(String name) {
        this.name = name;
    }

    public RealmResults<Product> getProducts() {
        return products;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
