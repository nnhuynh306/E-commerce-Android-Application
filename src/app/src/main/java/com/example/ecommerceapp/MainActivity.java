package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

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

        String appID = getString(R.string.realm_app_id); // replace this with your App ID
        App app = new App(new AppConfiguration.Builder(appID).build());

        Credentials anonymousCredentials = Credentials.anonymous();
        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated anonymously.");
                user.set(app.currentUser());

                SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), getString(R.string.PARTITION))
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        Log.v("EXAMPLE", "Successfully opened a realm.");

//                        realm.executeTransaction(r -> {
//                            RealmQuery<Product> productRealmQuery = realm.where(Product.class);
//                            RealmResults<Product> products = productRealmQuery.equalTo("category.name", "phone").findAll();
//                            Log.v("FIND", "find: " + products.asJSON());
//
//                            RealmQuery<ProductCategory> categoryRealmQuery = realm.where(ProductCategory.class);
//                            RealmResults<ProductCategory> categories = categoryRealmQuery.equalTo("name", "phone").findAll();
//                            Log.v("FIND", "find: " + categories.asJSON());
//
//                        });

                        realm.executeTransaction(r -> {
                            r.where(CartDetail.class).findAll();
                            Cart cart = r.where(Cart.class).equalTo("account._id", "admin").findFirst();
                            assert cart != null;
                            RealmResults<CartDetail> cartDetails = cart.getCartDetails();
                            Log.v("FIND", "find: " + cartDetails.asJSON());
//                            for (Product product: products) {
//                                r.insertOrUpdate(new CartDetail(cart, product, 10));
//                            }
                        });

//                        ProductCategory category = new ProductCategory("phone");
//                        realm.executeTransaction(r -> {
//                            r.insertOrUpdate(category);
//                        });
//
//                        realm.executeTransaction(r -> {
//                            r.insertOrUpdate(new Product("Samsung Galaxy J1", 1000, 12.1, "DESCRIPTION", "samsunggalaxyj1.png", category));
//                            r.insertOrUpdate(new Product("Samsung Galaxy J2", 1000, 12.2, "DESCRIPTION", "samsunggalaxyj2.png", category));
//                            r.insertOrUpdate(new Product("Samsung Galaxy J3", 1000, 12.3, "DESCRIPTION", "samsunggalaxyj3.png", category));
//                            r.insertOrUpdate(new Product("Samsung Galaxy J4", 1000, 12.4, "DESCRIPTION", "samsunggalaxyj4.png", category));
//                            r.insertOrUpdate(new Product("Samsung Galaxy J5", 1000, 12.5, "DESCRIPTION", "samsunggalaxyj5.png", category));
//                            r.insertOrUpdate(new Product("Samsung Galaxy J6", 1000, 12.6, "DESCRIPTION", "samsunggalaxyj6.png", category));
//                        });


                        realm.close();
                    }
                });

            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });



    }
}