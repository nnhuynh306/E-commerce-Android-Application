package com.example.ecommerceapp.ViewModel;

import android.content.Context;
import android.graphics.Picture;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.RealmObjects.OrderDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class ShoppingCartViewModel extends ViewModel {

    private LiveRealmResults<CartDetail> cartDetailsLiveData;


    public boolean loadCartDetails(Realm realm, String userName) {
        AtomicBoolean flag = new AtomicBoolean(false);
        realm.executeTransaction(r -> {
            try {
                RealmResults<CartDetail> cartDetails = Objects.requireNonNull(r.where(Cart.class)
                        .equalTo("account._id", userName).findFirst()).getCartDetails();

                this.cartDetailsLiveData = new LiveRealmResults<>(cartDetails);
                flag.set(true);
            } catch (NullPointerException e) {
                e.printStackTrace();
                flag.set(false);
            }
        });
        return flag.get();
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

    public List<CartDetail> getCartDetails(Realm realm, String userName) {
        final List<CartDetail>[] result = new List[]{new ArrayList<>()};
        realm.executeTransaction(r -> {
            try {
                Cart cart = Objects.requireNonNull(r.where(Cart.class)
                        .equalTo("account._id", userName).findFirst());

                result[0] = cart.getCartDetails();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });

        return result[0];
    }

    public boolean checkout(Realm realm, List<CartDetail> cartDetails, Date createdDate, String address, String receiverName, String state, String phoneNumber, Double totalPrice, String ownedAccountName) {
        AtomicBoolean flag = new AtomicBoolean(false);
        realm.executeTransaction(r -> {
            List<OrderDetail> orderDetails = new ArrayList<>();
            Account account = r.where(Account.class).equalTo("_id", ownedAccountName).findFirst();
            Order order = new Order(createdDate, address, receiverName, state, phoneNumber, totalPrice, account);
            r.insertOrUpdate(order);

            for (CartDetail cartDetail: cartDetails) {
                orderDetails.add(new OrderDetail(
                        order, cartDetail.getProduct(),
                        (cartDetail.getProduct().getPrice() * cartDetail.getQuantity()),
                        cartDetail.getQuantity()
                ));
            }
            r.insertOrUpdate(orderDetails);
            flag.set(true);
        });

        return flag.get();
    }

}
