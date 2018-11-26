package com.pagatodo.network_manager.dtos.wallet.results;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CountrysResult {

   public String ID_Pais,IdPaisFirebase,Nombre,Valor;

    public CountrysResult() {
    }

    public CountrysResult(String ID_Pais, String idPaisFirebase, String nombre, String valor) {
        this.ID_Pais = ID_Pais;
        IdPaisFirebase = idPaisFirebase;
        Nombre = nombre;
        Valor = valor;
    }



}
