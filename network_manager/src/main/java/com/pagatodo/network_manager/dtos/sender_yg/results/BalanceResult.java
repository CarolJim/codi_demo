package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class BalanceResult extends SenderGenericResult implements Serializable {

    private DataSaldo Data;

    public String getSaldo() {
        return Data.Saldo;
    }

    public void setSaldo(String saldo) {
        Data.Saldo = saldo;
    }

    public class DataSaldo {
        String Saldo;
    }
}
