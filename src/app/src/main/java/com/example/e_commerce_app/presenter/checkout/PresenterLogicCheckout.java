package com.example.e_commerce_app.presenter.checkout;

import android.content.Context;

import com.example.e_commerce_app.helper.DatabaseHelper;
import com.example.e_commerce_app.model.ModelCheckout;
import com.example.e_commerce_app.object.OrderDetail;
import com.example.e_commerce_app.view.checkout.IViewCheckout;

import java.util.List;

public class PresenterLogicCheckout implements IPresenterCheckout{
    IViewCheckout view;
    ModelCheckout model;
    Context context;

    public PresenterLogicCheckout(IViewCheckout view, Context context) {
        this.view = view;
        this.context = context;
        model = new ModelCheckout();
    }

    @Override
    public void checkout(String token, int id_user, String status_order, String phone, String delivery_address, String order_date, String desc) {
        int id_order = model.request(token, id_user, status_order, phone, delivery_address, order_date, desc);
        if (id_order != 0) {
            List<OrderDetail> orders = new DatabaseHelper(context).allCart(id_user);
            for (OrderDetail order: orders) {
                int status = model.store(token, id_order, order.getId_product(), order.getQuantity());
                if (status != 200) break;
            }
            view.checkout(200);
        } else {
            view.error("Unauthorized");
        }
    }
}
