package com.example.e_commerce_app.presenter.product;


import android.widget.ProgressBar;

import com.example.e_commerce_app.object.Product;

import java.util.List;

public interface IPresenterProduct {
    void menus(int id_group);
    void products(int id_product_type);
    List<Product> loadMoreProducts(int id_product_type, int sumItem, ProgressBar progress);
}
