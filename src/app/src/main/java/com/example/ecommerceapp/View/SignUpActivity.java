package com.example.ecommerceapp.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.ViewModel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private boolean isSignUp;
    private String email;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText emailText = findViewById(R.id.editText_signup_email);
        EditText passwordText = findViewById(R.id.editText_signup_password);
        EditText repassText = findViewById(R.id.editText_signup_RePassword);

        SignUpViewModel signUpViewModel = new SignUpViewModel(this);

        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = ProgressDialog.show(SignUpActivity.this, "",
                        "Loading. Please wait...", true);
                email = emailText.getText().toString();
                pass = passwordText.getText().toString();

                isSignUp = signUpViewModel.signUp(email
                        ,pass, repassText.getText().toString());
                if(isSignUp){
                    dialog.cancel();
                    signUpViewModel.status(true);

                    Account account = new Account();
                    account.set_id(email);
                    account.setPassword(pass);
                    Cart cart = new Cart(account);

                } else {
                    signUpViewModel.status(false);
                }
            }
        });
    }
}