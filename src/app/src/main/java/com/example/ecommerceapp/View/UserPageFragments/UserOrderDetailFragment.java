package com.example.ecommerceapp.View.UserPageFragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.View.Adapters.OrderDetailAdapter;
import com.example.ecommerceapp.ViewModel.OrderViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;


public class UserOrderDetailFragment extends Fragment {

    String orderId;

    RecyclerView recyclerView;
    OrderDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_order_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderViewModel orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        String orderId = UserOrderDetailFragmentArgs.fromBundle(getArguments()).getOrderId();

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new OrderDetailAdapter(requireContext());

        setupActionBar(view);

        TextView orderIdView = view.findViewById(R.id.orderId);
        orderIdView.setText(orderId);

        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        orderViewModel.getOrder(requireContext(), orderId);
        orderViewModel.getOrderMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order order) {
                setupView(view, order);
            }
        });
    }

    private void setupView(View view, Order order) {
        ((TextView) view.findViewById(R.id.receiver_name)).setText(order.getReceiverName());
        ((TextView) view.findViewById(R.id.phone_number)).setText(order.getPhoneNumber());
        ((TextView) view.findViewById(R.id.address)).setText(order.getAddress());
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(order.getTotalPrice()));
        ((TextView) view.findViewById(R.id.state)).setText(order.getState());
        ((TextView) view.findViewById(R.id.date)).setText(DateFormat.ddmmyyyyhhmmss.format(order.getCreatedDate()));
        setupRecyclerView(view, order);
    }

    private void setupRecyclerView(View view, Order order) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adapter.setOrderDetails(order.getOrderDetails());
        recyclerView.setAdapter(adapter);
    }

    private void setupActionBar(View view) {
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

}