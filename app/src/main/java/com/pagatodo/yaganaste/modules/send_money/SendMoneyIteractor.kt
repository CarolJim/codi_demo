package com.pagatodo.yaganaste.modules.send_money

import android.util.Log
import com.google.gson.Gson
import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.SendMoneyRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.SendMoneyResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.commons.StringUtils.createTicket
import com.pagatodo.yaganaste.dtos.CoDi
import com.pagatodo.yaganaste.dtos.CoDi_Decypher
import com.pagatodo.yaganaste.net.banxico.*
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

class SendMoneyIteractor : SendMoneyContracts.Iteractor, IRequestResult {

    private var presenter: SendMoneyContracts.Presenter
    private var codiDecypher: CoDi_Decypher? = null
    private var newIdQr: String? = null
    private var serieCtr: String? = null

    constructor(presenter: SendMoneyContracts.Presenter) {
        this.presenter = presenter
    }

    override fun proccessQrRead(qrRead: CoDi) {
        newIdQr = Utils.getCodiNewId(qrRead.ic.idc)
        val phoneNumber = qrRead.v.dev.split("/")[0]
        val verifierDigit = qrRead.v.dev.split("/")[1]
        val hash = Utils.HmacSha256(App.getPreferences().loadData(CODI_KEYSOURCE).substring(64, 128),
                newIdQr + qrRead.ic.ser + phoneNumber + Utils.validateDv(verifierDigit) + App.getPreferences().loadData(PHONE_NUMBER)
                        + App.getPreferences().loadData(CODI_DV) + qrRead.ic.enc)
        /** Creación del objeto request para el Consulta Claves Descifrado */
        val request = SolicitudClaveDescifrado_Request(
                1,
                Beneficiario_Ordenante_Data(phoneNumber, verifierDigit.toInt()),
                Beneficiario_Ordenante_Data(
                        App.getPreferences().loadData(PHONE_NUMBER),
                        App.getPreferences().loadData(CODI_DV).toInt()),
                Mensaje_Cobro_Cifrado_Data(
                        newIdQr!!,
                        qrRead.ic.ser.toString(),
                        qrRead.ic.enc),
                hash)
        val text = "d=" + Gson().toJson(request)
        Log.e(TAG_CODI, "$text")
        /** Generación de header para indicar que el body es un tipo text/plain */
        val body = RequestBody.create(MediaType.parse("text/plain"), text)
        /** Petición al Web Service: [API_Banxico.GetBanxicoService.getClaveDescifrado] para obtener las llaves
         * necesarias para descifrar el mensaje de cobro */
        API_Banxico().getCustomService().getClaveDescifrado(body).enqueue(object : Callback<SolicitudClaveDescifrado_Result> {
            override fun onResponse(call: Call<SolicitudClaveDescifrado_Result>, response: Response<SolicitudClaveDescifrado_Result>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.e(TAG_CODI, "solicitaClaveDescifradoMC: ${response.toString()}")
                    Log.e(TAG_CODI, "solicitaClaveDescifradoMC edoPet: ${response.body()!!.edoPet}")
                    Log.e(TAG_CODI, "solicitaClaveDescifradoMC claveEnmascarada: ${response.body()!!.claveEnmascarada}")
                    Log.e(TAG_CODI, "solicitaClaveDescifradoMC selloBmx: ${response.body()!!.selloBmx}")
                    Log.e(TAG_CODI, "solicitaClaveDescifradoMC serieCertificado: ${response.body()!!.serieCertificado}")
                    if (response.body()!!.edoPet == 0) {
                        serieCtr = response.body()!!.serieCertificado
                        val cspc = Utils.Sha512Hex(request.ic.id + App.getPreferences().loadData(CODI_KEYSOURCE) + request.ic.s)
                        val cspv = Utils.XOR(response.body()!!.claveEnmascarada, cspc)
                        val jsonCodiDecypher = Utils.Aes128CbcPkcs(
                                cspv.substring(0, 32),
                                cspv.substring(32, 64),
                                qrRead.ic.enc,
                                Cipher.DECRYPT_MODE)
                        Log.e(TAG_CODI, "jsonCodiDecypher (qr.ic.enc): $jsonCodiDecypher")

                        codiDecypher = Gson().fromJson(jsonCodiDecypher, CoDi_Decypher::class.java)

                        //Guardamos el idc y mensaje descifrado, para poder realizar posteriormente la consulta de los mensajes de cobro presenciales
                        App.getPreferences().saveData(CODI_IDC_TYPE19, codiDecypher!!.idc)
                        App.getPreferences().saveData(CODI_MSSG_COBRO, jsonCodiDecypher)

                        presenter.onCodiDescipher(codiDecypher)

                    } else {

                        val error = when (response.body()!!.edoPet){
                            0 -> "EDO_EXITO"
                            -3 -> "EDO_ERROR_PARAMETROS_ENTRADA_INCORRECTOS"
                            -4 -> "EDO_ERROR_DISPOSITIVO_NO_REGISTRADO_PREVIAMENTE"
                            -8 -> "EDO_ERROR_CODIGO_HMAC_INVALIDO"
                            -10 -> "EDO_ERROR_AL_DESCIFRAR_MC"
                            -11 -> "EDO_ERROR_NO_CORRESPONDE_CUENTA_Y_NOMBRE_BENEF"
                            -13 -> "EDO_ERROR_MENSAJE_COBRO_DUPLICADO"
                            -15 -> "EDO_ERROR_ID_MENSAJE_COBRO_INCOMPLETO"
                            -16 -> "EDO_ERROR_CARACTERES_INVALIDOS"
                            -17 -> "EDO_ERROR_SIN_FECHA_LIMITE_PAGO"
                            else -> "EDO_ERROR_DESCONOCIDO (${response.body()!!.edoPet})"
                        }

                        Log.e(TAG_CODI, "$error")
                        presenter.onErrorService("Error en obtención de certificados: $error")
                    }
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en obtención de certificados")
                }
            }

            override fun onFailure(call: Call<SolicitudClaveDescifrado_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
                t.printStackTrace()
                presenter.onErrorService("Error en obtención de certificados")
            }
        })
    }

    override fun sendCodiPayment(codiDecypher: CoDi_Decypher?) {
        val typeReference = when (
            codiDecypher!!.v.acc.replace(" ", "").length) {
            TARJETA_DEBITO -> "C"
            CUENTA_CLABE -> "S"
            TELEFONO_CELULAR -> "T"
            else -> "O"
        }


        /** Creación del objeto request para el envío de SPEI por CoDi */
        val request = CollectiveWireTranfer_Request(
                App.getPreferences().loadData(CLABE_NUMBER).replace(" ", "").toLong(),
                codiDecypher!!.v.acc,
                codiDecypher!!.amo,
                codiDecypher!!.des,
                "${formatBankCode(codiDecypher!!.v.ban)}",
                codiDecypher!!.v.nam,
                Utils.getNumericReference(),
                codiDecypher!!.typ,
                typeReference,
                App.getPreferences().loadData(PHONE_NUMBER),
                App.getPreferences().loadData(CODI_DV).toLong(),
                codiDecypher!!.v.dev.split("/")[0],
                codiDecypher!!.v.dev.split("/")[1].toLong(),
                newIdQr!!,
                serieCtr!!)
        val text = Gson().toJson(request)
        Log.e(TAG_CODI, "request: $text")
        /** Generación de header para indicar que el body es un tipo application/json  */
        val body = RequestBody.create(MediaType.parse("application/json"), text)
        /** Petición al Web Service: [API_Idun.GetIdunService.getCollectiveWireTransfer] para mandar dinero
         * empleando CoDi */
        API_Idun().getCustomService().getCollectiveWireTransfer(body).enqueue(object : Callback<CollectWireTransfer_Result> {
            override fun onResponse(call: Call<CollectWireTransfer_Result>, response: Response<CollectWireTransfer_Result>) {
                Log.e(TAG_CODI, "getCollectiveWireTransfer response: ${response.toString()}")
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Log.e(TAG_CODI, "getCollectiveWireTransfer confirmationNumber: ${response.body()!!.confirmationNumber}")
                    presenter.onCoDiSent(response.body()!!.confirmationNumber)
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en petición Idun")
                }
            }

            override fun onFailure(call: Call<CollectWireTransfer_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio getCollectiveWireTransfer: " + t.message)
                t.printStackTrace()
                presenter.onErrorService("Error en petición Idun")
            }
        })
    }

    override fun sendMoney(cardNumber: String, amout: String, name: String, idBank: Int) {
        presenter.showLoader("Enviando dinero")
        var request = SendMoneyRequest(3, cardNumber, amout.toDouble(), idBank, name, "Envío de dinero",
                "123456", createTicket())
        try {
            SenderApi.enviarDinero(App.getContext(), request, this, "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc/EjecutarTransaccion")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }
    }

    override fun onSuccess(data: Any?) {
        when (data) {
            is SendMoneyResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    presenter.onMoneySent()
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
        }
    }

    override fun onFailed(error: Any?) {
        when (error) {
            is SenderGenericResult -> presenter.onErrorService(error.mensaje)
        }
    }

    private fun formatBankCode(bankCode: Long): String {
        return when (bankCode.toString().length) {
            1 -> "00000$bankCode"
            2 -> "0000$bankCode"
            3 -> "000$bankCode"
            4 -> "00$bankCode"
            5 -> "0$bankCode"
            else -> bankCode.toString()
        }
    }
}