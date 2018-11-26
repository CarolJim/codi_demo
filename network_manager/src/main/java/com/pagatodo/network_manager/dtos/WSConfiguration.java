package com.pagatodo.network_manager.dtos;


import com.pagatodo.network_manager.interfaces.IRequestResult;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Create by ozunigag
 */
public class WSConfiguration {

    private int method, timeOut;
    private Map<String, String> headers;
    private String urlRequest;
    private JSONObject body;
    private IRequestResult requestResult;
    private Type typeResponse;

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }

    public IRequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(IRequestResult requestResult) {
        this.requestResult = requestResult;
    }

    public Type getTypeResponse() {
        return typeResponse;
    }

    public void setTypeResponse(Type typeResponse) {
        this.typeResponse = typeResponse;
    }
}
