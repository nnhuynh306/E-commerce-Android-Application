package com.example.ecommerceapp.View.UserPageFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.Util.DateFormat;
import com.example.ecommerceapp.ViewModel.AccountViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class UserInformationFragment extends Fragment {

    private TextView userNameView, emailView;
    private EditText nameView, phoneNumberView, addressView, dobView;

    private AccountViewModel accountViewModel;
    private Account currentAccount = null;
    private static SimpleDateFormat simpleDateFormat = DateFormat.ddmmyyyy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_information, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModelProvider viewModelProvider = new ViewModelProvider(requireActivity());
        accountViewModel = viewModelProvider.get(AccountViewModel.class);

        String userName = new RealmApp(requireContext()).getAccountID();

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

        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            if (currentAccount == null) {
                Toast.makeText(requireContext(), R.string.loading_data, Toast.LENGTH_LONG).show();
            } else {
                Account currentAccount = new Account();
                currentAccount.set_id(this.currentAccount.get_id());
                currentAccount.setName(nameView.getText().toString());
                currentAccount.setPhoneNumber(phoneNumberView.getText().toString());
                try {
                    currentAccount.setDob(simpleDateFormat.parse(dobView.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), R.string.wrong_date_format, Toast.LENGTH_LONG).show();
                    return;
                }
                currentAccount.setAddress(addressView.getText().toString());
                accountViewModel.saveAccount(requireActivity(), currentAccount);
                Toast.makeText(requireContext(), R.string.saving, Toast.LENGTH_LONG).show();
            }
        });
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