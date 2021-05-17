package com.example.ecommerceapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LoginAnonymous(this).run();
    }
}