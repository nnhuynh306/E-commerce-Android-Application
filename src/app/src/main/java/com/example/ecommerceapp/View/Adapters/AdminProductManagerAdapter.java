package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.View.AdminPageFragments.AdminProductDetailFragment;
import com.example.ecommerceapp.ViewModel.ProductListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;

public class AdminProductManagerAdapter extends RecyclerView.Adapter<AdminProductManagerAdapter.ViewHolder> {

    private Context context;
    private ProductListViewModel productListViewModel;
    private List<Product> productList = new ArrayList<>();
    List<Product> completeProductList = new ArrayList<>();
    private String searchQuery;
    Realm realm;
    App app;

    public AdminProductManagerAdapter(Context context, ProductListViewModel productListViewModel) {
        this.context = context;
        this.productListViewModel = productListViewModel;
        searchQuery ="";
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        this.completeProductList = productList;
        notifyDataSetChanged();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery.toLowerCase();

        if(!searchQuery.isEmpty()){
            List<Product> tempList = new ArrayList<>();
            for(int i = 0; i < productList.size(); i++){
                if (productList.get(i).getName().toLowerCase().contains(searchQuery)||productList.get(i).getDescription().toLowerCase().contains(searchQuery))
                    tempList.add(productList.get(i));
            }
            productList = tempList;
        }
        else {
            productList = completeProductList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public AdminProductManagerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.fragment_admin_product_item,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdminProductManagerAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        Glide.with(context)
                .load(R.drawable.image_holder)
                .optionalCenterCrop()
                .into(holder.productImage);

        holder.productCategory.setText(product.getCategory().getName());
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        holder.adminProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdminProductDetailFragment.class);
                Bundle bundle = new Bundle();

                bundle.putString("id", product.getId().toString());
                bundle.putString("name", product.getName());
                bundle.putString("description",product.getDescription());
                bundle.putDouble("price", product.getPrice());
                bundle.putInt("quantity",product.getQuantity());
                bundle.putString("picture",product.getPicture());
                bundle.putString("category", product.getCategory().getName());
                bundle.putString("intention", "edit");
                intent.putExtras(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View adminProductItem;
        ImageView productImage;
        TextView productName;
        TextView productCategory;
        TextView productQuantity;
        TextView productPrice;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            adminProductItem = itemView.findViewById(R.id.admin_product_item);
            productImage = itemView.findViewById(R.id.admin_product_image);
            productName = itemView.findViewById(R.id.admin_product_name);
            productCategory = itemView.findViewById(R.id.admin_product_category);
            productQuantity = itemView.findViewById(R.id.admin_product_quantity);
            productPrice = itemView.findViewById(R.id.admin_product_price);
        }
    }
}
