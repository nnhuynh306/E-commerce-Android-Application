package com.example.ecommerceapp.ViewModel;


import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ecommerceapp.RealmObjects.Account;

public class LoginViewModel extends BaseObservable {

    private Context context;
    private Account account;
    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";


    @Bindable
    public String getUserEmail() {
        return account.getEmail();
    }
    @Bindable
    public String getUserPassword() {
        return account.getPassword();
    }

    public void setUserEmail(String userEmail){
        account.setEmail(userEmail);
    }
    public void setUserPass(String userPass){
        account.setPassword(userPass);
    }
    public LoginViewModel(Context context) {
        this.context = context;
        account = new Account("","");
    }
    public void onButtonClicked() {
        if (isValid()) {
            Toast.makeText(context,successMessage,Toast.LENGTH_LONG);
        } else{
            Toast.makeText(context,errorMessage,Toast.LENGTH_LONG);
        }

    }
    public boolean isValid() {
        return true;
    }
}
