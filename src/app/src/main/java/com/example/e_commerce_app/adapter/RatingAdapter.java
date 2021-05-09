package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapter.viewHolder.RatingHolder;
import com.example.e_commerce_app.object.Rate;
import com.example.e_commerce_app.utils.Utils;


import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingHolder> {
    Context context;
    List<Rate> list;
    int resource;

    public RatingAdapter(Context context, List<Rate> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
    }

    @Override
    public RatingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(final RatingHolder holder, int position) {
        final Rate object = list.get(position);
        holder.name.setText(object.getUser().getName());
        holder.comment.setText(object.getContent().trim());
        holder.time.setText(Utils.convertTime(object.getTime_rate()));
        holder.ratingBar.setRating(object.getStars());
        if (object.getUser().getAvatar() != null && !object.getUser().getAvatar().equals("null")) {
            Utils.loadImage(context, object.getUser().getAvatar(), holder.img, holder.progress);
        } else {
            holder.img.setImageResource(R.drawable.image_null);
            holder.progress.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
