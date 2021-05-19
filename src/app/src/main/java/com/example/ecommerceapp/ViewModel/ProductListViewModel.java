package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;

import org.bson.types.ObjectId;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.RealmQuery;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class ProductListViewModel extends AndroidViewModel {
    private RealmResults<Product> ProductListData;

    private LiveRealmResults<Product> productLiveRealmResults;

    private App app;

    private MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();

    public ProductListViewModel(Application application) {
        super(application);
        app = new RealmApp(application).getApp();
    }


    public boolean loadProductListDetails(Realm realm) {
        AtomicBoolean flag = new AtomicBoolean(false);
        realm.executeTransaction(realm1 -> {
            try{
                ProductListData =  realm.where(Product.class).findAll();
                flag.set(true);
            }
            catch (NullPointerException e){
                e.printStackTrace();
                flag.set(false);
            }
            });
        return flag.get();
    }

    public RealmResults<Product> getProductListData(){return ProductListData;}

    public void loadAllProducts( Context context, Handler handler) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    productLiveRealmResults = new LiveRealmResults<Product>(realm.where(Product.class).findAll());

                    handler.sendEmptyMessage(1);
                });

            }
        });
    }

    public void createProduct(Context context,
                              String name, int quantity, Double price, String description, String picture, String category){
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    try {
                        ProductCategory productCategory = r.where(ProductCategory.class).equalTo("name", category.toLowerCase()).findFirst();
                        if (productCategory != null) {
                            Product temp = r.createObject(Product.class, new ObjectId());
                            temp.setPicture(picture);
                            temp.setQuantity(quantity);
                            temp.setPrice(price);
                            temp.setDescription(description);
                            temp.setName(name);
                            temp.setCategory(productCategory);
                        }

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                });
                realm.close();
            }
        });
    }

    public void loadProduct(Context context, String productId) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    try {
                        Product product = Objects.requireNonNull(r.where(Product.class).equalTo("_id", new ObjectId(productId)).findFirst());
                        productMutableLiveData.setValue(product);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                });
            }
        });
    }


    public void deleteProduct(Context context, String productId) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    try {
                        Product product = Objects.requireNonNull(r.where(Product.class).equalTo("_id", new ObjectId(productId)).findFirst());
                        product.deleteFromRealm();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                });
                realm.close();
            }
        });
    }

    public void updateProduct(Context context, String productId,
                              String name, int quantity, Double price, String description, String picture){
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransactionAsync(r -> {
                    try {
                        Product product = Objects.requireNonNull(r.where(Product.class).equalTo("_id", new ObjectId(productId)).findFirst());
                        product.setName(name);
                        product.setDescription(description);
                        product.setPrice(price);
                        product.setQuantity(quantity);
                        product.setPicture(picture);
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                });

                realm.close();
            }
        });
    }

    public LiveRealmResults<Product> getProductLiveRealmResults() {
        return productLiveRealmResults;
    }

    public MutableLiveData<Product> getProductMutableLiveData() {
        return productMutableLiveData;
    }
}
