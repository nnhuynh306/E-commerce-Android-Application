package com.example.ecommerceapp.View.UserPageFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.View.Adapters.OrdersAdapter;
import com.example.ecommerceapp.ViewModel.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class UserOrderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private OrdersAdapter ordersAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_order, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        recyclerView = view.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        ordersAdapter = new OrdersAdapter(requireContext(), Navigation.findNavController(view));
        recyclerView.setAdapter(ordersAdapter);

        String userName = "admin";

        Log.d("ORDER", "onViewCreated: ");

        orderViewModel.getOrders(requireActivity(), userName, new  Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        orderViewModel.getOrderLiveRealmResults().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {

                            @Override
                            public void onChanged(List<Order> orders) {
                                ordersAdapter.setData(orders);
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}