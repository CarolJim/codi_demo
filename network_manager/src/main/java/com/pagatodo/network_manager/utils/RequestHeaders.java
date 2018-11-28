package com.pagatodo.network_manager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pagatodo.network_manager.R;

public class RequestHeaders {

    public static String IdOperacion = "IdOperacion";
    public static String IdComponente = "IdComponente";
    public static String NombreUsuario = "NombreUsuario";
    public static String IdDispositivo = "IdDispositivo";
    public static String TokenAutenticacion = "TokenAutenticacion";
    public static String TokenDispositivo = "TokenDispositivo";
    public static String TokenSesion = "TokenSesion";
    public static String IdCuenta = "IdCuenta";

    public static String IdTransaccionFreja = "IdTransaccionFreja";
    public static String TokenFreja = "TokenFreja";

    /* Sb Headers */
    public static String TokenSeguridad = "tokenSeguridad";
    public static String numeroMiembro = "numeroMiembro";

    /* Adq Headers */
    public static String IdCuentaAdq = "ID";
    public static String TokenAdq = "Token";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static String getIdDispositivo() {
        return IdDispositivo;
    }

    public static void setIdDispositivo(String idDispositivo) {
        IdDispositivo = idDispositivo;
    }

    public static void initHeaders(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(ctx.getString(R.string.prf_wallet_headers),
                Context.MODE_PRIVATE);
    }

    public static String getOperation() {
        return sharedPreferences.getString(IdOperacion, "");
    }

    public static void setOperation(String operation) {
        editor = sharedPreferences.edit();
        editor.putString(IdOperacion, operation);
        editor.apply();
    }

    public static String getComponent() {
        return sharedPreferences.getString(IdComponente, "");
    }

    public static void setComponent(String component) {
        editor = sharedPreferences.edit();
        editor.putString(IdComponente, component);
        editor.apply();
    }

    public static String getUsername() {
        return sharedPreferences.getString(NombreUsuario, "");
    }

    public static void setUsername(String username) {
        editor = sharedPreferences.edit();
        editor.putString(NombreUsuario, username);
        editor.apply();
    }

    public static String getDevice() {
        return sharedPreferences.getString(IdDispositivo, "");
    }

    public static void setDevice(String device) {
        editor = sharedPreferences.edit();
        editor.putString(IdDispositivo, device);
        editor.apply();
    }

    public static String getTokenauth() {
        return sharedPreferences.getString(TokenAutenticacion, "");
    }

    public static void setTokenauth(String tokenauth) {
        editor = sharedPreferences.edit();
        editor.putString(TokenAutenticacion, tokenauth);
        editor.apply();
    }

    public static String getTokendevice() {
        return sharedPreferences.getString(TokenDispositivo, "");
    }

    public static void setTokendevice(String tokendevice) {
        editor = sharedPreferences.edit();
        editor.putString(TokenDispositivo, tokendevice);
        editor.apply();
    }

    public static String getTokensesion() {
        return sharedPreferences.getString(TokenSesion, "");
    }

    /**
     * starbucks
     *
     * @return
     */
    public static String getTokenSeguridad() {
        return sharedPreferences.getString(TokenSeguridad, "");
    }

    public static String getNumeroMiembro() {
        return sharedPreferences.getString(numeroMiembro, "");
    }

    ////

    public static void setTokensesion(String tokensesion) {
        editor = sharedPreferences.edit();
        editor.putString(TokenSesion, tokensesion);
        editor.apply();
    }

    public static String getTokenFreja() {
        return sharedPreferences.getString(TokenFreja, "");
    }

    public static void setTokenFreja(String tokenFreja) {
        editor = sharedPreferences.edit();
        editor.putString(TokenFreja, tokenFreja);
        editor.apply();
    }

    public static String getIdTransaccionFreja() {
        return sharedPreferences.getString(IdTransaccionFreja, "");
    }

    public static void setIdTransaccionFreja(String transaccionFreja) {
        editor = sharedPreferences.edit();
        editor.putString(IdTransaccionFreja, transaccionFreja);
        editor.apply();
    }

    public static String getIdCuenta() {
        return sharedPreferences.getString(IdCuenta, "");
    }

    public static void setIdCuenta(String idCuenta) {
        editor = sharedPreferences.edit();
        editor.putString(IdCuenta, idCuenta);
        editor.apply();
    }

    public static String getTokenAdq() {
        return sharedPreferences.getString(TokenAdq, "");
    }

    public static void setTokenAdq(String tokenAdq) {
        editor = sharedPreferences.edit();
        editor.putString(TokenAdq, tokenAdq);
        editor.apply();
    }

    public static String getIdCuentaAdq() {
        return sharedPreferences.getString(IdCuentaAdq, "");
    }

    public static void setIdCuentaAdq(String idCuentaAdq) {
        editor = sharedPreferences.edit();
        editor.putString(IdCuentaAdq, idCuentaAdq);
        editor.apply();
    }

    public static void clearPreferences() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return;
    }
}
