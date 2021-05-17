package com.example.ecommerceapp.ViewModel;

import android.content.Context;
import android.widget.Toast;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;

public class SignUpViewModel {
    private Context context;
    private String successMessage = "Sign up successful";
    private String errorMessage = "Password not match with re password";
    private String email;
    private String password;
    private String repass;
    private App app;
    private Realm realm;
    private Account account;
    private Cart cart;



    public SignUpViewModel(Context context) {
        this.context = context;
        this.app = new RealmApp(context).getApp();

        RealmConfiguration config = new RealmConfiguration.Builder()
                .build();
        this.realm =Realm.getInstance(config);
    }

    public void setTransaction(){
        this.realm.executeTransactionAsync(transactionRealm ->{
            transactionRealm.insertOrUpdate(this.account);
            transactionRealm.insertOrUpdate(this.cart);
        });
    }

    public boolean signUp(String email, String password){
        SignUp threadSignUp = new SignUp(email,password);
        threadSignUp.start();
        synchronized (threadSignUp){
            try {
                threadSignUp.wait();
            } catch (InterruptedException e) {
                return false;
            }
            this.account = new Account(email,password);
            this.cart = new Cart(this.account);
            setTransaction();
            return true;
        }
    }
    public void status(boolean loginStatus){
        if (loginStatus){
            Toast.makeText(context,successMessage,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show();
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
