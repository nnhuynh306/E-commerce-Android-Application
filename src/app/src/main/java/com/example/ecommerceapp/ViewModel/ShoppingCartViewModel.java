package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.graphics.Picture;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.RealmObjects.OrderDetail;
import com.example.ecommerceapp.RealmObjects.Product;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class ShoppingCartViewModel extends AndroidViewModel {

    private LiveRealmResults<CartDetail> cartDetailsLiveData;

    private App app;

    public ShoppingCartViewModel(@NonNull @NotNull Application application) {
        super(application);

        app = new RealmApp(application).getApp();
    }


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

    public boolean checkout(Realm realm, String userName, List<CartDetail> cartDetails
            , Date createdDate, String address, String receiverName, String state, String phoneNumber, Double totalPrice, String ownedAccountName) {
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

            Cart cart = Objects.requireNonNull(r.where(Cart.class)
                    .equalTo("account._id", userName).findFirst());

            r.insertOrUpdate(orderDetails);

            RealmResults<CartDetail> deleteCartDetails = cart.getCartDetails();
            deleteCartDetails.deleteAllFromRealm();
            flag.set(true);
        });

        return flag.get();
    }

    public void addToCart(Context context, String productId, int quantity, String username, Handler handler) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    Cart cart = r.where(Cart.class).equalTo("account._id", username).findFirst();

                    if (cart == null) {
                        handler.sendEmptyMessage(0);
                    } else {
                        Product product = r.where(Product.class).equalTo("_id", new ObjectId(productId)).findFirst();

                        if (product == null) {
                            handler.sendEmptyMessage(0);
                        } else {
                            r.insertOrUpdate(new CartDetail(cart, product, quantity));
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
            }
        });
    }

}
