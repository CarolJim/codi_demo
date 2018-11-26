package com.pagatodo.network_manager.interfaces;

/**
 * Created by ozunigag
 */
public interface IRequestResult<Data extends Object> {

    void onSuccess(Data data);

    void onFailed(Data error);
}
