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
    private RealmResults<Product> ProductListData;

    public boolean loadProductListDetails(Realm realm) {
        AtomicBoolean flag = new AtomicBoolean(false);
        realm.executeTransaction(realm1 -> {
            try{
                ProductListData =  realm.where(Product.class).findAll();
                flag.set(true);
            }
            catch (NullPointerException e){
                e.printStackTrace();
                flag.set(false);
            }
            });
        return flag.get();
    }

    public RealmResults<Product> getProductListData(){return ProductListData;}

}
