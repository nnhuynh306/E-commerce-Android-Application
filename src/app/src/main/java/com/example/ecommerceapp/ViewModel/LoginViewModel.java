package com.example.ecommerceapp.ViewModel;


import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.controller.DB_Controller;

import io.realm.mongodb.App;

public class LoginViewModel extends BaseObservable {

    private Context context;
    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";
    private String email;
    private String password;
    private DB_Controller db_controller;


    public LoginViewModel(Context context) {
        this.context = context;
        db_controller = new DB_Controller( context);
    }

    public void login(String email, String password){
        this.email = email;
        this.password = password;

        db_controller.login(email,password);

    }
    public int getLoginStatus(){return db_controller.getCheckLogin();}
    public void status(boolean loginStatus){
        if (loginStatus){
            Toast.makeText(context,successMessage,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show();
        }
    }

}
