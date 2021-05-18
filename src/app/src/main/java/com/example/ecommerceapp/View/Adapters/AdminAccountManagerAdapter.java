package com.example.ecommerceapp.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Account;
import com.example.ecommerceapp.RealmObjects.CartDetail;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.View.AdminPageFragments.AdminPageMainFragmentDirections;
import com.example.ecommerceapp.ViewModel.AccountViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;

public class AdminAccountManagerAdapter extends RecyclerView.Adapter<AdminAccountManagerAdapter.ViewHolder> {
    Context context;
    AccountViewModel accountViewModel;
    List<Account> accounts = new ArrayList<>();
    NavController navController;

    App app;

    public AdminAccountManagerAdapter(Context context, AccountViewModel accountViewModel, NavController navController) {
        this.context = context;
        this.accountViewModel = accountViewModel;
        this.navController = navController;
    };

    public void setApp(App app) {
        this.app = app;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_admin_account_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account account = this.accounts.get(position);

        Glide.with(context)
                .load(R.drawable.avatar)
                .optionalCenterCrop()
                .into(holder.accountImage);

        String userName = account.getUserName();
        String name = account.getName();

        if (userName != null) {
            holder.accountUsername.setText(userName);
        } else {
            holder.accountUsername.setText("");
        }
        if (name != null) {
            holder.accountName.setText(name);
        }else {
            holder.accountName.setText("");
        }
    }

    public void setAccount(List<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton accountImage;
        private TextView accountUsername;
        private TextView accountName;

        private ImageButton deleteButton;
        private ImageButton seeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountImage = itemView.findViewById(R.id.account_avatar);
            accountUsername = itemView.findViewById(R.id.account_username);
            accountName = itemView.findViewById(R.id.account_name);

            deleteButton = itemView.findViewById(R.id.delete_button);
            seeButton = itemView.findViewById(R.id.see_button);

            deleteButton.setOnClickListener(v -> {
                accountViewModel.deleteAccount(context,  accounts.get(getAdapterPosition()));
            });

            seeButton.setOnClickListener(v -> {
                NavDirections action =
                        AdminPageMainFragmentDirections.actionAdminPageMainFragmentToAdminAccountDetailFragment(
                                accounts.get(getAdapterPosition()).get_id());

                navController.navigate(action);
            });
        }
    }
}
