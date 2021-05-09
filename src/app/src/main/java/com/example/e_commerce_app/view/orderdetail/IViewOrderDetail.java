package com.example.e_commerce_app.view.orderdetail;

import com.example.e_commerce_app.object.OrderDetail;

import java.util.List;

public interface IViewOrderDetail {
    void orderDetails(List<OrderDetail> orderDetails, int total);
}