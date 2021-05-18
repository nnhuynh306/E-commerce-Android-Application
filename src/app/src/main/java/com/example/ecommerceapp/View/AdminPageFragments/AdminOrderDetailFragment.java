package com.example.ecommerceapp.View.AdminPageFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

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

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Order;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.View.Adapters.AdminOrderManagerAdapter;
import com.example.ecommerceapp.View.Adapters.OrderDetailAdapter;
import com.example.ecommerceapp.View.Adapters.OrdersAdapter;
import com.example.ecommerceapp.View.UserPageFragments.UserOrderDetailFragmentArgs;
import com.example.ecommerceapp.ViewModel.OrderViewModel;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminOrderDetailFragment extends Fragment {
    String orderId;

    RecyclerView recyclerView;
    OrderDetailAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_order_detail, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderViewModel orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        String orderId = AdminOrderDetailFragmentArgs.fromBundle(getArguments()).getOrderId();

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

        view.findViewById(R.id.delete_button).setOnClickListener(v -> {
            orderViewModel.deleteOrder(requireContext(), orderId, new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0:
                            Toast.makeText(requireContext(), R.string.delete_order_fail, Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(requireContext(), R.string.delete_order_success, Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).popBackStack();
                            break;
                        default:
                    }
                }
            });
        });

        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            int checkedId = ((RadioGroup) view.findViewById(R.id.orderState)).getCheckedRadioButtonId();

            String newState =  ((RadioButton) view.findViewById(checkedId)).getText().toString();

            orderViewModel.changeOrderState(requireContext(), orderId, newState, new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case -1:
                            Toast.makeText(requireContext(), R.string.change_order_state_fail, Toast.LENGTH_LONG).show();
                            break;
                        case 0:
                            Toast.makeText(requireContext(), R.string.change_order_state_fail, Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(requireContext(), R.string.change_order_state_success, Toast.LENGTH_LONG).show();
                            break;
                        default:
                    }
                }
            });
        });
    }

    private void setupView(View view, Order order) {
        ((TextView) view.findViewById(R.id.receiver_name)).setText(order.getReceiverName());
        ((TextView) view.findViewById(R.id.phone_number)).setText(order.getPhoneNumber());
        ((TextView) view.findViewById(R.id.address)).setText(order.getAddress());
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(order.getTotalPrice()));

        String state = order.getState();

        if (state.equalsIgnoreCase("unconfirmed")) {
//            ((RadioGroup) view.findViewById(R.id.orderState))
            ((RadioButton) view.findViewById(R.id.orderState_unconfirmed)).setChecked(true);
        } else if (state.equalsIgnoreCase("confirmed")) {
            ((RadioButton) view.findViewById(R.id.orderState_confirmed)).setChecked(true);
        } else if (state.equalsIgnoreCase("delivering")) {
            ((RadioButton) view.findViewById(R.id.orderState_delivering)).setChecked(true);
        } else if (state.equalsIgnoreCase("delivered")) {
            ((RadioButton) view.findViewById(R.id.orderState_delivered)).setChecked(true);
        }

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