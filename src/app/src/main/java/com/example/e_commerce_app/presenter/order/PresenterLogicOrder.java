package com.example.e_commerce_app.presenter.order;


import com.example.e_commerce_app.model.ModelOrder;
import com.example.e_commerce_app.object.Order;
import com.example.e_commerce_app.view.order.IViewOrder;

import java.util.List;

public class PresenterLogicOrder implements IPresenterOrder{
    IViewOrder view;
    ModelOrder model;

    public PresenterLogicOrder(IViewOrder view) {
        this.view = view;
        model = new ModelOrder();
    }

    @Override
    public void orders(String token, int id_user) {
        List<Order> orders = model.orders(token, id_user);
        view.orders(orders);
    }
}
