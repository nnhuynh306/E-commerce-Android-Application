package com.example.e_commerce_app.view.checkout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.helper.DatabaseHelper;
import com.example.e_commerce_app.presenter.checkout.PresenterLogicCheckout;

import com.example.e_commerce_app.utils.Utils;
import com.valdesekamdem.library.mdtoast.MDToast;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class CheckoutActivity extends AppCompatActivity implements
        IViewCheckout {
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    EditText edPhone, edDesc, edAddress;
    Button btnCheckout;
    PresenterLogicCheckout presenterLogicCheckout;
    //Need call this function after you init database
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Notes : add this code before setContentView
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/font_main.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_checkout);
        //InitView
        initView();
        //InitPresenter
        presenterLogicCheckout = new PresenterLogicCheckout(this, this);
        //InitEvent
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edPhone.getText().toString();
                String address = edAddress.getText().toString();
                String desc = edDesc.getText().toString();
                String current_time = Utils.currentTime();
                if (!phone.trim().equals("") && !address.trim().equals(""))
                    presenterLogicCheckout.checkout(Common.CURRENT_USER.getToken(), Common.CURRENT_USER.getId(), Common.PLACED, phone, address, current_time, desc);
                else
                if (phone.trim().equals("")) error("Please enter your phone");
                if (address.trim().equals("")) error("Please enter your address");
            }
        });
    }

    private void initView() {
        setUpToolbar();
        edPhone = findViewById(R.id.edPhone);
        edDesc = findViewById(R.id.edDesc);
        btnCheckout = findViewById(R.id.btnCheckout);
        edAddress = findViewById(R.id.edAddress);
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FGShop Checkout");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void checkout(int status) {
        if (status == 200) {
            new DatabaseHelper(this).removeAllCart(Common.CURRENT_USER.getId());
            finish();
        }
    }

    @Override
    public void error(String message) {
        Utils.showToastShort(this, message, MDToast.TYPE_ERROR);
    }
}
