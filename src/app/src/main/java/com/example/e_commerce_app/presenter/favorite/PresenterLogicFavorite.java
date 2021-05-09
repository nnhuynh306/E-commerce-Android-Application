package com.example.e_commerce_app.presenter.favorite;


import com.example.e_commerce_app.model.ModelFavorite;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.view.favorite.IViewFavorite;

import java.util.List;

public class PresenterLogicFavorite implements IPresenterFavorite {
    IViewFavorite view;
    ModelFavorite model;

    public PresenterLogicFavorite(IViewFavorite view) {
        this.view = view;
        model = new ModelFavorite();
    }

    @Override
    public void favorites(String token, int id_user) {
        List<Product> products = model.favorites(token, id_user);
        view.favorites(products);
    }
}
