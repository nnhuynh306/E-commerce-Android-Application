package com.example.e_commerce_app.presenter.detail.commom;


import com.example.e_commerce_app.model.ModelDetail;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.view.detail.commom.IViewDetailCommom;

public class PresenterLogicDetailCommom implements IPresenterDetailCommom {
    IViewDetailCommom view;
    ModelDetail model;

    public PresenterLogicDetailCommom(IViewDetailCommom view) {
        this.view = view;
        model = new ModelDetail();
    }

    @Override
    public void fillData(int id_product) {
        Product product = model.findById(id_product);
        if (product != null) view.fillData(product);
        else view.showError("Error 404");
    }
}
