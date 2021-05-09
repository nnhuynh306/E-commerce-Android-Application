package com.example.e_commerce_app.view.product;

import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.object.ProductType;

import java.util.List;

public interface IViewProduct {
    void menus(List<ProductType> lists);
    void products(List<Product> products);
    void emptyProduct();
}
