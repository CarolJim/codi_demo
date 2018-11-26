package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class SenderGenericResult implements Serializable {

    private int IdOperacion;
    private int CodigoRespuesta;
    private int Accion;
    private String Mensaje = "";

    public SenderGenericResult() {
    }

    public SenderGenericResult(int idOperacion, String mensaje) {
        IdOperacion = idOperacion;
        Mensaje = mensaje;
    }

    public int getIdOperacion() {
        return IdOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        IdOperacion = idOperacion;
    }

    public int getCodigoRespuesta() {
        return CodigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        CodigoRespuesta = codigoRespuesta;
    }

    public int getAccion() {
        return Accion;
    }

    public void setAccion(int accion) {
        Accion = accion;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
