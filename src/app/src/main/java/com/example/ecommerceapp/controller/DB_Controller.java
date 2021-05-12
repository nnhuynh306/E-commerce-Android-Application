package com.example.ecommerceapp.controller;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.ecommerceapp.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class DB_Controller {
    private App app;
    private Context context;
    private int checkLogin = -1;
    public DB_Controller(Context context){
        this.context = context;
        String appID = context.getString(R.string.realm_app_id);
        this.app = new App(new AppConfiguration.Builder(appID).build());
    }
    boolean checkSignUp;

    public void login(String email, String pass){
        Credentials credentials = Credentials.emailPassword(email,pass);
        app.loginAsync(credentials, result -> {
            if(result.isSuccess()){
                checkLogin = 0;
                Toast.makeText(context,"Login OK",Toast.LENGTH_LONG).show();
            }else {
                checkLogin = 1;
                Toast.makeText(context,"Login failed",Toast.LENGTH_LONG).show();
            }
        });

    }
    public int getCheckLogin(){return checkLogin;}

    public boolean signup(String email, String password){
        app.getEmailPassword().registerUserAsync(email,password,it->{
            if (it.isSuccess()){
                checkSignUp = true;
            }else checkSignUp = false;
        });
        return checkSignUp;
    }
}
