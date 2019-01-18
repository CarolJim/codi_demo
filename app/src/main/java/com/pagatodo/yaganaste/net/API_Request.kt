package com.pagatodo.yaganaste.net

import com.google.gson.annotations.SerializedName

class RegistroInicial_Request(@SerializedName("nc") val phoneNumber: String, @SerializedName("idH") val idHardware: String,
                              @SerializedName("ia") val additionalInfo: Aditional_Info_Data)

class Aditional_Info_Data(@SerializedName("so") val so: String = "android", @SerializedName("vSO") val SO: String,
                          @SerializedName("fab") val manufacturer: String, @SerializedName("mod") val model: String)

class RegistroDispositivo_Request(@SerializedName("nc") val phoneNumber: String, @SerializedName("idH") val idHardware: String,
                                  @SerializedName("ia") val additionalInfo: Aditional_Info_Data,
                                  @SerializedName("dv") val dv: Int, @SerializedName("idN") val idN: String,
                                  @SerializedName("hmac") val hmac: String)

class RegistroDispositivoPorOmision_Request(@SerializedName("nc") val phoneNumber: String, @SerializedName("dv") val dv: Int,
                                            @SerializedName("hmac") val hmac: String)

class BajaDispositivo_Request(@SerializedName("infoDispositivosCif") val infoDispositivosCif: String,
                              @SerializedName("claveSimCif") val claveSimCif: String, @SerializedName("serieCertPartic") val serieCertPartic: String,
                              @SerializedName("cvePartic") val cvePartic: Long, @SerializedName("selloPartic") val selloPartic: String,
                              @SerializedName("serieCertBM") val serieCertBM: String)

class Consulta_Request(@SerializedName("v") val v: Beneficiario_Ordenante_Data, @SerializedName("c") val c: Beneficiario_Ordenante_Data,
                       @SerializedName("id") val id: String, @SerializedName("tpg") val tpg: Long,
                       @SerializedName("npg") val npg: Long)

class Beneficiario_Ordenante_Data(@SerializedName("nc") val nc: String, @SerializedName("dv") val dv: Int)