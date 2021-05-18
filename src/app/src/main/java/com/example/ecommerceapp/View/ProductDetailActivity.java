package com.example.ecommerceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;

public class ProductDetailActivity extends AppCompatActivity {

   int quantityChoosing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}