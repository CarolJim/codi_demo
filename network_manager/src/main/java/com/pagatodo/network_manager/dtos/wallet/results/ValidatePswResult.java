package com.pagatodo.network_manager.dtos.wallet.results;

import java.io.Serializable;
import java.util.List;

public class ValidatePswResult implements Serializable {

    private String message;
    private boolean success;
    private List<String> errorList;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}
