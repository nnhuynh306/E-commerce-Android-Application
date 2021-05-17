package com.example.ecommerceapp.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecommerceapp.MongoDBRealm.RealmApp;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.mongodb.App;
import io.realm.mongodb.sync.SyncConfiguration;

public class AccountViewModel extends AndroidViewModel {
    private App app;

    MutableLiveData<Account> accountLiveData = new MutableLiveData<>();

    public AccountViewModel(Application application) {
        super(application);
        app = new RealmApp(application).getApp();
    }

    public void getAccount(Context context, String userName) {

        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                accountLiveData.setValue(realm.where(Account.class).equalTo("_id", userName).findFirst());


            }
        });
    }

    public void saveAccount(Context context, Account account) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransaction(r -> {
                    r.insertOrUpdate(account);
                });

                realm.close();
            }
        });
    }

    public void changePassword(Context context, String userName, String oldPassword, String newPassword,  Handler handler) {
        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), context.getString(R.string.PARTITION))
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                realm.executeTransactionAsync(r -> {
                    Account account = r.where(Account.class).equalTo("_id", userName).findFirst();
                    if (account != null) {
                        if (!account.getPassword().equals(oldPassword)) {
                            handler.sendEmptyMessage(0);
                        } else {
                            account.setPassword(newPassword);
                            handler.sendEmptyMessage(1);
                        }
                    } else {
                        handler.sendEmptyMessage(-1);
                    }
                });

                realm.close();
            }
        });
    }

    public MutableLiveData<Account> getAccountLiveData() {
        return accountLiveData;
    }
}
