package com.pagatodo.network_manager.interfaces;

/**
 * Created by ozunigag
 */
public interface IRequestResult {

    void onSuccess(Object data);

    void onFailed(Object error);
}
