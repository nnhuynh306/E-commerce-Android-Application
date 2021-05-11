package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CartDetail extends RealmObject {
    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private Cart cart;

    private Product product;

    private int quantity;

    public CartDetail() {
        quantity = 0;
    }

    public CartDetail(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ObjectId getId() {
        return _id;
    }
}
