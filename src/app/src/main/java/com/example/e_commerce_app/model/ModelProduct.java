package com.example.e_commerce_app.model;

import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.connect.ConnectAPI;
import com.example.e_commerce_app.helper.ParseHelper;
import com.example.e_commerce_app.object.Product;
import com.example.e_commerce_app.object.ProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelProduct {
    /**
     * controller: ProductTpe
     * action: group
     * @return
     */
    public List<ProductType> productTypes(int id) {
        String data = "";
        int status = 0;

        List<ProductType> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.PRODUCT_TYPE);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.GROUP);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("id_group", String.valueOf(id));
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            list = ParseHelper.parseProductTypes(data, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * controller: Product
     * action: group
     * @return
     */
    public List<Product> products(int id_product_type, int limit) {
        String data = "";
        int status = 0;

        List<Product> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.PRODUCT);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.GROUP);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("id_product_type", String.valueOf(id_product_type));
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("limit", String.valueOf(limit));
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            list = ParseHelper.parseProducts(data, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }
}
