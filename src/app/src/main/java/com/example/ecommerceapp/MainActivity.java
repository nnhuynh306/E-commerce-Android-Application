package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.ViewModel.ViewModelTest;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelTest viewModelTest = new ViewModelProvider(this).get(ViewModelTest.class);

        String appID = getString(R.string.realm_app_id); // replace this with your App ID
        App app = new App(new AppConfiguration.Builder(appID).build());

        Credentials anonymousCredentials = Credentials.anonymous();
        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(anonymousCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated anonymously.");
                user.set(app.currentUser());

                Account account = new Account("test", "123");

                SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), getString(R.string.PARTITION))
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        Log.v("EXAMPLE", "Successfully opened a realm.");
                        // Read all tasks in the realm. No special syntax required for synced realms.
                        RealmResults<Account> tasks = realm.where(Account.class).findAll();
                        // Write to the realm. No special syntax required for synced realms.
                        Log.v("EXAMPLE", "Data" + tasks.asJSON());
                        // Don't forget to close your realm!
                        realm.close();
                    }
                });

            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });



    }
}