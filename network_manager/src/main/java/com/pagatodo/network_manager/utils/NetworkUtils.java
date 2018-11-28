package com.pagatodo.network_manager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

public class NetworkUtils {

    public static final int CODE_OK = 0;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo network_info = cm.getActiveNetworkInfo();
        return network_info != null && network_info.isConnected() ? true : false;
    }

    private static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }

    public static Object jsonToObject(String json, Type type) {
        Object o = null;
        try {
            o = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss")
                    .create().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            o = null;
        }
        return o;
    }

    public static JSONObject madeJsonFromObject(Object oRequest) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String tmp = gson.toJson(oRequest);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(tmp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
