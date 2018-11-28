package com.pagatodo.network_manager.dtos.sender_yg.results;

public class LogInResult extends SenderGenericResult {

    private UyuLogInResult Data;

    public LogInResult() {
        Data = new UyuLogInResult();
    }

    public UyuLogInResult getData() {
        return Data;
    }

    public void setData(UyuLogInResult data) {
        Data = data;
    }
}