package com.example.e_commerce_app.presenter.detail.rating;


import android.view.View;
import android.widget.ProgressBar;

import com.example.e_commerce_app.model.ModelDetail;
import com.example.e_commerce_app.object.Rate;
import com.example.e_commerce_app.view.detail.rating.IViewFragmentRating;

import java.util.List;

public class PresenterLogicFragmentRating implements IPresenterFragmentRating {
    IViewFragmentRating view;
    ModelDetail model;

    public PresenterLogicFragmentRating(IViewFragmentRating view) {
        this.view = view;
        model = new ModelDetail();
    }

    @Override
    public void rates(int id_product) {
        List<Rate> list = model.rates(id_product, 0);
        if (!list.isEmpty() && list != null) view.rates(list);
    }

    @Override
    public List<Rate> loadMoreRates(int id_product, int sumItem, ProgressBar progress) {
        progress.setVisibility(View.VISIBLE);
        List<Rate> rates = model.rates(id_product, sumItem);
        if (!rates.isEmpty()) progress.setVisibility(View.GONE);
        return rates;
    }
}
