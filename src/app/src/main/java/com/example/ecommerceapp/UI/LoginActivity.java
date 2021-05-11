package com.example.ecommerceapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.LoginViewModel;

import io.realm.mongodb.App;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}