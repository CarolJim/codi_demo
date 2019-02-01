package com.pagatodo.yaganaste.net.banxico

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RegistroInicial_Request(@SerializedName("nc") val phoneNumber: String, @SerializedName("idH") val idHardware: String,
                              @SerializedName("ia") val additionalInfo: Aditional_Info_Data)

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

class SolicitudClaveDescifrado_Request(@SerializedName("tipo") val tipo: Int, @SerializedName("v") val v: Beneficiario_Ordenante_Data,
                                       @SerializedName("c") val c: Beneficiario_Ordenante_Data,
                                       @SerializedName("ic") val ic: Mensaje_Cobro_Cifrado_Data,
                                       @SerializedName("hmac") val hmac: String)


class ValidacionCuenta_Request(@SerializedName("cb") val cb: String, @SerializedName("tc") val tc: Int,
                               @SerializedName("ci") val ci: Int, @SerializedName("hmac") val hmac: String,
                               @SerializedName("ds") val ds: Beneficiario_Ordenante_Data)

/**
 *  Clases Data : Objetos de 2do o mayor nivel en el Json
 */
class Aditional_Info_Data(@SerializedName("so") val so: String = "android", @SerializedName("vSO") val SO: String,
                          @SerializedName("fab") val manufacturer: String, @SerializedName("mod") val model: String)

class Beneficiario_Ordenante_Data(@SerializedName("nc") val nc: String, @SerializedName("dv") val dv: Int)

class Mensaje_Cobro_Cifrado_Data(@SerializedName("id") val id: String, @SerializedName("s") val s: String,
                                 @SerializedName("mc") val mc: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(s)
        parcel.writeString(mc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mensaje_Cobro_Cifrado_Data> {
        override fun createFromParcel(parcel: Parcel): Mensaje_Cobro_Cifrado_Data {
            return Mensaje_Cobro_Cifrado_Data(parcel)
        }

        override fun newArray(size: Int): Array<Mensaje_Cobro_Cifrado_Data?> {
            return arrayOfNulls(size)
        }
    }
}