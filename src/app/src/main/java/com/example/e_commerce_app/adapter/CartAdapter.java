package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.e_commerce_app.adapter.viewHolder.CartHolder;
import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.helper.DatabaseHelper;
import com.example.e_commerce_app.object.Cart;
import com.example.e_commerce_app.object.OrderDetail;
import com.example.e_commerce_app.presenter.cart.PresenterLogicCart;
import com.example.e_commerce_app.utils.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    Context context;
    private List<Cart> list;
    int resource;
    PresenterLogicCart presenterLogicCart;

    public CartAdapter(Context context, List<Cart> list, int resource, PresenterLogicCart presenterLogicCart) {
        this.context = context;
        this.list = list;
        this.resource = resource;
        this.presenterLogicCart = presenterLogicCart;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {
        holder.btnQuantity.setNumber(String.valueOf(list.get(position).getQuantity()));
        final NumberFormat numberFormat = new DecimalFormat("###,###");
        holder.btnQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = list.get(position);
                cart.setQuantity(newValue);
                cart.setTotal(cart.getQuantity() * Integer.parseInt(cart.getPrice()));

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId_product(cart.getId_product());
                orderDetail.setId_user(cart.getId_user());
                orderDetail.setQuantity(cart.getQuantity());
                new DatabaseHelper(context).updateCart(orderDetail);
                holder.price.setText(String.valueOf(numberFormat.format(Integer.valueOf(list.get(position).getTotal())) + " VND"));
                presenterLogicCart.total(Common.CURRENT_USER.getId());
            }
        });
        holder.price.setText(String.valueOf(numberFormat.format(Integer.valueOf(list.get(position).getTotal())) + " VND"));
        holder.name.setText(list.get(position).getName());
        presenterLogicCart.total(Common.CURRENT_USER.getId());
        Utils.loadImage(context, list.get(position).getImage(), holder.img, holder.progress);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Cart getItem(int position) {
        return list.get(position);
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }
}
