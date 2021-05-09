package com.example.e_commerce_app.view.detail.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.presenter.detail.commom.PresenterLogicDetailCommom;
import com.example.e_commerce_app.view.detail.commom.IViewDetailCommom;


public class FragmentDetail extends Fragment implements
        IViewDetailCommom {
    private static final String TAG = FragmentDetail.class.getSimpleName();
    TextView tvDesc;
    int id_product = 0;
    PresenterLogicDetailCommom presenterLogicCommom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        if (getActivity().getIntent() != null) {
            id_product = getActivity().getIntent().getIntExtra("id_product", 0);
        }
        //Init Presenter
        presenterLogicCommom = new PresenterLogicDetailCommom(this);
        //Init View
        initView(view);
        presenterLogicCommom.fillData(id_product);
        return view;
    }

    private void initView(View view) {
        tvDesc = view.findViewById(R.id.tvDesc);
    }

    @Override
    public void fillData(Product product) {
        tvDesc.setText(product.getDesc());
    }

    @Override
    public void showError(String message) {

    }
}