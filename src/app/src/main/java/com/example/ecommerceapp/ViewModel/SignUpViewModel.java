package com.example.ecommerceapp.ViewModel;

import android.content.Context;
import android.widget.Toast;

import com.example.ecommerceapp.controller.DB_Controller;

public class SignUpViewModel {
    private Context context;
    private String successMessage = "Sign up successful";
    private String errorMessage = "Password not match with re password";
    private String email;
    private String password;
    private String repass;
    private DB_Controller db_controller;



    public SignUpViewModel(Context context) {
        this.context = context;
        db_controller = new DB_Controller(context);
    }

    public boolean signUp(String email, String password, String repass){
        this.email = email;
        this.password = password;
        this.repass = repass;
        if (db_controller.signup(this.email,this.password) && repass.equals(password)){
            return true;
        }
        return false;
    }
    public void status(boolean loginStatus){
        if (loginStatus){
            Toast.makeText(context,successMessage,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show();
        }
    }
}
