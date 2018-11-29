package com.pagatodo.network_manager.dtos.sender_yg.requests;

import java.io.Serializable;

public class SendMoneyRequest implements Serializable {

    public Data request;

    public SendMoneyRequest(int idTipoTransaccion, String referencia, Double monto, int idComercioAfectado,
                            String ticket) {
        request = new Data(idTipoTransaccion, referencia, monto, idComercioAfectado, ticket);
    }

    public SendMoneyRequest(int idTipoTransaccion, String referencia, Double monto,
                            int idComercioAfectado, String nombreBeneficiario, String concepto,
                            String referenciaNumerica, String ticket) {
        request = new Data(idTipoTransaccion, referencia, monto, idComercioAfectado, nombreBeneficiario,
                concepto, referenciaNumerica, ticket);
    }

    public int getIdTipoTransaccion() {
        return request.IdTipoTransaccion;
    }

    public String getTicket() {
        return request.Ticket;
    }

    public String getReferencia() {
        return request.Referencia;
    }

    public Double getMonto() {
        return request.Monto;
    }

    public int getIdComercioAfectado() {
        return request.IdComercioAfectado;
    }

    public String getNombreBeneficiario() {
        return request.NombreBeneficiario;
    }

    public String getConcepto() {
        return request.Concepto;
    }

    public String getReferenciaNumerica() {
        return request.ReferenciaNumerica;
    }

    class Data {

        int IdTipoTransaccion;
        String Ticket;
        String Referencia;
        Double Monto;
        int IdComercioAfectado;
        String NombreBeneficiario;
        String Concepto;
        String ReferenciaNumerica;

        public Data() {
            super();
        }

        public Data(int idTipoTransaccion, String referencia, Double monto, int idComercioAfectado,
                    String ticket) {
            super();
            this.IdTipoTransaccion = idTipoTransaccion;
            this.Ticket = ticket;
            this.Referencia = referencia;
            this.Monto = monto;
            this.IdComercioAfectado = idComercioAfectado;
            this.NombreBeneficiario = "";
            this.Concepto = "";
            this.ReferenciaNumerica = "";
        }

        public Data(int idTipoTransaccion, String referencia, Double monto,
                    int idComercioAfectado, String nombreBeneficiario, String concepto,
                    String referenciaNumerica, String ticket) {
            super();
            this.IdTipoTransaccion = idTipoTransaccion;
            this.Ticket = ticket;
            this.Referencia = referencia;
            this.Monto = monto;
            this.IdComercioAfectado = idComercioAfectado;
            this.NombreBeneficiario = nombreBeneficiario;
            this.Concepto = concepto;
            this.ReferenciaNumerica = referenciaNumerica;

        }
    }
}
