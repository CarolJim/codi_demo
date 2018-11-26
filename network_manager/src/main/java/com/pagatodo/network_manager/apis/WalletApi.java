package com.pagatodo.network_manager.apis;

import android.content.Context;

import com.android.volley.Request;
import com.pagatodo.network_manager.R;
import com.pagatodo.network_manager.WsConsumer;
import com.pagatodo.network_manager.dtos.WSConfiguration;
import com.pagatodo.network_manager.dtos.wallet.results.SendSmsResult;
import com.pagatodo.network_manager.dtos.wallet.results.ValidatePswResult;
import com.pagatodo.network_manager.interfaces.IRequestResult;
import com.pagatodo.network_manager.utils.OfflineException;

public class WalletApi extends GenericApi {

    public static void sendSmsFbs(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 20000, null, SendSmsResult.class, result);
        WsConsumer.consumeWS(context, null, WSConfiguration);
    }

    public static void validatePswrd(Context context, Object request, IRequestResult result, String url) throws  OfflineException{
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 10000, null, ValidatePswResult.class, result);
        WsConsumer.consumeWS(context, null, WSConfiguration);
    }
}
