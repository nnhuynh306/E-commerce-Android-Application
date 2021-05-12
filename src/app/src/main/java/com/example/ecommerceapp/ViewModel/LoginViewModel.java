package com.example.ecommerceapp.ViewModel;


import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;

import com.example.ecommerceapp.controller.DB_Controller;

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

    public boolean login(String email, String password){
        this.email = email;
        this.password = password;
        if (db_controller.login(email,password)){
            return true;
        }
        return false;
    }
    public void status(int loginStatus){
        if (loginStatus==1){
            Toast.makeText(context,successMessage,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show();
        }
    }

}
