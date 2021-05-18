package com.example.ecommerceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;

public class ProductDetailActivity extends AppCompatActivity {

    TextView productName = findViewById(R.id.prod_name);
    TextView productQuantity = findViewById(R.id.prod_qty);
    TextView productCategory = findViewById(R.id.prod_category);
    TextView productPrice = findViewById(R.id.prod_price);
    TextView productDescription = findViewById(R.id.prod_description);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        Product product = new Product(extras.getString("name"),
                extras.getInt("quantity"),
                extras.getDouble("price"),
                extras.getString("description"),
                extras.getString("picture"),
                new ProductCategory(extras.getString("category")));
    }
}