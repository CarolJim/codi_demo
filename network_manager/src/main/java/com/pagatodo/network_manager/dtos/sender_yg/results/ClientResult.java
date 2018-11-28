package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class ClientResult implements Serializable {

    private Boolean ConCuenta;
    private String Nombre = "";
    private String PrimerApellido = "";
    private String SegundoApellido = "";

    public Boolean getConCuenta() {
        return ConCuenta;
    }

    public void setConCuenta(Boolean conCuenta) {
        ConCuenta = conCuenta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }
}
