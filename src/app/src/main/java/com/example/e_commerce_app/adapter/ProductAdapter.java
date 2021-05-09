package com.example.e_commerce_app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_app.adapter.viewHolder.ProductHolder;
import com.example.e_commerce_app.common.Common;

import com.example.e_commerce_app.handler.click.IClickItemListener;
import com.example.e_commerce_app.helper.DatabaseHelper;
import com.example.e_commerce_app.object.OrderDetail;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.utils.Utils;
import com.example.e_commerce_app.view.detail.DetailActivity;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    Context context;
    List<Product> list;
    int resource;

    public ProductAdapter(Context context, List<Product> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, int position) {
        final Product object = list.get(position);
        holder.name.setText(object.getName_product());
        NumberFormat numberFormat = new DecimalFormat("###,###");
        holder.price.setText(String.valueOf(numberFormat.format(Integer.valueOf(object.getPrice())) + " VND"));
        holder.ratingBar.setRating(object.getRate());
        holder.rate_number.setText("(" + String.valueOf(object.getNum_people_rates()) + ")");
        holder.like_number.setText(String.valueOf(object.getNum_likes()));

        Utils.loadImage(context, object.getImage(), holder.image, holder.progress);

        holder.btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isLogin()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId_user(Common.CURRENT_USER.getId());
                    orderDetail.setId_product(object.getId());
                    orderDetail.setQuantity(1);
                    new DatabaseHelper(context).saveCart(orderDetail);
                    Utils.showToastShort(context, "Add to cart success!", MDToast.TYPE_SUCCESS);
                } else {
                    //login activity
                }
            }
        });

        holder.setiClickItemListener(new IClickItemListener() {
            @Override
            public void itemClickListener(View view, int position, boolean isLongClick) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("id_product", list.get(position).getId());
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
