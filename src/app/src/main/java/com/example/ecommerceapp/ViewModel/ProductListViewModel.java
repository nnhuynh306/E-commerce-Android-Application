package com.example.ecommerceapp.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.RealmQuery;

public class ProductListViewModel extends ViewModel {
    private RealmResults<Product> ProductListLiveData;

    public boolean loadProductListDetails(Realm realm) {
        ProductListLiveData =  realm.where(Product.class).findAll();
        return true;
    }

    public RealmResults<Product> getProductListLiveData(){return ProductListLiveData;}

}
