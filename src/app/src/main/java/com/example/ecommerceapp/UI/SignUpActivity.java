package com.example.ecommerceapp.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private boolean isSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText email = findViewById(R.id.editText_signup_email);
        EditText password = findViewById(R.id.editText_signup_password);
        EditText repass = findViewById(R.id.editText_signup_RePassword);

        SignUpViewModel signUpViewModel = new SignUpViewModel(this);

        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = ProgressDialog.show(SignUpActivity.this, "",
                        "Loading. Please wait...", true);
                isSignUp = signUpViewModel.signUp(email.getText().toString()
                        ,password.getText().toString(), repass.getText().toString());
                dialog.cancel();
                signUpViewModel.status(isSignUp);
            }
        });
    }
}