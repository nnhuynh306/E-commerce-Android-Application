package com.example.e_commerce_app.presenter.detail.overview;

public interface IPresenterOverview {
    void isFavorite(String token,int id_product, int id_user);
    void doFavorite(String token,int id_product, int id_user);
}
