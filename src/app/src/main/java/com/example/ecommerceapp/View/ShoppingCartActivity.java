   package com.example.ecommerceapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.View.Adapters.ShoppingCartAdapter;
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
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

//        toolbar = findViewById(R.id.toolbar);

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
                        if (shoppingCartViewModel.loadCartDetails(realm, userName)) {
                            shoppingCartViewModel.getCartDetailsLiveData().observe(ShoppingCartActivity.this, new Observer<List<CartDetail>>() {
                                @Override
                                public void onChanged(List<CartDetail> cartDetails) {
                                    cartAdapter.setCartDetails(cartDetails);
                                    setPrice(cartDetails, 2);
                                }
                            });
                        }
                    }
                });
            }
        });

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.shopping_cart);

        findViewById(R.id.check_out_button).setOnClickListener(v -> {
            Intent checkOut = new Intent(this, CheckOutActivity.class);
            realm.close();
            finish();
            startActivity(checkOut);
        });
    }

    private void setPrice(@NonNull List<CartDetail> cartDetails, int decimalPlaces) {
        Double subtotalPrice = (double) 0, totalPrice;
        Double discount = (double) 0;
        TextView totalPriceView = findViewById(R.id.totalPrice);
        TextView subtotalPriceView = findViewById(R.id.subtotalPrice);

        for(CartDetail cartDetail: cartDetails) {
            subtotalPrice += cartDetail.getQuantity() * cartDetail.getProduct().getPrice();
        }

        BigDecimal bd;

        bd = BigDecimal.valueOf(subtotalPrice);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        subtotalPriceView.setText(String.valueOf(bd.doubleValue()));

        totalPrice = subtotalPrice - (subtotalPrice * discount);
        bd = BigDecimal.valueOf(totalPrice);
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        totalPriceView.setText(String.valueOf(bd.doubleValue()));
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}