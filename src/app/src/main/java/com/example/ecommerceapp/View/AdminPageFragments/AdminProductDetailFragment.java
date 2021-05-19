package com.example.ecommerceapp.View.AdminPageFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.RealmObjects.Product;
import com.example.ecommerceapp.RealmObjects.ProductCategory;
import com.example.ecommerceapp.ViewModel.ProductListViewModel;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

public class AdminProductDetailFragment extends Fragment {

    EditText productName, productCategory, productQuantity;
    EditText productPrice, productDescription ,productImage;
    ImageButton delete, update, create;

    ProductListViewModel viewModel;

    String productId, intention;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_product_detail, container, false);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProductListViewModel.class);
        setupView(view);

        productId = AdminProductDetailFragmentArgs.fromBundle(getArguments()).getProductId();
        intention = AdminProductDetailFragmentArgs.fromBundle(getArguments()).getIntention();

        if (!productId.isEmpty()) {
            viewModel.loadProduct(requireContext(), productId);
            viewModel.getProductMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    setUpViewContent(product);
                }
            });
        }

        setupActionBar(view);

        if(intention.equals("edit")) {
            create.setVisibility(View.INVISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AdminProductDetailFragment.this.getContext())
                            .setMessage("Are you sure you want to delete this product?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    viewModel.deleteProduct(AdminProductDetailFragment.this.getContext(), productId);
                                    Navigation.findNavController(view).popBackStack();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AdminProductDetailFragment.this.getContext())
                            .setMessage("Are you sure you want to update this product?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    viewModel.updateProduct(AdminProductDetailFragment.this.getContext(), productId,
                                            productName.getText().toString(),
                                            Integer.parseInt(productQuantity.getText().toString()),
                                            Double.parseDouble(productPrice.getText().toString()),
                                            productDescription.getText().toString(),
                                            productImage.getText().toString()
                                    );
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }else{
            delete.setVisibility(View.INVISIBLE);
            update.setVisibility(View.INVISIBLE);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(AdminProductDetailFragment.this.getContext())
                            .setMessage("Are you sure you want to create this product?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    viewModel.createProduct(AdminProductDetailFragment.this.getContext(),
                                            productName.getText().toString(),
                                            Integer.parseInt(productQuantity.getText().toString()),
                                            Double.parseDouble(productPrice.getText().toString()),
                                            productDescription.getText().toString(),
                                            productImage.getText().toString(),
                                            productCategory.getText().toString()
                                    );
                                    Navigation.findNavController(view).popBackStack();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }


    private void setupActionBar(View view) {
        ActionBar actionBar = ((AppCompatActivity)requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    private void setupView(View view) {
        productName = view.findViewById(R.id.admin_product_detail_name);
        productCategory = view.findViewById(R.id.admin_product_detail_category);
        productQuantity = view.findViewById(R.id.admin_product_detail_quantity);
        productPrice = view.findViewById(R.id.admin_product_detail_price);
        productDescription = view.findViewById(R.id.admin_product_detail_description);
        productImage = view.findViewById(R.id.admin_product_detail_image_url);

        delete = view.findViewById(R.id.admin_product_detail_delete_button);
        update = view.findViewById(R.id.admin_product_detail_check_button);
        create = view.findViewById(R.id.admin_product_detail_create_button);
    }

    private void setUpViewContent(Product product) {
        if(product.getName()!=null) productName.setText(product.getName());
        if(product.getCategory().getName()!=null) productCategory.setText(product.getCategory().getName());
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productPrice.setText(String.valueOf(product.getPrice()));
        if(product.getDescription()!=null) productDescription.setText(product.getName());
        if(product.getPicture()!=null) productImage.setText(product.getPicture());


    }

}