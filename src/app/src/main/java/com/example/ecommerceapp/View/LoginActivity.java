package com.example.ecommerceapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private int loginState=0;
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
                if(loginViewModel.login(email.getText().toString(),password.getText().toString())){
                    loginState = 1;
                }
                loginViewModel.status(loginState);
            }
        });

        TextView signUp = findViewById(R.id.textView_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signup);
            }
        });
    }
}