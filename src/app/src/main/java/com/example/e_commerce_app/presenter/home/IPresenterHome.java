package com.example.e_commerce_app.presenter.home;

import android.content.Context;

public interface IPresenterHome {
    void loadBanners();
    void loadGroupProductTypes();
    void logout(String token);
    void countCart(Context context, int id_user);
}
