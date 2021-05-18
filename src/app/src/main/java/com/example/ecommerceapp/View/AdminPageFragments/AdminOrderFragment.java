package com.example.ecommerceapp.View.AdminPageFragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.View.Adapters.AdminOrderManagerAdapter;
import com.example.ecommerceapp.ViewModel.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminOrderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private AdminOrderManagerAdapter ordersAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_order, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        recyclerView = view.findViewById(R.id.order_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        ordersAdapter = new AdminOrderManagerAdapter(requireContext(), Navigation.findNavController(view));
        recyclerView.setAdapter(ordersAdapter);


        orderViewModel.loadAllOrders(requireActivity(), new  Handler() {
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

        view.findViewById(R.id.search_button).setOnClickListener(v -> {
            String searchStr = ((TextView) view.findViewById(R.id.search_text)).getText().toString().toLowerCase();

            if (searchStr.trim().isEmpty()) {
                orderViewModel.loadAllOrders(requireActivity(), new  Handler() {
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
                return;
            }

            orderViewModel.getOrders(requireContext(), searchStr, new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1: {
                            orderViewModel.getOrderLiveRealmResults().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                                @Override
                                public void onChanged(List<Order> orders) {
                                    ordersAdapter.setData(orders);
                                }
                            });
                            break;
                        }
                        default: {

                        }
                    }
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}