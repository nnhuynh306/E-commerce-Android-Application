package com.example.e_commerce_app.view.product;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.adapter.ExpandAdapter;
import com.example.e_commerce_app.adapter.ProductAdapter;
import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.handler.loadmore.ILoadMore;
import com.example.e_commerce_app.handler.loadmore.LoadMoreScroll;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.object.ProductType;
import com.example.e_commerce_app.presenter.product.PresenterLogicProduct;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class ProductActivity extends AppCompatActivity implements
        IViewProduct,
        ILoadMore
{

    private static final String TAG = ProductActivity.class.getSimpleName();

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView expandableListView;
    ProgressBar progress;
    PresenterLogicProduct presenterLogicProduct;
    int id_group;
    int id_product_type = 0;
    //View
    TextView tvFullName;
    CircleImageView profile_image;
    String title = "";

    private RecyclerView recycler_product;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView.LayoutManager layoutManager;
    List<Product> listProduct = new ArrayList<>();
    ProductAdapter productAdapter;
    //Need call this function after you init database firebase
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
        setContentView(R.layout.activity_product);
        if (getIntent() != null) {
            id_group = getIntent().getIntExtra("id_group", 0);
            title = getIntent().getStringExtra("title");
        }
        //InitView
        initView();
        //InitPresenter
        presenterLogicProduct = new PresenterLogicProduct(this);
        presenterLogicProduct.menus(id_group);
        //Load Data
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listProduct = new ArrayList<>();
                presenterLogicProduct.products(id_product_type);
            }
        });
        //Default, load for first time
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                listProduct = new ArrayList<>();
                presenterLogicProduct.products(id_product_type);
            }
        });
        recycler_product.addOnScrollListener(new LoadMoreScroll(layoutManager, this));
    }

    private void initView() {
        setUpToolbar(); //Set toolbar
        progress = findViewById(R.id.progress);
        drawerLayout = findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.epMenu);
        appBarLayout = findViewById(R.id.appbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        profile_image = findViewById(R.id.profile_image);
        tvFullName      = findViewById(R.id.tvFullName);
        existUser();
        //recycler
        recycler_product    = findViewById(R.id.recycler_product);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_product.setLayoutManager(layoutManager);
        //Add animation recycler
        //SwipeRefresh Layout
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
        );
    }

    private void existUser() {
        if (Common.CURRENT_USER != null) {
            if (Common.CURRENT_USER.getAvatar() != null && !Common.CURRENT_USER.getAvatar().equals("null")) {
                Picasso.with(this)
                        .load(Common.URL + Common.CURRENT_USER.getAvatar())
                        .into(profile_image);
            } else {
                profile_image.setImageResource(R.drawable.image_null);
            }
            tvFullName.setText(Common.CURRENT_USER.getName());
        } else {
            profile_image.setImageResource(R.drawable.image_null);
            tvFullName.setText(getString(R.string.anonymous));
        }
    }

    /**
     * Set up toolbar
     */
    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Option Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Handle event option menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }

    @Override
    public void menus(final List<ProductType> lists) {
        ExpandAdapter expandAdapter = new ExpandAdapter(this, lists, id_group);
        expandableListView.setAdapter(expandAdapter);

        //Default
        id_product_type = lists.get(0).getId();
        presenterLogicProduct.products(id_product_type);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                id_product_type = lists.get(groupPosition).getId();
                presenterLogicProduct.products(id_product_type);
                onBackPressed();
                return true;
            }
        });
        expandAdapter.notifyDataSetChanged();
    }

    @Override
    public void products(List<Product> products) {
        findViewById(R.id.content_null).setVisibility(View.GONE);
        recycler_product.setVisibility(View.VISIBLE);
        listProduct.clear();
        listProduct = products;
        productAdapter = new ProductAdapter(this,
                listProduct,
                R.layout.item_product);
        recycler_product.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void emptyProduct() {
        recycler_product.setVisibility(View.INVISIBLE);
        findViewById(R.id.content_null).setVisibility(View.VISIBLE);
    }

    @Override
    public void loadMore(final int sumItem) {
        recycler_product.post(new Runnable() {
            @Override
            public void run() {
                List<Product> listMore = presenterLogicProduct.loadMoreProducts(id_product_type, sumItem, progress);
                listProduct.addAll(listMore);
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterLogicProduct.products(id_product_type);
    }
}
