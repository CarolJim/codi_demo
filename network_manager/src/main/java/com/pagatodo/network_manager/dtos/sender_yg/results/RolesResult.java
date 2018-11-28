package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public  class RolesResult implements Serializable {
    private int IdRol;
    private String NombreRol = "";

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int idRol) {
        IdRol = idRol;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String nombreRol) {
        NombreRol = nombreRol;
    }
}
