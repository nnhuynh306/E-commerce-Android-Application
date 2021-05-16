package com.example.ecommerceapp.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ecommerceapp.R;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
}