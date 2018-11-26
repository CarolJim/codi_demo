package com.pagatodo.network_manager.dtos.wallet.results;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RegisterUserResult {

    public String Nm, LtsNm, SrNm, Gnr, BrthDt, BrthPlc, Ntv, Curp,Country;
    public boolean Mx = true;
}