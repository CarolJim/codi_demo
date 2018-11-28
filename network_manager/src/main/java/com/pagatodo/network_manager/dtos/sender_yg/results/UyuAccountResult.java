package com.pagatodo.network_manager.dtos.sender_yg.results;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class UyuAccountResult implements Serializable {

    private String CLABE = "";
    private String Cuenta = "";
    private int IdCuenta = 0;
    private int IdUsuario;
    private int NumeroCliente;

    private ArrayList<UyuCardResult> Tarjetas = new ArrayList<>();

    @SerializedName("Telefono")
    private String telefono;

    public String getCLABE() {
        return CLABE;
    }

    public void setCLABE(String CLABE) {
        this.CLABE = CLABE;
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }


    public String getCuenta() {
        return Cuenta;
    }

    public void setCuenta(String cuenta) {
        Cuenta = cuenta;
    }

    public ArrayList<UyuCardResult> getTarjetas() {
        return Tarjetas;
    }

    public void setTarjetas(ArrayList<UyuCardResult> tarjetas) {
        Tarjetas = tarjetas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getNumeroCliente() {
        return NumeroCliente;
    }

    public void setNumeroCliente(int numeroCliente) {
        NumeroCliente = numeroCliente;
    }
}
