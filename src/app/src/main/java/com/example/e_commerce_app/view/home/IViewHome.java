package com.example.e_commerce_app.view.home;

import com.example.e_commerce_app.object.Banner;
import com.example.e_commerce_app.object.GroupProductType;

import java.util.List;

public interface IViewHome {
    void showBanners(List<Banner> banners);
    void showGroupProductTypes(List<GroupProductType> groupProductTypes);
    void showError();
    void logout(int status);
    void countCart(int count);
}
