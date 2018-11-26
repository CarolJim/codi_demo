package com.pagatodo.network_manager.dtos.wallet.results;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OptionMenuResult {

    private String ID;
    private String IMG;
    private String Name;

    public OptionMenuResult() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
