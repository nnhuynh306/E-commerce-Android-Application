package com.example.ecommerceapp.ViewModel;

import android.content.Context;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;

import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;

public class ForgetPassViewModel {

    private App app;
    public ForgetPassViewModel(Context context){
        app = new RealmApp(context).getApp();
    }

    public boolean forgetPass(String email, String newPass){
        forgetPassAsync threadForget = new forgetPassAsync(email,newPass);
        threadForget.start();
        synchronized (threadForget){
            try {
                threadForget.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;

    }
    class forgetPassAsync extends Thread{
        String email;
        String pass;
        public forgetPassAsync(String email, String pass){
            this.email = email;
            this.pass = pass;
        }
        @Override
        public void run() {
            synchronized (this){
                try {
                    super.run();
                    Credentials credentials = Credentials.emailPassword(email,pass);
                }catch (Exception e){
                    e.printStackTrace();
                }
                notify();
            }
        }
    }
}
