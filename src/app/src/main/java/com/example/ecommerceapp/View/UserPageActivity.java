package com.example.ecommerceapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ecommerceapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.mongodb.User;

public class UserPageActivity extends AppCompatActivity {
    String returnActivityName, forwardActivityName;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        Intent sentIntent = getIntent();

        returnActivityName = sentIntent.getStringExtra("returnActivityName");
        forwardActivityName = sentIntent.getStringExtra("forwardActivityName");

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        FloatingActionButton home = findViewById(R.id.user_home);
        home.bringToFront();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent product_List = new Intent(UserPageActivity.this, ProductListActivity.class);
                startActivity(product_List);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.user_cart:
                Intent shoppingCart = new Intent(this, ShoppingCartActivity.class);
                shoppingCart.putExtra("returnActivityName", "UserPageActivity");
                finish();
                startActivity(shoppingCart);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        Intent intent;
        if (returnActivityName == null) {
            intent = new Intent(this, ProductListActivity.class);
        } else if (returnActivityName.equalsIgnoreCase("ProductDetailActivity")) {
            intent = new Intent(this, ProductDetailActivity.class);
        } else if (returnActivityName.equalsIgnoreCase("ProductListActivity")) {
            intent = new Intent(this, ProductListActivity.class);
        } else {
            intent = new Intent(this, ShoppingCartActivity.class);
        }
        UserPageActivity.this.finish();
        startActivity(intent);
    }
}
