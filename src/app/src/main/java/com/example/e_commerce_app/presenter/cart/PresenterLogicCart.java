package com.example.e_commerce_app.presenter.cart;

import android.content.Context;

import com.example.e_commerce_app.model.ModelCart;

import com.example.e_commerce_app.object.Cart;
import com.example.e_commerce_app.object.OrderDetail;
import com.example.e_commerce_app.view.cart.IViewCart;

import java.util.List;

public class PresenterLogicCart implements IPresenterCart {
    IViewCart view;
    ModelCart model;

    public PresenterLogicCart(Context context, IViewCart view) {
        this.view = view;
        model = new ModelCart(context);
    }

    @Override
    public void save(Cart cart) {
        model.saveCart(cart);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        model.saveCart(orderDetail);
    }

    @Override
    public void removeIndex(int id_user, int id_product) {
        model.removeCart(id_user, id_product);
    }

    @Override
    public void carts(int id_user) {
        List<Cart> carts = model.carts(id_user);
        view.carts(carts);
    }

    @Override
    public void total(int id_user) {
        int total = 0;
        List<Cart> carts = model.carts(id_user);
        for (Cart cart: carts)
            total += cart.getTotal();
        view.total(total);
    }
}

