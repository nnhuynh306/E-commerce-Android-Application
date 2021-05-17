package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Order;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class OrderViewModel extends AndroidViewModel {

    private App app;

    private LiveRealmResults<Order> orderLiveRealmResults;

    private MutableLiveData<Order> orderMutableLiveData = new MutableLiveData<>();

    public OrderViewModel(@NonNull @NotNull Application application) {
        super(application);

        app = new RealmApp(application).getApp();
    }

    public void getOrders(Context context, String userName, Handler handler) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                RealmResults<Order> orders = realm.where(Order.class).equalTo("ownedAccount._id", userName).findAll();
                orderLiveRealmResults = new LiveRealmResults<>(orders);
                handler.sendEmptyMessage(1);
            }
        });
    }

    public void getOrder(Context context, String orderId) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                Order order = realm.where(Order.class).equalTo("_id", new ObjectId(orderId)).findFirst();
                orderMutableLiveData.setValue(order);
                realm.close();
            }
        });
    }

    public MutableLiveData<Order> getOrderMutableLiveData() {
        return orderMutableLiveData;
    }

    public LiveRealmResults<Order> getOrderLiveRealmResults() {
        return orderLiveRealmResults;
    }
}
