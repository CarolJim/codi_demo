package com.pagatodo.network_manager.dtos.sttngs;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class CardResult {

    private String CS;
    private String IMG;
    private String LI;
    private List<BtnResult> Btns = new ArrayList<>();


    public CardResult() {

    }

    public List<BtnResult>  getBtns() {
        return Btns;
    }

    public void setBtns(List<BtnResult> btns) {
        Btns = btns;
    }

    public String getCS() {
        return CS;
    }

    public void setCS(String CS) {
        this.CS = CS;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getLI() {
        return LI;
    }

    public void setLI(String LI) {
        this.LI = LI;
    }

}
