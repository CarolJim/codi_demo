package com.pagatodo.network_manager.apis;

import com.pagatodo.network_manager.utils.RequestHeaders;

import java.util.HashMap;
import java.util.Map;

public class GenericApi {

    public static Map<String, String> getHeadersYaGanaste() {
        Map<String, String> headersYaGanaste = new HashMap<>();
        headersYaGanaste.put(RequestHeaders.IdDispositivo, "2");
        String userName = RequestHeaders.getUsername().isEmpty() ?
                "correo@correo.correo" : RequestHeaders.getUsername();
        headersYaGanaste.put(RequestHeaders.NombreUsuario, userName);
        headersYaGanaste.put(RequestHeaders.IdComponente, "1");
        return headersYaGanaste;
    }

    public static Map<String, String> getHeadersAdq() {
        Map<String, String> headersAdq = new HashMap<>();
        headersAdq.put("Version", "1.0.7");
        headersAdq.put("SO", "Android");
        return headersAdq;
    }

    public static Map<String, String> getHeadersLoyalty() {
        Map<String, String> headersLoy = new HashMap<>();
        headersLoy.put("Content-Type", "application/json");
        headersLoy.put("idPrograma", "43");
        headersLoy.put("version", "1");
        headersLoy.put("so", "Android");
        headersLoy.put("UDID", "PRUEBAPOSTMAN");



        //headersSaldo.put("idUsuario",
          //      App.getPreferences().loadData(App.getContext().
            //            getResources().getString(R.string.prf_user_aeropostale)));
        ///headersSaldo.put("tokenUsuario", App.getPreferences().
        //        loadData(App.getContext().getResources().getString(R.string.prf_token_aeropostale)));
        //headersSaldo.put("SO", "Android");
        return headersLoy;
    }

    public static Map<String,String> getHeadersSucursales(){
        Map<String,String> headersSucs = new HashMap<>();
        headersSucs.put("Content-Type","application/json; charset=utf-8");

        return headersSucs;
    }
}
