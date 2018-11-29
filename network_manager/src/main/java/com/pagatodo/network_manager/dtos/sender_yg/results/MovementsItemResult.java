package com.pagatodo.network_manager.dtos.sender_yg.results;

import java.io.Serializable;

public class MovementsItemResult implements Serializable {

    private String IdMovimiento = "";
    private int TipoMovimiento;
    private String Descripcion = "";
    private String FechaMovimiento = "";
    private String HoraMovimiento = "";
    private String Concepto = "";
    private String Detalle = "";
    private Double Importe;
    private Double Comision;
    private Double IVA;
    private Double Total;
    private String Beneficiario = "";
    private String Referencia = "";
    private String ReferenciaNum = "";
    private String ClaveRastreo = "";
    private String NumAutorizacion = "";
    private String URLImagen = "";
    private int IdComercio;
    private String Comercio = "";
    private int IdTipoTransaccion;
    private Double Compra;
    private String NumAutPTH = "";
    private String ColorMarcaComercio = "";

    public MovementsItemResult() {
    }


    public String getIdMovimiento() {
        return IdMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        IdMovimiento = idMovimiento;
    }

    public int getTipoMovimiento() {
        return TipoMovimiento;
    }

    public void setTipoMovimiento(int tipoMovimiento) {
        TipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFechaMovimiento() {
        return FechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        FechaMovimiento = fechaMovimiento;
    }

    public String getHoraMovimiento() {
        return HoraMovimiento;
    }

    public void setHoraMovimiento(String horaMovimiento) {
        HoraMovimiento = horaMovimiento;
    }

    public String getConcepto() {
        return Concepto;
    }

    public void setConcepto(String concepto) {
        Concepto = concepto;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public Double getImporte() {
        return Importe;
    }

    public void setImporte(Double importe) {
        Importe = importe;
    }

    public Double getComision() {
        return Comision;
    }

    public void setComision(Double comision) {
        Comision = comision;
    }

    public Double getIVA() {
        return IVA;
    }

    public void setIVA(Double IVA) {
        this.IVA = IVA;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getBeneficiario() {
        return Beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        Beneficiario = beneficiario;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getReferenciaNum() {
        return ReferenciaNum;
    }

    public void setReferenciaNum(String referenciaNum) {
        ReferenciaNum = referenciaNum;
    }

    public String getClaveRastreo() {
        return ClaveRastreo;
    }

    public void setClaveRastreo(String claveRastreo) {
        ClaveRastreo = claveRastreo;
    }

    public String getNumAutorizacion() {
        return NumAutorizacion;
    }

    public void setNumAutorizacion(String numAutorizacion) {
        NumAutorizacion = numAutorizacion;
    }

    public String getURLImagen() {
        return URLImagen;
    }

    public void setURLImagen(String URLImagen) {
        this.URLImagen = URLImagen;
    }

    public int getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(int idComercio) {
        IdComercio = idComercio;
    }

    public String getComercio() {
        return Comercio;
    }

    public void setComercio(String comercio) {
        Comercio = comercio;
    }

    public int getIdTipoTransaccion() {
        return IdTipoTransaccion;
    }

    public void setIdTipoTransaccion(int idTipoTransaccion) {
        IdTipoTransaccion = idTipoTransaccion;
    }

    public Double getCompra() {
        return Compra;
    }

    public void setCompra(Double compra) {
        Compra = compra;
    }

    public String getNumAutPTH() {
        return NumAutPTH;
    }

    public void setNumAutPTH(String numAutPTH) {
        NumAutPTH = numAutPTH;
    }

    public String getColorMarcaComercio() {
        return ColorMarcaComercio;
    }

    public void setColorMarcaComercio(String colorMarcaComercio) {
        ColorMarcaComercio = colorMarcaComercio;
    }
}
