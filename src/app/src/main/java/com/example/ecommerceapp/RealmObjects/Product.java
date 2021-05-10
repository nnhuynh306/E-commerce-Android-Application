package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    @PrimaryKey
    private ObjectId id = new ObjectId();

    private String name;

    private int quantity;

    private float price;

    private String description;

    private RealmList<String> pictures;

    private ProductCategory category;

    public Product() {

    }

    public Product(String name, int quantity, float price, String description, RealmList<String> pictures, ProductCategory category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.pictures = pictures;
        this.category = category;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public RealmList<String> getPictures() {
        return pictures;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictures(RealmList<String> pictures) {
        this.pictures = pictures;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
