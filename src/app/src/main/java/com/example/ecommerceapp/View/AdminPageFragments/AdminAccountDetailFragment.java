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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.ViewModel.AccountViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdminAccountDetailFragment extends Fragment {

    private TextView userNameView, emailView;
    private TextView nameView, phoneNumberView, addressView, dobView;

    private AccountViewModel accountViewModel;
    private Account currentAccount = null;
    private static SimpleDateFormat simpleDateFormat = DateFormat.ddmmyyyy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_account_detail, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        accountViewModel = viewModelProvider.get(AccountViewModel.class);

        String userName = AdminAccountDetailFragmentArgs.fromBundle(getArguments()).getAccountId();

        setupActionBar(view);
        setupView(view);

        accountViewModel.getAccount(requireActivity(), userName);
        accountViewModel.getAccountLiveData().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account != null) {
                    currentAccount = account;
                    setupViewContent(account);
                }
            }
        });

        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

    }

    private void setupActionBar(View view) {
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    private void setupView(View view) {
        userNameView = view.findViewById(R.id.userName);
        emailView = view.findViewById(R.id.email);

        nameView = view.findViewById(R.id.name);
        phoneNumberView = view.findViewById(R.id.phoneNumber);
        dobView = view.findViewById(R.id.dob);
        addressView = view.findViewById(R.id.address);
    }

    private void setupViewContent(Account account) {
        userNameView.setText(account.get_id());

        if (account.getEmail() != null) {
            emailView.setText(account.getEmail());
        }

        if (account.getName() != null) {
            nameView.setText(account.getName());
        }

        if (account.getPhoneNumber() != null) {
            phoneNumberView.setText(account.getPhoneNumber());
        }

        if (account.getDob() != null) {
            dobView.setText(simpleDateFormat.format(account.getDob()));
        }

        if (account.getAddress() != null) {
            addressView.setText(account.getAddress());
        }

    }
}