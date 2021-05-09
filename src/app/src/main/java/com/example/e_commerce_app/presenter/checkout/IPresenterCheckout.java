package com.example.e_commerce_app.presenter.checkout;

public interface IPresenterCheckout {
    void checkout(String token, int id_user, String status_order, String phone, String delivery_address, String order_date, String desc);
}
