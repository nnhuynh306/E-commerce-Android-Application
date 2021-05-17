package com.example.ecommerceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewModel.ForgetPassViewModel;

public class ForgetPassActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    EditText rePassText;
    TextView statusText;

    private String email;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);


        emailText= findViewById(R.id.editText_forgetPass_email);
        passwordText = findViewById(R.id.editText_forgetPass_password);
        rePassText = findViewById(R.id.editText_forgetPass_RePassword);


        statusText = findViewById(R.id.forgetPass_status);
        email = emailText.getText().toString();
        pass = passwordText.getText().toString();

        String rePass = rePassText.getText().toString();

        Button reset_btn = findViewById(R.id.btn_reset);

        ForgetPassViewModel forgetPassViewModel  = new ForgetPassViewModel(this);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.compareTo(rePass)!=0){
                    statusText.setText("Password and re password not match!!!");
                } else {
                }
            }
        });
    }
}