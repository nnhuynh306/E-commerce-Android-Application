package com.example.e_commerce_app.presenter.detail.rating;


import android.widget.ProgressBar;

import com.example.e_commerce_app.object.Rate;

import java.util.List;

public interface IPresenterFragmentRating {
    void rates(int id_product);
    List<Rate> loadMoreRates(int id_product, int sumItem, ProgressBar progress);
}
