package com.example.e_commerce_app.adapter.viewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_app.R;

public class ImageHolder extends RecyclerView.ViewHolder{
    public ImageView img;
    public ProgressBar progress;
    public ImageHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        progress = itemView.findViewById(R.id.progress);
    }
}