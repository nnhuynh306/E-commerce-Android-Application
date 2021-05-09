package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_app.adapter.viewHolder.ImageHolder;
import com.example.e_commerce_app.utils.Utils;


import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {
    Context context;
    List<String> list;
    int resource;

    public ImageAdapter(Context context, List<String> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        final String path = list.get(position);

        Utils.loadImage(context, path, holder.img, holder.progress);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

