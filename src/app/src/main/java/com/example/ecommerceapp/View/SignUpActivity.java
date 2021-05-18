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


                if(repassText.getText().toString().compareTo(pass)!=0) {
                    dialog.cancel();
                    status.setVisibility(View.VISIBLE);
                    status.setText("Password and re password not match!!");

                } else if (pass.length()<6 || pass.length()>128){
                    dialog.cancel();
                    status.setVisibility(View.VISIBLE);
                    status.setText("Password must between 6 and 128 char");
                }else if (email.isEmpty()){
                    dialog.cancel();
                    status.setVisibility(View.VISIBLE);
                    status.setText("Email empty!!!");
                }else {
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
                SignUpActivity.this.finish();
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
        Intent intent;
        if (forwardActivityName == null) {
            intent = new Intent(this, ProductListActivity.class);
        } else if (forwardActivityName.equalsIgnoreCase("CheckOutActivity")) {
            intent = new Intent(this, CheckOutActivity.class);
        } else if (forwardActivityName.equalsIgnoreCase("ProductDetailActivity")) {
            intent = new Intent(this, ProductDetailActivity.class);
        } else if (forwardActivityName.equalsIgnoreCase("ProductListActivity")) {
            intent = new Intent(this, ProductListActivity.class);
        } else if (forwardActivityName.equalsIgnoreCase("ShoppingCartActivity")) {
            intent = new Intent(this, ShoppingCartActivity.class);
        } else {
            intent = new Intent(this, ProductListActivity.class);
        }
        SignUpActivity.this.finish();
        startActivity(intent);
    }

    private void backToPreviousActivityIfPossible() {
        Intent intent;
        if (returnActivityName == null) {
            intent = new Intent(this, ProductListActivity.class);
        } else if (returnActivityName.equalsIgnoreCase("ProductDetailActivity")) {
            intent = new Intent(this, ProductDetailActivity.class);
        } else if (returnActivityName.equalsIgnoreCase("ProductListActivity")) {
            intent = new Intent(this, ProductListActivity.class);
        } else if (returnActivityName.equalsIgnoreCase("LoginActivity")) {
            intent = new Intent(this, LoginActivity.class);
            intent.putExtra("returnActivityName", returnActivityName);
            intent.putExtra("forwardActivityName", forwardActivityName);
        } else {
            intent = new Intent(this, ProductListActivity.class);
        }
        SignUpActivity.this.finish();
        startActivity(intent);
    }
}