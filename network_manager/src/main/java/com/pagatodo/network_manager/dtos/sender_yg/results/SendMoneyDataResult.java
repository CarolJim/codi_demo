package com.pagatodo.network_manager.dtos.sender_yg.results;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SendMoneyDataResult implements Serializable {
    private String NumeroAutorizacion = "";
    private Double Saldo;
    private String ClaveRastreo = "";
    private Double Comision;
    private String IdTransaccion = "";
    @SerializedName("Date")
    private String fecha;
    @SerializedName("Time")
    private String hora;

    public SendMoneyDataResult() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNumeroAutorizacion() {
        return NumeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        NumeroAutorizacion = numeroAutorizacion;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }

    public String getClaveRastreo() {
        return ClaveRastreo;
    }

    public void setClaveRastreo(String claveRastreo) {
        ClaveRastreo = claveRastreo;
    }

    public Double getComision() {
        return Comision;
    }

    public void setComision(Double comision) {
        Comision = comision;
    }

    public String getIdTransaccion() {
        return IdTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        IdTransaccion = idTransaccion;
    }
}
