package com.example.ecommerceapp.View.UserPageFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.example.ecommerceapp.ViewModel.AccountViewModel;

import org.jetbrains.annotations.NotNull;

@SuppressLint("HandlerLeak")
public class UserPageResetPasswordFragment extends Fragment {

    AccountViewModel accountViewModel;

    EditText oldpassE, newpassE, renewpassE;

    TextView error;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_page_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userName = new RealmApp(requireContext()).getAccountID();

        setupActionBar(view);
        setupView(view);

        navController = Navigation.findNavController(view);

        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        view.findViewById(R.id.save_button).setOnClickListener(v -> {
            changePassword(userName);
        });

        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            navController.popBackStack();
        });
    }

    private void setupActionBar(View view) {
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    private void setupView(View view) {
        this.oldpassE = view.findViewById(R.id.old_password);
        this.newpassE = view.findViewById(R.id.new_password);
        this.renewpassE = view.findViewById(R.id.renew_password);

        this.error = view.findViewById(R.id.error_message);
    }

    private void changePassword(String username) {
        String oldpass = oldpassE.getText().toString();
        String newpass = newpassE.getText().toString();
        String renewpass = renewpassE.getText().toString();

        if (oldpass.isEmpty()) {
            error.setText(R.string.empty_old_pass);
            return;
        }
        if (newpass.isEmpty()) {
            error.setText(R.string.empty_new_pass);
            return;
        }
        if (!renewpass.equals(newpass)) {
            error.setText(R.string.renew_pass_wrong);
            return;
        }

        accountViewModel.changePassword(requireContext(), username, oldpass, newpass, new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case -1:
                    {
                        error.setText(R.string.cant_find_account);
                        break;
                    }
                    case 0:
                    {
                        error.setText(R.string.change_password_fail);
                        break;
                    }
                    case 1:
                    {
                        navController.popBackStack();
                        Toast.makeText(requireContext(), R.string.change_password_success, Toast.LENGTH_LONG).show();
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        });
    }
}