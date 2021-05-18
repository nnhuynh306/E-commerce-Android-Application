package com.example.ecommerceapp.ViewModel;


import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;

import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class LoginViewModel extends BaseObservable {

    private Context context;
    private String successMessage = "Login successful";
    private String errorMessage = "Email or Password is not valid";
    private String email;
    private String password;
    private User curUser;
    private App app;
    private RealmApp realmApp;

    public LoginViewModel(Context context) {
        this.context = context;
        this.realmApp = new RealmApp(context);
        this.app = realmApp.getApp();
    }

    public boolean login(String email, String password){
        this.email = email;
        this.password = password;

        Login threadLogin = new Login(this.email,this.password);
        threadLogin.start();
        synchronized (threadLogin){
            try {
                threadLogin.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (curUser!=null && curUser.isLoggedIn()){
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
    class Login extends Thread{
        String email;
        String pass;
        public Login(String email, String pass){
            this.email = email;
            this.pass = pass;
        }
        @Override
        public void run() {
            synchronized (this){
                try {
                    super.run();
                    Credentials credentials = Credentials.emailPassword(email,pass);
                    curUser = app.login(credentials);
                }catch (Exception e){
                    e.printStackTrace();
                }
                notify();
            }
        }
    }

}
