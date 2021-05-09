package com.example.e_commerce_app.view.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapter.CartAdapter;
import com.example.e_commerce_app.adapter.viewHolder.CartHolder;
import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.handler.remove.IRecyclerItemTouch;
import com.example.e_commerce_app.handler.remove.RecyclerItemTouchCart;
import com.example.e_commerce_app.object.Cart;
import com.example.e_commerce_app.presenter.cart.PresenterLogicCart;
import com.example.e_commerce_app.utils.Utils;
import com.example.e_commerce_app.view.checkout.CheckoutActivity;
import com.google.android.material.snackbar.Snackbar;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


public class CartActivity extends AppCompatActivity implements IViewCart,
        IRecyclerItemTouch {
    private static final String TAG = CartActivity.class.getSimpleName();

    RecyclerView recycler_cart;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout main_layout;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvTotal;
    Button btnCheckout;
    PresenterLogicCart presenterLogicCart;
    CartAdapter adapter;
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
        setContentView(R.layout.activity_cart);
        //InitView
        initView();
        //InitPresenter
        presenterLogicCart = new PresenterLogicCart(this, this);
        presenterLogicCart.total(Common.CURRENT_USER.getId());
        //Swipe to remove
        ItemTouchHelper.SimpleCallback itemSimpleCallback = new RecyclerItemTouchCart(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemSimpleCallback).attachToRecyclerView(recycler_cart);
        //Load Data
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenterLogicCart.carts(Common.CURRENT_USER.getId());
            }
        });
        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                presenterLogicCart.carts(Common.CURRENT_USER.getId());
            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isLogin()) {
                    Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(checkoutIntent);
                } else {
//                    Utils.openLogin(CartActivity.this);
                }
            }
        });
    }

    private void initView() {
        setUpToolbar();
        recycler_cart = findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(mLayoutManager);
        main_layout = findViewById(R.id.main_layout);
        tvTotal = findViewById(R.id.total);
        btnCheckout = findViewById(R.id.btnCheckout);
        //SwipeRefresh Layout
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FGShop Cart");
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
    public void carts(List<Cart> carts) {
        adapter = new CartAdapter(this, carts, R.layout.item_cart, presenterLogicCart);
        recycler_cart.setAdapter(adapter);
        presenterLogicCart.total(Common.CURRENT_USER.getId());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void total(int total) {
        NumberFormat numberFormat = new DecimalFormat("###,###");
        if (total == 0) tvTotal.setText("0 VND");
        else tvTotal.setText(String.valueOf(numberFormat.format(Integer.valueOf(total)) + " VND"));
    }

    @Override
    public void error(String msg) {
        Utils.showSnackBarShort(main_layout, msg);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartHolder) {
            String name = ((CartAdapter)recycler_cart.getAdapter()).getItem(viewHolder.getAdapterPosition()).getName();
            final Cart deleteItem = ((CartAdapter)recycler_cart.getAdapter()).getItem(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();
            adapter.removeItem(deleteIndex);
            presenterLogicCart.removeIndex(Common.CURRENT_USER.getId(), deleteItem.getId_product());
            //Refresh
            presenterLogicCart.carts(Common.CURRENT_USER.getId());
            //Make SnackBar
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main_layout), name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deleteItem, deleteIndex);
                    presenterLogicCart.save(deleteItem);
                    //Refresh
                    presenterLogicCart.carts(Common.CURRENT_USER.getId());
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterLogicCart.total(Common.CURRENT_USER.getId());
        presenterLogicCart.carts(Common.CURRENT_USER.getId());
    }
}
