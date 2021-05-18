package com.example.ecommerceapp.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.ViewModel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private boolean isSignUp;
    private String email;
    private String pass;

    String returnActivityName, forwardActivityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent sentIntent = getIntent();
        returnActivityName = sentIntent.getStringExtra("returnActivityName");
        forwardActivityName = sentIntent.getStringExtra("forwardActivityName");


        EditText emailText = findViewById(R.id.editText_signup_email);
        EditText passwordText = findViewById(R.id.editText_signup_password);
        EditText repassText = findViewById(R.id.editText_signup_RePassword);

        TextView status = findViewById(R.id.textView_signup_status);
        status.setVisibility(View.GONE);

        SignUpViewModel signUpViewModel = new SignUpViewModel(this);

        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = ProgressDialog.show(SignUpActivity.this, "",
                        "Loading. Please wait...", true);
                email = emailText.getText().toString().toLowerCase();
                pass = passwordText.getText().toString();

                if(repassText.getText().toString().compareTo(passwordText.getText().toString())!=0)
                {
                    status.setVisibility(View.VISIBLE);
                    status.setText("Password and re password not match!!");
                    dialog.cancel();
                } else {
                    if(signUpViewModel.signUp(email,pass)){
                        dialog.cancel();
                        signUpViewModel.status(true);
                    } else {
                        signUpViewModel.status(false);
                    }
                }
            }
        });

        TextView login = findViewById(R.id.textView_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                login.putExtra("returnActivityName", returnActivityName);
                login.putExtra("forwardActivityName", forwardActivityName);
                startActivity(login);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToPreviousActivityIfPossible();
    }

    private void ForwardToNextActivity() {
        if (forwardActivityName == null) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        } else if (forwardActivityName.equalsIgnoreCase("CheckOutActivity")) {
            Intent intent = new Intent(this, CheckOutActivity.class);
            startActivity(intent);
        } else if (forwardActivityName.equalsIgnoreCase("ProductDetailActivity")) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            startActivity(intent);
        } else if (forwardActivityName.equalsIgnoreCase("ProductListActivity")) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        } else if (forwardActivityName.equalsIgnoreCase("ShoppingCartActivity")) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
    }

    private void backToPreviousActivityIfPossible() {
        if (returnActivityName == null) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        } else if (returnActivityName.equalsIgnoreCase("ProductDetailActivity")) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            startActivity(intent);
        } else if (returnActivityName.equalsIgnoreCase("ProductListActivity")) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        } else if (returnActivityName.equalsIgnoreCase("LoginActivity")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("returnActivityName", returnActivityName);
            intent.putExtra("forwardActivityName", forwardActivityName);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
    }
}