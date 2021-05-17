package com.example.ecommerceapp;

import android.content.Context;
import android.util.Log;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class LoginAnonymous {
    private App app;
    private Context context;
    public LoginAnonymous(Context context){
        this.app = new RealmApp(context).getApp();
        this.context = context;

    }
    public void run(){
        Credentials anonymousCredentials = Credentials.anonymous();
        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated anonymously.");
                user.set(app.currentUser());

                SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), this.context.getString(R.string.PARTITION))
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        Log.v("EXAMPLE", "Successfully opened a realm.");

                        realm.close();
                    }
                });

            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });
    }
}
