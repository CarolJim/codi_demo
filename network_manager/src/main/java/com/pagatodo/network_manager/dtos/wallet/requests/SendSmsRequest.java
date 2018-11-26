package com.pagatodo.network_manager.dtos.wallet.requests;

public class SendSmsRequest {

    private String phone;

    public SendSmsRequest(String phone) {
        this.phone = phone;
    }
}
