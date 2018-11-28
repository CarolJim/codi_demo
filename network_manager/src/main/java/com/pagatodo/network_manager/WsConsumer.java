package com.pagatodo.network_manager;

import android.content.Context;
import android.util.Log;

import com.pagatodo.network_manager.dtos.WSConfiguration;
import com.pagatodo.network_manager.interfaces.IRequestResult;
import com.pagatodo.network_manager.utils.NetworkUtils;
import com.pagatodo.network_manager.utils.OfflineException;

import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WsConsumer {

    /**
     * Método para consumir un ws a partir de los parámetros enviados
     */
    public static void consumeWS(Context context, String[] pins, WSConfiguration WSConfiguration) throws OfflineException {
        if (NetworkUtils.isOnline(context)) {
            WsCaller wsCaller = new WsCaller();
            if (pins == null) {
                disableSSLValidation();
            }
            wsCaller.sendJsonPost(context, pins, WSConfiguration);
        } else {
            throw new OfflineException();
        }
    }

    /**
     * Método para crear el {@link WSConfiguration} al servicio web
     *
     * @param httpMethod    enum que indica el tipo de método
     * @param urlService    {@link String} url del servicio a consumir
     * @param result {@link IRequestResult} interface para obtener el resultado
     *                      de la petición.
     * @return WsRequest petición para el servicio
     */
    public static WSConfiguration createRequest(int httpMethod, String urlService, Object oRequest, int timeout,
                                                Map<String, String> headers, Type responseType, IRequestResult result) {
        WSConfiguration request = new WSConfiguration();
        request.setHeaders(headers);
        request.setMethod(httpMethod);
        request.setUrlRequest(urlService);
        request.setBody(NetworkUtils.madeJsonFromObject(oRequest));
        request.setRequestResult(result);
        request.setTypeResponse(responseType);
        request.setTimeOut(timeout);

        return request;
    }

    /**
     * Create a trust manager for Web Services that include SSL Certificates
     * WARNING: ONLY USED WITH WS THAT AREN'T PART OF BANKING PROJECT
     */
    private static void disableSSLValidation() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            Log.d("test_disable", "OK========");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
