package com.pagatodo.yaganaste.net

import com.google.gson.annotations.SerializedName

class RegistroInicial_Result(@SerializedName("gId") val gId: String, @SerializedName("dv") val dv: Int,
                             @SerializedName("edoPet") val edoPet: Int)

class RegistroDispositivo_Result(@SerializedName("dv") val dv: Int, @SerializedName("dvOmision") val dvOmision: Int,
                                 @SerializedName("edoPet") val edoPet: Int)

class RegistroDispositivoPorOmision_Result(@SerializedName("edoPet") val edoPet: Int)

class BajaDispositivos_Result(@SerializedName("cadenaResultadosCif") val cadenaResultadosCif: String,
                              @SerializedName("edoPet") val edoPet: Int, @SerializedName("serieCertBM") val serieCertBM: String,
                              @SerializedName("selloBM") val selloBM: String)

class Consulta_Result(@SerializedName("info") val info: Consulta_Data, @SerializedName("edoPet") val edoPet: Int)

class Consulta_Data(@SerializedName("listaMC") val listaMC: String, @SerializedName("ultima") val ultima: Boolean)

class Mensaje_Cobro_Decipher(@SerializedName("id") val id: String, @SerializedName("cc") val cc: String,
                             @SerializedName("mt") val mt: Long, @SerializedName("cr") val cr: String,
                             @SerializedName("hs") val hs: Long, @SerializedName("hp") val hp: Long,
                             @SerializedName("e") val e: Long, @SerializedName("c") val c: Comprador_Vendedor,
                             @SerializedName("v") val v: Comprador_Vendedor)

class Comprador_Vendedor(@SerializedName("nb") val nb: String, @SerializedName("ci") val ci: Long,
                         @SerializedName("tc") val tc: Long, @SerializedName("cb") val cb: String,
                         @SerializedName("dv") val dv: Long, @SerializedName("nc") val nc: String)