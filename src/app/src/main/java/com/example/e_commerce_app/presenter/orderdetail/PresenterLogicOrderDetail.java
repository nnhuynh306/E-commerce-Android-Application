package com.example.e_commerce_app.presenter.orderdetail;


import com.example.e_commerce_app.model.ModelDetail;
import com.example.e_commerce_app.model.ModelOrder;
import com.example.e_commerce_app.object.OrderDetail;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.view.orderdetail.IViewOrderDetail;

import java.util.List;

public class PresenterLogicOrderDetail implements IPresenterOrderDetail {
    IViewOrderDetail view;
    ModelOrder model;

    public PresenterLogicOrderDetail(IViewOrderDetail view) {
        this.view = view;
        model = new ModelOrder();
    }

    @Override
    public void orderDetails(String token, int id_order) {
        List<OrderDetail> list = model.orderDetails(token, id_order);
        int total = 0;
        for (OrderDetail orderDetail: list) {
            ModelDetail modelDetail = new ModelDetail();
            Product product = modelDetail.findById(orderDetail.getId_product());
            int price = Integer.valueOf(product.getPrice()) * orderDetail.getQuantity();
            total += price;
        }
        view.orderDetails(list, total);
    }
}
