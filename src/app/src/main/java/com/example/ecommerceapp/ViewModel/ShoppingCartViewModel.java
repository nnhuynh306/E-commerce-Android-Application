package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.sync.SyncConfiguration;

public class ShoppingCartViewModel extends ViewModel {

    private LiveRealmResults<CartDetail> cartDetailsLiveData;


    public void loadCartDetails(Realm realm, String userName) {
        realm.executeTransaction(r -> {
            RealmResults<CartDetail> cartDetails = Objects.requireNonNull(r.where(Cart.class)
                    .equalTo("account._id", userName).findFirst()).getCartDetails();

            this.cartDetailsLiveData = new LiveRealmResults<>(cartDetails);
        });
    }

    public LiveRealmResults<CartDetail> getCartDetailsLiveData() {
        return cartDetailsLiveData;
    }

    public void updateCartDetailQuantity(Realm realm, CartDetail cartDetail, int newQuantity) {
        realm.executeTransaction(r -> {
            cartDetail.setQuantity(newQuantity);
            r.insertOrUpdate(cartDetail);
        });

    }

    public void deleteCartDetail(Realm realm, CartDetail cartDetail) {
        realm.executeTransaction(r -> {
            cartDetail.deleteFromRealm();
        });
    }
}
