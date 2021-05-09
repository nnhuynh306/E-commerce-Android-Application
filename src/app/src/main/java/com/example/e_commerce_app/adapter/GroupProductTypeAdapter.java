package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_app.adapter.viewHolder.GroupProductTypeHolder;
import com.example.e_commerce_app.handler.click.IClickItemListener;
import com.example.e_commerce_app.object.GroupProductType;
import com.example.e_commerce_app.utils.Utils;
import com.example.e_commerce_app.view.product.ProductActivity;


import java.util.List;

public class GroupProductTypeAdapter extends RecyclerView.Adapter<GroupProductTypeHolder> {
    Context context;
    List<GroupProductType> list;
    int resource;

    public GroupProductTypeAdapter(Context context, List<GroupProductType> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public GroupProductTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new GroupProductTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupProductTypeHolder holder, int position) {
        final GroupProductType object = list.get(position);
        holder.name.setText(object.getName_group());

        Utils.loadImage(context, object.getImage(), holder.image, holder.progress);

        holder.setiClickItemListener(new IClickItemListener() {
            @Override
            public void itemClickListener(View view, int position, boolean isLongClick) {
                //Start new Activity
                Intent productIntent = new Intent(context, ProductActivity.class);
                productIntent.putExtra("title", list.get(position).getName_group());
                productIntent.putExtra("id_group", list.get(position).getId()); //Send Group Id to new activity
                context.startActivity(productIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

