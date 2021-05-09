package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.e_commerce_app.adapter.viewHolder.OrderDetailHolder;
import com.example.e_commerce_app.model.ModelDetail;
import com.example.e_commerce_app.object.OrderDetail;

import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailHolder> {
    Context context;
    private List<OrderDetail> list;
    int resource;

    public OrderDetailAdapter(Context context, List<OrderDetail> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public OrderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderDetailHolder holder, final int position) {
        OrderDetail orderDetail = list.get(position);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(orderDetail.getQuantity()), Color.RED);
        holder.product_count.setImageDrawable(drawable);
        ModelDetail modelDetail = new ModelDetail();
        Product product = modelDetail.findById(list.get(position).getId_product());
        final NumberFormat numberFormat = new DecimalFormat("###,###");
        int price = Integer.valueOf(product.getPrice())  * orderDetail.getQuantity();
        holder.price.setText(String.valueOf(numberFormat.format(price) + " VND"));
        holder.name.setText(product.getName_product());
        Utils.loadImage(context, product.getImage(), holder.img, holder.progress);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
