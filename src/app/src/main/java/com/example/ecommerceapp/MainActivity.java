package com.example.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.Cart;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;
import com.example.ecommerceapp.View.ProductListActivity;
import com.example.ecommerceapp.View.UserPageActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.ConnectionState;
import io.realm.mongodb.sync.Progress;
import io.realm.mongodb.sync.ProgressListener;
import io.realm.mongodb.sync.ProgressMode;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.sync.SyncSession;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ProgressBar progressBar = null;

    TextView errorMessage = null;

    LoadDataAsynTask loadDataAsynTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessage = findViewById(R.id.error_message);

        progressBar = findViewById(R.id.progress_circular);
        new LoadDataAsynTask(this).execute();


    }

    private class LoadDataAsynTask extends AsyncTask<Void, Void, Void> {

        public LoadDataAsynTask(Context context) {
            this.context = context;
        }

        Context context;

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            App app = new RealmApp(context).getApp();

            Credentials anonymousCredentials = Credentials.anonymous();
            AtomicReference<User> user = new AtomicReference<User>();

            while (app.currentUser() == null) {
                try {
                    Log.d(TAG, "doInBackground: ");
                    app.login(anonymousCredentials);
                } catch (Exception e) {
                    Log.d(TAG, "can't login: ");
                }
            }

            Log.d(TAG, "login: ");
            progressBar.setVisibility(View.VISIBLE);

            try {
                syncData(app);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void syncData(App app) throws InterruptedException {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), getString(R.string.PARTITION))
                .allowQueriesOnUiThread(false)
                .allowWritesOnUiThread(false)
                .build();

        Realm realm = Realm.getInstance(config);

        SyncSession syncSession = app.getSync().getSession(config);

        syncSession.downloadAllServerChanges();
        Log.d(TAG, "downloading changes: ");
        syncSession.addDownloadProgressListener(ProgressMode.INDEFINITELY, new ProgressListener() {
            @Override
            public void onChange(Progress progress) {
                if (progress.getFractionTransferred() == 1) {
                    Intent user = new Intent(MainActivity.this, ProductListActivity.class);
                    MainActivity.this.finish();
                    startActivity(user);
                }
            }

        });

        realm.close();

    }

}