package com.example.ecommerceapp.RealmObjects;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OrderDetail extends RealmObject {

    @PrimaryKey
    private ObjectId _id = new ObjectId();

    private Order order;

    private Product product;

    private Double totalPrice;

    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, Double totalPrice, int quantity) {
        this.order = order;
        this.product = product;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public ObjectId getId() {
        return _id;
    }
}
