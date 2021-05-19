package com.example.ecommerceapp.View.AdminPageFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
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
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.View.Adapters.AdminOrderManagerAdapter;
import com.example.ecommerceapp.View.Adapters.AdminProductManagerAdapter;
import com.example.ecommerceapp.ViewModel.OrderViewModel;
import com.example.ecommerceapp.ViewModel.ProductListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.Realm;

public class AdminProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdminProductManagerAdapter adminProductManagerAdapter;
    private ProductListViewModel viewModel;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_product, container, false);
    }
    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductListViewModel.class);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        adminProductManagerAdapter = new AdminProductManagerAdapter(requireContext(),viewModel, Navigation.findNavController(view));
        recyclerView.setAdapter(adminProductManagerAdapter);


        viewModel.loadAllProducts(requireContext(), new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 1: {
                        viewModel.getProductLiveRealmResults().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                            @Override
                            public void onChanged(List<Product> products) {
                                adminProductManagerAdapter.setProductList(products);
                            }
                        });
                    }
                    default: { }
                }
            }
        });

        view.findViewById(R.id.search_button).setOnClickListener(v -> {
            String searchStr = ((TextView) view.findViewById(R.id.search_text)).getText().toString().toLowerCase();

            adminProductManagerAdapter.setSearchQuery(searchStr);
        });

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action =
                        AdminPageMainFragmentDirections.actionAdminPageMainFragmentToAdminProductDetailFragment("", "create");

                Navigation.findNavController(view).navigate(action);
            }
        });
    }


}