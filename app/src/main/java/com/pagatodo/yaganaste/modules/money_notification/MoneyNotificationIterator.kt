package com.pagatodo.yaganaste.modules.money_notification

import android.util.Log
import com.google.gson.Gson
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Cifrado_Data
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Decipher
import com.pagatodo.yaganaste.net.idun.API_Idun
import com.pagatodo.yaganaste.net.idun.CollectWireTransfer_Result
import com.pagatodo.yaganaste.net.idun.CollectiveWireTranfer_Request
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import javax.crypto.Cipher

class MoneyNotificationIterator(private val presenter: MoneyNotificationContracts.Presenter) : MoneyNotificationContracts.Iterator {

    override fun decypherMsjCbr(msjCobro: Mensaje_Cobro_Cifrado_Data): String {
        val cspv = Utils.Sha512Hex(msjCobro.id + App.getPreferences().loadData(CODI_KEYSOURCE) + msjCobro.s)
        val msjDecypher = Utils.Aes128CbcPkcs(cspv.substring(0, 32), cspv.substring(32, 64), msjCobro.mc, Cipher.DECRYPT_MODE)
        return msjDecypher!!
    }

    override fun sendMoney(objCbr: Mensaje_Cobro_Decipher) {
        val typeReference = when (objCbr.v.cb.replace(" ", "").length) {
            TARJETA_DEBITO -> "C"
            CUENTA_CLABE -> "S"
            TELEFONO_CELULAR -> "T"
            else -> "O"
        }
        /** Creación del objeto request para el envío de SPEI por CoDi */
        val request = CollectiveWireTranfer_Request(App.getPreferences().loadData(CLABE_NUMBER).replace(" ", "").toLong(),
                objCbr.v.cb, objCbr.mt, objCbr.cc, "0${objCbr.v.ci}", objCbr!!.v.nb,
                Utils.getNumericReference(), 20, typeReference, App.getPreferences().loadData(PHONE_NUMBER),
                App.getPreferences().loadData(CODI_DV).toLong(), "0",
                objCbr.v.dv, objCbr.id, objCbr.v.nc)
        val text = Gson().toJson(request)
        /** Generación de header para indicar que el body es un tipo application/json  */
        val body = RequestBody.create(MediaType.parse("application/json"), text)
        /** Petición al Web Service: [API_Idun.GetIdunService.getCollectiveWireTransfer] para mandar dinero
         * empleando CoDi */
        API_Idun().getCustomService().getCollectiveWireTransfer(body).enqueue(object : Callback<CollectWireTransfer_Result> {
            override fun onResponse(call: Call<CollectWireTransfer_Result>, response: Response<CollectWireTransfer_Result>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    presenter.onCoDiSent(response.body()!!.trackingKey)
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en petición Idun")
                }
            }

            override fun onFailure(call: Call<CollectWireTransfer_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
                presenter.onErrorService("Error en petición Idun")
            }
        })
    }

}