package com.example.ecommerceapp.RealmObjects;

import io.realm.RealmObject;

public class CartDetail extends RealmObject {
    private Cart cart;

    private Product product;

    private int quantity;

    public CartDetail() {
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
}
