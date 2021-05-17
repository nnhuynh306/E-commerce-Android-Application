package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.View.ProductDetailActivity;
import com.example.ecommerceapp.ViewModel.ProductListViewModel;
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.mongodb.App;

public class ProductListItemAdapter extends RecyclerView.Adapter<ProductListItemAdapter.ViewHolder> {
    Context context;
    ProductListViewModel productListViewModel;
    Realm realm;
    App app;
    List<Product> productList = new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }


    public ProductListItemAdapter(Context context, ProductListViewModel productListViewModel) {
        this.context = context;
        this.productListViewModel = productListViewModel;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ProductListItemAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.products_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductListItemAdapter.ViewHolder holder, int position) {
        Product product = this.productList.get(position);

        Glide.with(context)
                .load(R.drawable.image_holder)
                .optionalCenterCrop()
                .into(holder.productImage);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", product.getName());
                bundle.putString("description",product.getDescription());
                bundle.putDouble("price", product.getPrice());
                bundle.putInt("quantity",product.getQuantity());
                intent.putExtras(bundle);

                realm.close();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton productImage;
        private TextView productName;
        private TextView productPrice;
        private TextView productQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.prod_image);
            productName = itemView.findViewById(R.id.prod_name);
            productPrice = itemView.findViewById(R.id.prod_price);
            productQuantity = itemView.findViewById(R.id.prod_qty);
        }
    }
}
