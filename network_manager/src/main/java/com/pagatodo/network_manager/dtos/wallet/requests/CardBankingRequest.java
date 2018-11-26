package com.pagatodo.network_manager.dtos.wallet.requests;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CardBankingRequest {

    public boolean active;
    public String email;
    public String key;


    public CardBankingRequest() {
    }

    public CardBankingRequest(boolean active, String email, String key) {
        this.active = active;
        this.email = email;
        this.key = key;

    }
}
