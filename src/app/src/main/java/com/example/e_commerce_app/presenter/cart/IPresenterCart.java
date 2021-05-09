package com.example.e_commerce_app.presenter.cart;

import com.example.e_commerce_app.object.Cart;
import com.example.e_commerce_app.object.OrderDetail;

public interface IPresenterCart {
    void save(Cart cart);
    void save(OrderDetail orderDetail);
    void removeIndex(int id_user, int id_product);
    void carts(int id_user);
    void total(int id_user);
}
