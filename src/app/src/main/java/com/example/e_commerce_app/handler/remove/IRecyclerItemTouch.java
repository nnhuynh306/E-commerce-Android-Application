package com.example.e_commerce_app.handler.remove;

import androidx.recyclerview.widget.RecyclerView;

public interface IRecyclerItemTouch {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
