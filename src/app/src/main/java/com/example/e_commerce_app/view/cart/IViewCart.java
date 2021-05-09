package com.example.e_commerce_app.view.cart;

import com.example.e_commerce_app.object.Cart;

import java.util.List;

public interface IViewCart {
    void carts(List<Cart> carts);
    void total(int total);
    void error(String msg);
}
