package com.example.e_commerce_app.model;

import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.connect.ConnectAPI;
import com.example.e_commerce_app.helper.ParseHelper;
import com.example.e_commerce_app.object.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelFavorite {
    /**
     * controller: Product
     * action: group
     * @return
     */
    public List<Product> favorites(String token, int id_user) {
        String data = "";
        int status = 0;

        List<Product> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.FAVORITE);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.GROUP);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("token", token);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("id_user", String.valueOf(id_user));
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
