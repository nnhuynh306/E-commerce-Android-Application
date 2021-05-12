package com.example.ecommerceapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.LoginViewModel;
import com.example.ecommerceapp.controller.DB_Controller;

import io.realm.mongodb.App;

public class LoginActivity extends AppCompatActivity {
    private int loginState;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.editText_email);
        EditText password = findViewById(R.id.editText_password);

        LoginViewModel loginViewModel = new LoginViewModel(getApplicationContext());


        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.login(email.getText().toString(),password.getText().toString());

            }
        });

        TextView signUp = findViewById(R.id.textView_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signup);
            }
        });
    }
}