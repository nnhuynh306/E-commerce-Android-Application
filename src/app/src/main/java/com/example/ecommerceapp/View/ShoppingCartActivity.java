package com.example.ecommerceapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.View.Adapters.ShoppingCartAdapter;
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;


public class ShoppingCartActivity extends AppCompatActivity {

    Toolbar toolbar;

    ShoppingCartViewModel shoppingCartViewModel;

    RecyclerView cartView;

    Realm realm;

    ShoppingCartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        shoppingCartViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        String userName = "admin";

        String appID = getString(R.string.realm_app_id); // replace this with your App ID
        App app = new App(new AppConfiguration.Builder(appID).build());

        Credentials anonymousCredentials = Credentials.anonymous();


        cartView = findViewById(R.id.cart_list);

        cartAdapter = new ShoppingCartAdapter(this, shoppingCartViewModel);

        cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartView.setAdapter(cartAdapter);

        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated anonymously.");

                SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), getString(R.string.PARTITION))
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        ShoppingCartActivity.this.realm = realm;
                        cartAdapter.setRealm(realm);
                        shoppingCartViewModel.loadCartDetails(realm, userName);
                        shoppingCartViewModel.getCartDetailsLiveData().observe(ShoppingCartActivity.this, new Observer<List<CartDetail>>() {
                            @Override
                            public void onChanged(List<CartDetail> cartDetails) {
                                cartAdapter.setCartDetails(cartDetails);
                            }
                        });
                    }
                });
            }
        });

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}