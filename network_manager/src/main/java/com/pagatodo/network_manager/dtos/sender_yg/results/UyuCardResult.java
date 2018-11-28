package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class UyuCardResult implements Serializable {
    private Boolean AsignoNip;
    private String Numero = "";

    public Boolean getAsignoNip() {
        return AsignoNip;
    }

    public void setAsignoNip(Boolean asignoNip) {
        AsignoNip = asignoNip;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }
}
