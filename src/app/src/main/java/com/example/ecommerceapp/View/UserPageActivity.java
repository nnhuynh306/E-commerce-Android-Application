package com.example.ecommerceapp.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.AccountViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.realm.mongodb.User;

@SuppressLint("HandlerLeak")
public class UserPageActivity extends AppCompatActivity {
    String returnActivityName, forwardActivityName;
    AccountViewModel accountViewModel;
    String username;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        username = new RealmApp(this).getAccountID();
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

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
            case R.id.admin_page:
            {
                accountViewModel.isAdmin(this, username, new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case 1:
                            {
                                Intent adminPage = new Intent(UserPageActivity.this, AdminPageActivity.class);
                                adminPage.putExtra("returnActivityName", "UserPageActivity");
                                finish();
                                startActivity(adminPage);
                                break;
                            }
                            default:
                                Toast.makeText(UserPageActivity.this, "YOU'RE NOT AN ADMIN", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
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
