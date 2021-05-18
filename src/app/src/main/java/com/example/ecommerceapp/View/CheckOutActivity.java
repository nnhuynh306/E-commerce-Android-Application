package com.example.ecommerceapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.View.Adapters.CheckOutItemAdapter;
import com.example.ecommerceapp.View.Adapters.ShoppingCartAdapter;
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;


public class CheckOutActivity extends AppCompatActivity {


    ShoppingCartViewModel shoppingCartViewModel;

    RecyclerView cartView;

    Realm realm;

    CheckOutItemAdapter cartAdapter;

    EditText addressEditText, phoneNumberEditText, receiverNameEditText;

    App app;

    RealmApp realmApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        shoppingCartViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);


        String userName = "admin";

        realmApp = new RealmApp(this);
        app = realmApp.getApp();

        addressEditText = findViewById(R.id.address);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        receiverNameEditText = findViewById(R.id.receiverName);

        Credentials anonymousCredentials = Credentials.anonymous();

        FloatingActionButton home = findViewById(R.id.checkout_home);
        home.bringToFront();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent product_List = new Intent(CheckOutActivity.this, ProductListActivity.class);
                startActivity(product_List);
            }
        });


        cartView = findViewById(R.id.cart_list);

        cartAdapter = new CheckOutItemAdapter(this, shoppingCartViewModel);

        cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartView.setAdapter(cartAdapter);

        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                CheckOutActivity.this.realm = realm;
                cartAdapter.setRealm(realm);
                shoppingCartViewModel.loadCartDetails(realm, userName);
                shoppingCartViewModel.getCartDetailsLiveData().observe(CheckOutActivity.this, new Observer<List<CartDetail>>() {
                    @Override
                    public void onChanged(List<CartDetail> cartDetails) {
                        cartAdapter.setCartDetails(cartDetails);
                        setPrice(cartDetails, 2);
                    }
                });
            }
        });

        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.check_out);

        findViewById(R.id.check_out_button).setOnClickListener(v -> {

            if (cartAdapter.getCartDetails().size() <= 0) {
                Toast.makeText(this, R.string.empty_cart_error, Toast.LENGTH_LONG).show();
                return;
            }

            Date createdDate = Calendar.getInstance().getTime();
            String address = addressEditText.getText().toString();
            String receiverName = receiverNameEditText.getText().toString();
            String state = "UNCONFIRMED";
            String phoneNumber = phoneNumberEditText.getText().toString();
            TextView totalPriceView = findViewById(R.id.totalPrice);
            Double totalPrice = Double.parseDouble(totalPriceView.getText().toString());

            if (receiverName.isEmpty()) {
                Toast toast = Toast.makeText(this, "Receiver's name can't be empty", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                receiverNameEditText.requestFocus();
                return;
            }
            if (phoneNumber.isEmpty()) {
                Toast toast = Toast.makeText(this, "Phone number can't be empty", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                phoneNumberEditText.requestFocus();
                return;
            }

            if (address.isEmpty()) {
                Toast toast = Toast.makeText(this, "Address can't be empty", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                addressEditText.requestFocus();
                return;
            }

            if (shoppingCartViewModel.checkout(realm, userName, shoppingCartViewModel.getCartDetails(realm, userName),
                    createdDate, address, receiverName, state, phoneNumber, totalPrice, userName)) {
                Toast.makeText(this, "Order is saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error saving order. Some products may be out of stock", Toast.LENGTH_LONG).show();
            }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ShoppingCart = new Intent(this, ShoppingCartActivity.class);
        realm.close();
        finish();
        startActivity(ShoppingCart);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(realmApp.getAccountID()!=null){
            getMenuInflater().inflate(R.menu.logged_toolbar,menu);
        }else {
            getMenuInflater().inflate(R.menu.anonymous_toolbar,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}