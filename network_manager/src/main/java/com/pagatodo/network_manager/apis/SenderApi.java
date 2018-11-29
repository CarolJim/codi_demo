package com.pagatodo.network_manager.apis;

import android.content.Context;

import com.android.volley.Request;
import com.pagatodo.network_manager.WsConsumer;
import com.pagatodo.network_manager.dtos.WSConfiguration;
import com.pagatodo.network_manager.dtos.sender_yg.results.BalanceResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.LogInResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.LogOutResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.SendMoneyResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult;
import com.pagatodo.network_manager.dtos.sender_yg.results.ValidatePersonResult;
import com.pagatodo.network_manager.interfaces.IRequestResult;
import com.pagatodo.network_manager.utils.OfflineException;
import com.pagatodo.network_manager.utils.RequestHeaders;

import java.util.Map;

public class SenderApi extends GenericApi {

    private static final String PIN_ADTVO = "275a28946f92da9acab52475df6ec73a10a40811";
    private static final String PIN_TRANS = "275a28946f92da9acab52475df6ec73a10a40811";

    public static void validatePerson(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 20000, headers, ValidatePersonResult.class, result);
        WsConsumer.consumeWS(context, null, WSConfiguration);
    }

    public static void logIn(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenDispositivo, RequestHeaders.getTokendevice());
        if (!RequestHeaders.getTokenauth().isEmpty())//Si ya se almaceno el tokenAuth, se envia en el login
            headers.put(RequestHeaders.TokenAutenticacion, RequestHeaders.getTokenauth());
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 25000, headers, LogInResult.class, result);
        WsConsumer.consumeWS(context, new String[]{PIN_ADTVO}, WSConfiguration);
    }

    public static void logOut(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.GET, url,
                request, 25000, headers, LogOutResult.class, result);
        WsConsumer.consumeWS(context, new String[]{PIN_ADTVO}, WSConfiguration);
    }

    public static void consultarSaldo(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.GET, url,
                request, 25000, headers, BalanceResult.class, result);
        WsConsumer.consumeWS(context, new String[]{PIN_TRANS}, WSConfiguration);
    }

    public static void consultarMovimientos(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 25000, headers, MovementsResult.class, result);
        WsConsumer.consumeWS(context, new String[]{PIN_ADTVO}, WSConfiguration);
    }

    public static void enviarDinero(Context context, Object request, IRequestResult result, String url) throws OfflineException {
        Map<String, String> headers = getHeadersYaGanaste();
        headers.put(RequestHeaders.TokenSesion, RequestHeaders.getTokensesion());
        headers.put(RequestHeaders.IdOperacion, "1");
        WSConfiguration WSConfiguration = WsConsumer.createRequest(Request.Method.POST, url,
                request, 25000, headers, SendMoneyResult.class, result);
        WsConsumer.consumeWS(context, new String[]{PIN_TRANS}, WSConfiguration);
    }
}
