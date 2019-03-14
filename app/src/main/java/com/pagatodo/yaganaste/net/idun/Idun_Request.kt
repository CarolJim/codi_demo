package com.pagatodo.yaganaste.net.idun

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class CollectiveWireTranfer_Request(
        @SerializedName("account")          val account: Long,
        @SerializedName("reference")        val reference: String,
        @SerializedName("amount")           val amount: Double,
        @SerializedName("concept")          val concept: String,
        @SerializedName("bank")             val bank: String,
        @SerializedName("beneficiary")      val beneficiary: String,
        @SerializedName("numericReference") val numericReference: Long,
        @SerializedName("type")             val type: Int,
        @SerializedName("referenceType")    val referenceType: String,
        @SerializedName("phonePayer")       val phonePayer: String,
        @SerializedName("digitPayer")       val digitPayer: Long,
        @SerializedName("phoneBeneficiary") val phoneBeneficiary: String,
        @SerializedName("digitBeneficiary") val digitBeneficiary: Long,
        @SerializedName("numberCollectSpei") val numberCollectSpei: String,
        @SerializedName("certificateSerie") val certificateSerie: String
        //Objetos para pago 22
){
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

class CollectiveWireTranferNP_Request(
        @SerializedName("account")          val account: Long, //Cuenta Beneficiario
        @SerializedName("reference")        val reference: String,
        @SerializedName("amount")           val amount: Double, //Monto
        @SerializedName("concept")          val concept: String, //Concepto
        @SerializedName("bank")             val bank: String, // ***¿Banco Beneficiario?
        @SerializedName("beneficiary")      val beneficiary: String, //Nombre beneficiario
        @SerializedName("numericReference") val numericReference: Long, //Referencia Numérica
        @SerializedName("type")             val type: Int, //Tipo de pago SPEI
        @SerializedName("typeReference")    val typeReference: String,
        @SerializedName("phonePayer")       val phonePayer: String, //Número de Celular Cliente
        @SerializedName("digitPayer")       val digitPayer: Long,
        @SerializedName("phoneBeneficiary") val phoneBeneficiary: String,
        @SerializedName("digitBeneficiary") val digitBeneficiary: Long,
        @SerializedName("numberCollectSpei") val numberCollectSpei: String,
        @SerializedName("certificateSerie") val certificateSerie: String,
        //Objetos para pago 22
        @SerializedName("payDate") val payDate: String, //Fecha y Hora Límite de Pago
        @SerializedName("requestDate") val requestDate: String, //Fecha y Hora de Solicitud
        @SerializedName("comision") val comision: Int, //Pago de la comisión por la transferencia
        @SerializedName("beneficiary2") val beneficiary2: String, //Nombre beneficiario 2
        @SerializedName("typeAccountBeneficiary2") val typeAccountBeneficiary2: String, //Tipo Cuenta Beneficiario 2
        @SerializedName("accountBeneficiary2") val accountBeneficiary2: String //Cuenta Beneficiario 2
){
    override fun toString(): String {
        return Gson().toJson(this)
    }
}