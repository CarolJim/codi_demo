package com.pagatodo.network_manager.interfaces;

import android.content.Context;

import com.pagatodo.network_manager.dtos.WSConfiguration;

public interface IRequestService {

    void sendJsonPost(Context context, String[] pins, final WSConfiguration request);

}
