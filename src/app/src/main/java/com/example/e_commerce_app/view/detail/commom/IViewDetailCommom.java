package com.example.e_commerce_app.view.detail.commom;

import com.example.e_commerce_app.object.Product;

public interface IViewDetailCommom {
    void fillData(Product product);
    void showError(String message);
}
