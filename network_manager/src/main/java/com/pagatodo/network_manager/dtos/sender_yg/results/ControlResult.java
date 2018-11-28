package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class ControlResult implements Serializable {

    private Boolean EsCliente;
    private Boolean EsUsuario;
    private Boolean RequiereActivacionSMS;

    public Boolean getEsCliente() {
        return EsCliente;
    }

    public void setEsCliente(Boolean esCliente) {
        EsCliente = esCliente;
    }

    public Boolean getEsUsuario() {
        return EsUsuario;
    }

    public void setEsUsuario(Boolean esUsuario) {
        EsUsuario = esUsuario;
    }

    public Boolean getRequiereActivacionSMS() {
        return RequiereActivacionSMS;
    }

    public void setRequiereActivacionSMS(Boolean requiereActivacionSMS) {
        RequiereActivacionSMS = requiereActivacionSMS;
    }
}
