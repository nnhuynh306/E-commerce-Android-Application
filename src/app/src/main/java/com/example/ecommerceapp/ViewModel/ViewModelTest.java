package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class ViewModelTest extends ViewModel {


    public void insert(App app, Context context, Account account) {
        SyncConfiguration config =
                new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                Log.v(
                        "EXAMPLE",
                        "Successfully opened a realm with reads and writes allowed on the UI thread."
                );
                realm.executeTransactionAsync(r -> {
                    r.insertOrUpdate(account);
                    Log.v("INSERT", "INSERT USER");
                });
            }
        });
    }
}
