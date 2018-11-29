package com.pagatodo.network_manager.dtos.sender_yg.results;

import com.google.gson.annotations.SerializedName;

public class SendMoneyResult extends SenderGenericResult {
    @SerializedName("Data")
    private SendMoneyDataResult Data;

    public SendMoneyResult() {
        Data = new SendMoneyDataResult();
    }

    public SendMoneyDataResult getData() {
        return Data;
    }

    public void setData(SendMoneyDataResult data) {
        Data = data;
    }
}