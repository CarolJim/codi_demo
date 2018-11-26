package com.pagatodo.network_manager.dtos.wallet.results;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Map;

@IgnoreExtraProperties
public class WalletsResult implements Serializable{
    private Map<String, WalletResult> Banking;
    private Map<String, WalletResult> Loyalty;

    public WalletsResult() {
    }

    public Map<String, WalletResult> getBanking() {
        return Banking;
    }

    public void setBanking(Map<String, WalletResult> banking) {
        Banking = banking;
    }

    public Map<String, WalletResult> getLoyalty() {
        return Loyalty;
    }

    public void setLoyalty(Map<String, WalletResult> loyalty) {
        Loyalty = loyalty;
    }
}
