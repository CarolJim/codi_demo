package com.pagatodo.network_manager.dtos.wallet.results;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class LoyaltyWalletResult {

    private boolean active;
    private String email,key;

    public LoyaltyWalletResult() {
    }

    public LoyaltyWalletResult(boolean active, String email, String key) {
        this.active = active;
        this.email = email;
        this.key = key;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
