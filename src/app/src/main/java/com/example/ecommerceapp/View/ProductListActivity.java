package com.example.ecommerceapp.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.View.Adapters.ProductListItemAdapter;
import com.example.ecommerceapp.ViewModel.ProductListViewModel;

import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.sync.SyncConfiguration;

public class ProductListActivity extends AppCompatActivity {

    private ProductListViewModel productListViewModel;
    private Realm realm;
    private App app;
    private RecyclerView recyclerViewProduct;
    private ProductListItemAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        app = new RealmApp(this).getApp();
        Credentials anonymousCredentials = Credentials.anonymous();
        productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        productListAdapter = new ProductListItemAdapter(this,productListViewModel);
        recyclerViewProduct = findViewById(R.id.product_list);
        recyclerViewProduct.setAdapter(productListAdapter);

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
                        ProductListActivity.this.realm = realm;
                        productListAdapter.setRealm(realm);
                        if (productListViewModel.loadProductListDetails(realm)){
                            List<Product> products = realm.copyFromRealm(productListViewModel.getProductListData());
                            productListAdapter.setProductList(products);
                        }
                    }
                });
            }
        });

        EditText searchBar = (EditText)findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productListAdapter.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.login:
                Intent login = new Intent(this, LoginActivity.class);
                finish();
                startActivity(login);
                break;
            case R.id.signup:
                Intent signUp = new Intent(this, SignUpActivity.class);
                finish();
                startActivity(signUp);
                break;
            case R.id.cart:
                Intent shoppingCart = new Intent(this, ShoppingCartActivity.class);
                finish();
                startActivity(shoppingCart);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}