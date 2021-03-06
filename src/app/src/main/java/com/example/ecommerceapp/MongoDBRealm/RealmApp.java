package com.example.ecommerceapp.MongoDBRealm;

import android.content.Context;

import com.example.ecommerceapp.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class RealmApp {
    private static App app = null;
    private static String accountID = null;

    public RealmApp(Context context) {
        if (app == null) {
            String appID = context.getString(R.string.realm_app_id);
            app = new App(new AppConfiguration.Builder(appID).build());
        }
    }

    public App getApp() {
        return app;
    }

    public void setAccountID(String accountID) {
        RealmApp.accountID = accountID;
    }

    public String getAccountID() {
        return accountID;
    }

}
