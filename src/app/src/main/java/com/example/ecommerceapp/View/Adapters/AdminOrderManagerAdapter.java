package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.View.AdminPageFragments.AdminPageMainFragmentDirections;
import com.example.ecommerceapp.View.UserPageFragments.UserPageMainFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderManagerAdapter extends RecyclerView.Adapter<AdminOrderManagerAdapter.ViewHolder> {
    private List<Order> orders = new ArrayList<>();
    private Context context;
    private NavController navController;

    public AdminOrderManagerAdapter(Context context, NavController navController) {
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_admin_order_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.idView.setText(order.getId().toString());
        holder.usernameView.setText(order.getOwnedUser().getUserName());
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

        TextView idView, usernameView, dateView, stateView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.orderId);
            usernameView = itemView.findViewById(R.id.orderUsername);
            dateView = itemView.findViewById(R.id.orderDate);
            stateView = itemView.findViewById(R.id.orderState);

            itemView.setOnClickListener(v -> {
                NavDirections action =
                        AdminPageMainFragmentDirections.actionAdminPageMainFragmentToAdminOrderDetailFragment(
                                orders.get(getAdapterPosition()).getId().toString());
                navController.navigate(action);

            });
        }
    }
}
