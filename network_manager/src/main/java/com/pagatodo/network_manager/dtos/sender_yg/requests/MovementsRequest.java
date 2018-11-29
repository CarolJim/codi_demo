package com.pagatodo.network_manager.dtos.sender_yg.requests;

import java.io.Serializable;

public class MovementsRequest implements Serializable {

    private Data request;

    public MovementsRequest() {
        request = new Data();
    }

    public String getMes() {
        return request.Mes;
    }

    public void setMes(String mes) {
        request.Mes = mes;
    }

    public String getAnio() {
        return request.Anio;
    }

    public void setAnio(String anio) {
        request.Anio = anio;
    }

    public String getIdMovimiento() {
        return request.IdMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        request.IdMovimiento = idMovimiento;
    }

    public String getDireccion() {
        return request.Direccion;
    }

    public void setDireccion(String direccion) {
        request.Direccion = direccion;
    }

    public class Data {
        public String Mes;
        public String Anio = "";
        public String IdMovimiento = "";
        public String Direccion = "";
    }
}
