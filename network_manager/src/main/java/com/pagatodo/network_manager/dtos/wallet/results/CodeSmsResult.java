package com.pagatodo.network_manager.dtos.wallet.results;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class CodeSmsResult {

    public String Code, CreationDate, ExpirationDate;
    public boolean Linked;
    public Long Timestamp;

    public CodeSmsResult() {
    }

    public CodeSmsResult(String code, String creationDate, String expirationDate, boolean linked) {
        this.Code = code;
        this.CreationDate = creationDate;
        this.ExpirationDate = expirationDate;
        this.Linked = linked;
    }

    public CodeSmsResult(String code, String creationDate, String expirationDate, boolean linked, Long dateburn) {
        this.Code = code;
        this.CreationDate = creationDate;
        this.ExpirationDate = expirationDate;
        this.Linked = linked;
        this.Timestamp = dateburn;
    }
}
