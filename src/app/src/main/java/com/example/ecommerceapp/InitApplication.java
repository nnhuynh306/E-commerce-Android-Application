package com.example.ecommerceapp;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
