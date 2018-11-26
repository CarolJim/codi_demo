package com.pagatodo.network_manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.HttpMetric;
import com.pagatodo.network_manager.dtos.WSConfiguration;
import com.pagatodo.network_manager.interfaces.IRequestService;
import com.pagatodo.network_manager.utils.CustomJsonObjectRequest;
import com.pagatodo.network_manager.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class WsCaller implements IRequestService {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * Método para realizar la petición http segun los
     * datos de la petición pasada como parámetro.
     *
     * @param request {@link WSConfiguration}
     */
    @Override
    public void sendJsonPost(Context context, String[] pins, final WSConfiguration request) {
        HttpMetric metric = FirebasePerformance.getInstance().newHttpMetric(request.getUrlRequest(),
                request.getMethod() == Request.Method.GET ? FirebasePerformance.HttpMethod.GET : FirebasePerformance.HttpMethod.POST);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(context, pins);
        Log.d(TAG, "Request : " + request.getUrlRequest());
        if (request.getHeaders() != null && request.getHeaders().size() > 0) {
            Log.d(TAG, "Headers : ");
            for (String name : request.getHeaders().keySet()) {
                String key = name.toString();
                String value = request.getHeaders().get(name).toString();
                Log.d(TAG, key + " : " + value);
            }
        }

        if (request.getBody() != null)
            Log.d(TAG, "Body Request : " + request.getBody().toString());

        CustomJsonObjectRequest jsonRequest = new CustomJsonObjectRequest(
                request.getMethod(),
                request.getMethod() == Request.Method.POST ? request.getUrlRequest() : parseGetRequest(request.getUrlRequest(), request.getBody()),
                request.getMethod() == Request.Method.POST ? request.getBody() : null,
                response -> {
                    Log.d(TAG, "Response Success : " + response.toString());
                    if (request.getRequestResult() != null) {
                        request.getRequestResult().onSuccess(NetworkUtils.jsonToObject(response.toString(), request.getTypeResponse()));
                        metric.setResponsePayloadSize(response.toString().length());
                    }
                    metric.stop();
                },
                error -> {
                    Log.d(TAG, error.toString());
                    Log.d(TAG, "Request Failed : " + error.getMessage());
                    if (request.getRequestResult() != null) {
                        request.getRequestResult().onFailed(error.getMessage());
                    }
                    metric.stop();
                }, request.getHeaders()) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d(TAG, "Http Status : " + response.statusCode);
                metric.setHttpResponseCode(response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };
        metric.start();
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(request.getTimeOut(), 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.addToRequestQueue(jsonRequest);
    }

    private String parseGetRequest(String mUrl, JSONObject request) {
        if (request == null) {
            return mUrl;
        }
        StringBuilder stringBuilder = new StringBuilder(mUrl);
        int i = 1;
        Iterator<String> iterator = request.keys();
        String key, keyToAdd, value;
        while (iterator.hasNext()) {
            key = iterator.next();
            try {
                keyToAdd = URLEncoder.encode(key, "UTF-8");
                value = URLEncoder.encode(request.get(key).toString(), "UTF-8");
                if (i == 1) {
                    stringBuilder.append("?" + keyToAdd + "=" + value);
                } else {
                    stringBuilder.append("&" + keyToAdd + "=" + value);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }
        return stringBuilder.toString();
    }
}
