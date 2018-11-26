package com.pagatodo.network_manager.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class CustomJsonObjectRequest extends JsonObjectRequest {

    private Map<String, String> headers;

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     * @param jsonRequest   A {@link JSONObject} to post with the request. Null is allowed and
     *                      indicates no parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     * @param headers       Headers to add in the peticion
     */
    public CustomJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener, Map<String, String> headers) {
        super(method, url, jsonRequest, listener, errorListener);
        this.headers = headers;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers != null) {
            return headers;
        }
        return super.getHeaders();
    }
}
