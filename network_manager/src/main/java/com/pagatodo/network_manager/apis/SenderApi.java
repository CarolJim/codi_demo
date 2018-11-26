package com.pagatodo.network_manager.apis;

import android.content.Context;

import com.android.volley.Request;
import com.pagatodo.network_manager.WsConsumer;
import com.pagatodo.network_manager.dtos.WSConfiguration;
import com.pagatodo.network_manager.dtos.sender_yg.results.ValidatePersonResult;
import com.pagatodo.network_manager.interfaces.IRequestResult;
import com.pagatodo.network_manager.utils.OfflineException;

import java.util.Map;

public class SenderApi extends GenericApi {

    public static void validatePerson(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 20000, headers, ValidatePersonResult.class, result);
        WsConsumer.consumeWS(context, null, WSConfiguration);
    }
}
