package com.example.ecommerceapp.RealmObjects;

public class OrderDetail {

    private Order order;

    private Product product;

    private float totalPrice;

    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, float totalPrice, int quantity) {
        this.order = order;
        this.product = product;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public void setTotalPrice(float totalPrice) {
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
