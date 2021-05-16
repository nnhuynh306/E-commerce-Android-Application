package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
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
import com.example.ecommerceapp.ViewModel.ShoppingCartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.mongodb.App;

public class CheckOutItemAdapter extends RecyclerView.Adapter<CheckOutItemAdapter.ViewHolder> {

    Context context;
    ShoppingCartViewModel shoppingCartViewModel;
    Realm realm;
    List<CartDetail> cartDetails = new ArrayList<>();

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public CheckOutItemAdapter(Context context, ShoppingCartViewModel shoppingCartViewModel) {
        this.context = context;
        this.shoppingCartViewModel = shoppingCartViewModel;
        ;
    };

    public void setRealm(Realm realm) {
        this.realm = realm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.check_out_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDetail cartDetail = this.cartDetails.get(position);
        Product product = cartDetail.getProduct();

        Glide.with(context)
                .load(R.drawable.image_holder)
                .optionalCenterCrop()
                .into(holder.productImage);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice() * cartDetail.getQuantity()));
        holder.productQuantity.setText(String.valueOf(cartDetail.getQuantity()));

    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton productImage;
        private TextView productName;
        private TextView productPrice;

        private TextView productQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);

            productQuantity = itemView.findViewById(R.id.productQuantity);

        }
    }
}
