package com.example.e_commerce_app.model;

import com.example.e_commerce_app.common.Common;
import com.example.e_commerce_app.connect.ConnectAPI;
import com.example.e_commerce_app.helper.ParseHelper;
import com.example.e_commerce_app.object.Banner;
import com.example.e_commerce_app.object.GroupProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelHome {
    /**
     * controller: GroupProductType
     * action: index
     * @return
     */
    public List<GroupProductType> groupProductTypes() {
        String data = "";
        int status = 0;

        List<GroupProductType> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.GROUP_PRODUCT_TYPE);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.INDEX);
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            list = ParseHelper.parseGroupProductTypes(data, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * controller: Banner
     * action: index
     * @return
     */
    public List<Banner> banners() {
        String data = "";
        int status = 0;

        List<Banner> list = new ArrayList<>();

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.BANNER);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.INDEX);
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API, attrs);
        connect.execute();
        try {
            data = connect.get().get(0);
            status = Integer.parseInt(connect.get().get(1));
            list = ParseHelper.parseBanners(data, status);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * controller: User
     * action:
     * @return
     */

    public int logout(String token) {
        int status = 0;

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> attr = new HashMap<>();

        attr.put("controller", Common.USER);
        attrs.add(attr);

        attr = new HashMap<>();
        attr.put("action", Common.LOGOUT);
        attrs.add(attr);

        ConnectAPI connect = new ConnectAPI(Common.URL_API_TOKEN + token, attrs);
        connect.execute();
        try {
            status = Integer.parseInt(connect.get().get(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return status;
    }
}

