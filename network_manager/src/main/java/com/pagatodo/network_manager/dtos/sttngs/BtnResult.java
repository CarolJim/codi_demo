package com.pagatodo.network_manager.dtos.sttngs;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BtnResult {

    private String ID,IMG,Name,Url;
    private int Order;

    public BtnResult() {
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }
}
