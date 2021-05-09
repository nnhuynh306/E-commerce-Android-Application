package com.example.e_commerce_app.view.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapter.OrderAdapter;
import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.object.Order;
import com.example.e_commerce_app.presenter.order.PresenterLogicOrder;

import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class OrderActivity extends AppCompatActivity implements
        IViewOrder{
    private static final String TAG = OrderActivity.class.getSimpleName();
    RecyclerView recycler_order;
    OrderAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    PresenterLogicOrder presenterLogicOrder;

    //Need call this function after you init database
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Notes : add this code before setContentView
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/font_main.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_order);
        //InitView
        initView();
        //InitPresenter
        presenterLogicOrder = new PresenterLogicOrder(this);
        presenterLogicOrder.orders(Common.CURRENT_USER.getToken(), Common.CURRENT_USER.getId());
    }

    private void initView() {
        setUpToolbar();
        recycler_order = findViewById(R.id.recycler_order);
        recycler_order.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recycler_order.setLayoutManager(mLayoutManager);
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FGShop Order");
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
    public void orders(List<Order> orders) {
        adapter = new OrderAdapter(this, orders, R.layout.item_order);
        recycler_order.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterLogicOrder.orders(Common.CURRENT_USER.getToken(), Common.CURRENT_USER.getId());
    }
}
