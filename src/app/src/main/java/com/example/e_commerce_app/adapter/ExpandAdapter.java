package com.example.e_commerce_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_commerce_app.R;
import com.example.e_commerce_app.model.ModelProduct;
import com.example.e_commerce_app.object.ProductType;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {
    List<ProductType> subList;
    Context context;
    int id_group;

    public ExpandAdapter(Context context, List<ProductType> subList, int id_group) {
        this.subList = subList;
        this.context = context;
        ModelProduct modelProduct = new ModelProduct();
        subList = modelProduct.productTypes(id_group);

    }

    @Override
    public int getGroupCount() {
        return subList.size();
    }

    @Override
    public int getChildrenCount(int positionGroup) {
        return 0;
    }

    @Override
    public Object getGroup(int positionGroup) {
        return subList.get(positionGroup);
    }

    @Override
    public Object getChild(int positionGroup, int positionSubGroup) {
        return 0;
    }

    @Override
    public long getGroupId(int positionGroup) {
        return subList.get(positionGroup).getId();
    }

    @Override
    public long getChildId(int positionGroup, int positionSubGroup) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int positionGroup, boolean isExpanded, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View groupView = layoutInflater.inflate(R.layout.item_product_type_expand_list, viewGroup,false);
        TextView name = groupView.findViewById(R.id.name);
        ImageView img = groupView.findViewById(R.id.img);
        name.setText(subList.get(positionGroup).getName_type());
        return groupView;
    }

    @Override
    public View getChildView(int positionGroup, int positionSubGroup, boolean isExpanded, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
