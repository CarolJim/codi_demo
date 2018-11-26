package com.pagatodo.network_manager.dtos.wallet.results;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CountiesResult {


    public String ID_EntidadNacimiento,ID_Estado,ID_Estatus,IdEstadoFirebase,Nombre,clave;

    public CountiesResult() {
    }

    public CountiesResult(String ID_EntidadNacimiento, String nombre, String clave) {
        this.ID_EntidadNacimiento = ID_EntidadNacimiento;
        Nombre = nombre;
        this.clave = clave;
    }

    public CountiesResult(String ID_EntidadNacimiento, String ID_Estado, String ID_Estatus, String idEstadoFirebase, String nombre, String clave) {
        this.ID_EntidadNacimiento = ID_EntidadNacimiento;
        this.ID_Estado = ID_Estado;
        this.ID_Estatus = ID_Estatus;
        IdEstadoFirebase = idEstadoFirebase;
        Nombre = nombre;
        this.clave = clave;
    }
}
