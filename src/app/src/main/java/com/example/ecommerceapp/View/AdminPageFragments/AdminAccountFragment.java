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
import com.example.ecommerceapp.View.Adapters.AdminAccountManagerAdapter;
import com.example.ecommerceapp.ViewModel.AccountViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class AdminAccountFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdminAccountManagerAdapter adminAccountManagerAdapter;

    private AccountViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_account, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        setupActionBar();
        setupView(view);

        viewModel.loadAllAccounts(requireContext(), new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 1: {
                        viewModel.getAllAccountsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
                            @Override
                            public void onChanged(List<Account> accounts) {
                                adminAccountManagerAdapter.setAccount(accounts);
                            }
                        });
                    }
                    default: {

                    }
                }
            }
        });

        view.findViewById(R.id.search_button).setOnClickListener(v -> {
            String searchStr = ((TextView) view.findViewById(R.id.search_text)).getText().toString();

            if (searchStr.trim().isEmpty()) {
                viewModel.loadAllAccounts(requireContext(), new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);

                        switch (msg.what) {
                            case 1: {
                                viewModel.getAllAccountsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
                                    @Override
                                    public void onChanged(List<Account> accounts) {
                                        adminAccountManagerAdapter.setAccount(accounts);
                                    }
                                });
                            }
                            default: {

                            }
                        }
                    }
                });
                return;
            }

            viewModel.loadSearchedAccount(requireContext(), searchStr, new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what) {
                        case 1: {
                            viewModel.getAllAccountsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
                                @Override
                                public void onChanged(List<Account> accounts) {
                                    adminAccountManagerAdapter.setAccount(accounts);
                                }
                            });
                        }
                        default: {

                        }
                    }
                }
            });
        });
    }

    private void setupActionBar() {

    }

    private void setupView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        adminAccountManagerAdapter = new AdminAccountManagerAdapter(requireContext(), viewModel, Navigation.findNavController(view));

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adminAccountManagerAdapter);

    }

}