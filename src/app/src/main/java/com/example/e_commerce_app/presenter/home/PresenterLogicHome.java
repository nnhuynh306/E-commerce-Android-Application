package com.example.e_commerce_app.presenter.home;


import android.content.Context;

import com.example.e_commerce_app.model.ModelCart;
import com.example.e_commerce_app.model.ModelHome;
import com.example.e_commerce_app.object.Banner;
import com.example.e_commerce_app.object.GroupProductType;
import com.example.e_commerce_app.view.home.IViewHome;

import java.util.List;

public class PresenterLogicHome implements IPresenterHome {
    IViewHome view;
    ModelHome model;

    public PresenterLogicHome(IViewHome view) {
        this.view = view;
        model = new ModelHome();
    }

    @Override
    public void loadBanners() {
        List<Banner> banners = model.banners();

        if (!banners.isEmpty())
            view.showBanners(banners);

    }

    @Override
    public void loadGroupProductTypes() {
        List<GroupProductType> groupProductTypes = model.groupProductTypes();

        if (!groupProductTypes.isEmpty())
            view.showGroupProductTypes(groupProductTypes);
    }

    @Override
    public void logout(String token) {
        int status = model.logout(token);
        view.logout(status);
    }

    @Override
    public void countCart(Context context, int id_user) {
        ModelCart modelCart = new ModelCart(context);
        view.countCart(modelCart.countCart(id_user));
    }
}
