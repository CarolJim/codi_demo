package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;
import java.util.ArrayList;

public class UyuSenderResult implements Serializable {

    private ArrayList<UyuAccountResult> Cuentas;

    public UyuSenderResult(){
        Cuentas = new ArrayList<>();
    }

    public ArrayList<UyuAccountResult> getCuentas() {
        return Cuentas;
    }

    public void setCuentas(ArrayList<UyuAccountResult> cuentas) {
        Cuentas = cuentas;
    }
}
