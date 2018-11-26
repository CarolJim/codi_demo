package com.pagatodo.network_manager.dtos.wallet.results;

import java.io.Serializable;
import java.util.ArrayList;

public class SendSmsResult implements Serializable {

    private String message;
    private boolean success;
    private ArrayList errorList;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList getErrorList() {
        return errorList;
    }
}
