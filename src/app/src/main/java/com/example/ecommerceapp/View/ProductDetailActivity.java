package com.example.ecommerceapp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;

import io.realm.Realm;
import io.realm.mongodb.App;

public class ProductDetailActivity extends AppCompatActivity {

    int quantityChoosing;

    App app;
    RealmApp realmApp;

    String productId;

    ShoppingCartViewModel shoppingCartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shoppingCartViewModel = new ViewModelProvider(this).get(ShoppingCartViewModel.class);

        realmApp = new RealmApp(this);
        app = realmApp.getApp();
        setContentView(R.layout.activity_product_detail);
        quantityChoosing = 1;

        TextView productName = findViewById(R.id.prod_name);
        TextView productQuantity = findViewById(R.id.prod_qty);
        TextView productCategory = findViewById(R.id.prod_category);
        TextView productPrice = findViewById(R.id.prod_price);
        TextView productDescription = findViewById(R.id.prod_description);
        TextView quantityToCart = findViewById(R.id.quantity_choose);
        ImageButton btnIncrease = findViewById(R.id.increaseProductDetailQuantity);
        ImageButton btnDecrease = findViewById(R.id.decreaseProductDetailQuantity);


        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        productId = extras.getString("id");
        Product product = new Product(extras.getString("name"),
                extras.getInt("quantity"),
                extras.getDouble("price"),
                extras.getString("description"),
                extras.getString("picture"),
                new ProductCategory(extras.getString("category")));

        productName.setText(product.getName());
        productCategory.setText(product.getCategory().getName());
        productDescription.setText(product.getDescription());
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productPrice.setText(String.valueOf(product.getPrice()));

        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantityChoosing>1){
                    quantityChoosing--;
                    quantityToCart.setText(String.valueOf(quantityChoosing));
                }
            }
        });

        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantityChoosing<product.getQuantity()){
                    quantityChoosing++;
                    quantityToCart.setText(String.valueOf(quantityChoosing));
                }
            }
        });

        findViewById(R.id.btn_add_to_cart).setOnClickListener(v -> {
            String userName = realmApp.getAccountID();
            Log.d("ADD TO CART", "onCreate: " + userName);
            if (userName == null) {
                Toast.makeText(this, R.string.login_needed, Toast.LENGTH_LONG).show();
            } else {
                shoppingCartViewModel.addToCart(this, productId,
                        Integer.parseInt(quantityToCart.getText().toString()),
                        userName, new Handler() {
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                super.handleMessage(msg);

                                switch(msg.what) {
                                    case 0: {
                                        Toast.makeText(ProductDetailActivity.this, R.string.add_to_cart_fail, Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    case 1: {
                                        Toast.makeText(ProductDetailActivity.this, R.string.add_to_cart_success, Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }
                        });
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(realmApp.getAccountID()!=null){
            getMenuInflater().inflate(R.menu.logged_toolbar, menu);
        }else {
            getMenuInflater().inflate(R.menu.anonymous_toolbar,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}