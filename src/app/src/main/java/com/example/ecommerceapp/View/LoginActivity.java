package com.example.ecommerceapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN ACTIVITY";
    private int loginState=0;
    String returnActivityName, forwardActivityName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent sentIntent = getIntent();

        returnActivityName = sentIntent.getStringExtra("returnActivityName");
        forwardActivityName = sentIntent.getStringExtra("forwardActivityName");

        EditText email = findViewById(R.id.editText_email);
        EditText password = findViewById(R.id.editText_password);

        LoginViewModel loginViewModel = new LoginViewModel(getApplicationContext());


        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginViewModel.login(email.getText().toString().toLowerCase()
                        ,password.getText().toString())){
                    loginState = 1;
                    ForwardToNextActivity();
                }
                loginViewModel.status(loginState);
            }
        });

        TextView signUp = findViewById(R.id.textView_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(LoginActivity.this, SignUpActivity.class);
                signup.putExtra("returnActivityName", "LoginActivity");
                signup.putExtra("forwardActivityName", forwardActivityName);
                LoginActivity.this.finish();
                startActivity(signup);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: {
                Log.d(TAG, "onOptionsItemSelected: ");
            }
        }
        
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToPreviousActivityIfPossible();
        Log.d(TAG, "onBackPressed: ");
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
        LoginActivity.this.finish();
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
        } else {
            intent = new Intent(this, ProductListActivity.class);
        }
        LoginActivity.this.finish();
        startActivity(intent);
    }
}