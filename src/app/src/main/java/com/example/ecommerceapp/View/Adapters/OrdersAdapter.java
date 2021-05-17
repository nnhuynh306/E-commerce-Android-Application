package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.View.UserPageFragments.UserOrderDetailFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserOrderFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserPageMainFragment;
import com.example.ecommerceapp.View.UserPageFragments.UserPageMainFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> orders = new ArrayList<>();
    private Context context;
    private NavController navController;

    public OrdersAdapter(Context context, NavController navController) {
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @NotNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_user_order_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.idView.setText(order.getId().toString());
        holder.priceView.setText(String.valueOf(order.getTotalPrice()));
        holder.dateView.setText(DateFormat.ddmmyyyyhhmmss.format(order.getCreatedDate()));
        holder.stateView.setText(order.getState());
    }

    public void setData(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idView, priceView, dateView, stateView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.orderId);
            priceView = itemView.findViewById(R.id.orderPrice);
            dateView = itemView.findViewById(R.id.orderDate);
            stateView = itemView.findViewById(R.id.orderState);

            itemView.setOnClickListener(v -> {
                UserPageMainFragmentDirections.ActionUserPageMainFragmentToUserOrderDetailFragment action =
                        UserPageMainFragmentDirections.actionUserPageMainFragmentToUserOrderDetailFragment
                                (orders.get(getAdapterPosition()).getId().toString());
                navController.navigate(action);

            });
        }
    }
}
