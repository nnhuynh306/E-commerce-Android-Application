package com.example.ecommerceapp.controller;

import android.content.Context;
import android.os.Looper;

import com.example.ecommerceapp.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class DB_Controller {
    private App app;
    private Context context;
    private User curUser;
    public DB_Controller(Context context){
        this.context = context;
        String appID = context.getString(R.string.realm_app_id);
        this.app = new App(new AppConfiguration.Builder(appID).build());
    }

    public boolean login(String email, String pass){
        Login threadLogin = new Login(email,pass);
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

    public boolean signup(String email, String password){
        SignUp threadSignUp = new SignUp(email,password);
        Login threadLogin = new Login(email,password);
        threadSignUp.start();
        synchronized (threadSignUp){
            try {
                threadSignUp.wait();
                threadLogin.start();
                synchronized (threadLogin){
                    threadLogin.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (curUser!=null && curUser.isLoggedIn()){
            return true;
        }
        return false;
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
    class SignUp extends Thread{
        String email;
        String pass;

        @Override
        public void run() {
            synchronized (this){
                try{
                    super.run();
                    app.getEmailPassword().registerUser(email,pass);
                }catch (Exception e){
                    e.printStackTrace();
                }
                notify();
            }
        }

        public SignUp(String email, String pass){
            this.email=email;
            this.pass = pass;
        }
    }
}
