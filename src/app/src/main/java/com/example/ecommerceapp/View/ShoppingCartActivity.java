package com.example.ecommerceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.ecommerceapp.R;


public class ShoppingCartActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
    }
}