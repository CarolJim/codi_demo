package com.pagatodo.yaganaste.modules.main

import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.MovementsRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.BalanceResult
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig.CODI_BANK_ID
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.net.banxico.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection.HTTP_OK
import java.util.*
import javax.crypto.Cipher


class MainIteractor(val presenter: MainContracts.Presenter) : MainContracts.Iteractor, IRequestResult {

    override fun getBalance() {
        presenter.showLoader("Obteniendo saldo")
        try {
            SenderApi.consultarSaldo(App.getContext(), null, this, "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc/ConsultarSaldo")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }
    }

    override fun getMovements() {
        presenter.showLoader("Obteniendo movimientos")
        try {
            SenderApi.consultarMovimientos(App.getContext(), getMovementsRequest(), this, "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc/ConsultarMovimientosMes")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }
    }

    override fun onSuccess(data: Any?) {
        when (data) {
            is BalanceResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    presenter.updateBalance(StringUtils.getCurrencyValue(data.saldo))
                } else {
                    presenter.onErrorService(data.mensaje)
                }
            }
            is MovementsResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    presenter.updateMovements(data.data)
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

    private fun getMovementsRequest(): MovementsRequest {
        // Calendar calendar = Calendar.getInstance(new Locale("MX"));
        val names = MovementsRequest()
        val calendar = Calendar.getInstance(Locale("MX"))
        calendar.add(Calendar.MONTH, 0)
        names.anio = calendar.get(Calendar.YEAR).toString()
        names.mes = (calendar.get(Calendar.MONTH) + 1).toString()
        names.direccion = ""
        names.idMovimiento = ""
        return names
    }

    override fun registerCodi() {
        /** Generación de request para realizar el Registro Inicial en CoDi */
        val request = RegistroInicial_Request(App.getPreferences().loadData(PHONE_NUMBER),
                App.getPreferences().loadData(CODI_IDH), Aditional_Info_Data("android",
                Build.VERSION.SDK_INT.toString(), Build.MANUFACTURER, Build.MODEL))
        val text = "d=" + Gson().toJson(request)
        /** Generación de header para indicar que el body es un tipo text/plain */
        val body = RequestBody.create(MediaType.parse("text/plain"), text)
        /** Petición al Web Service: [API_Banxico.GetBanxicoService.getRegistroInicial] para solicitar el código de
         * proyecto de Google Cifrado, y el código de verificación enviado vía SMS */
        API_Banxico().getCustomService().getRegistroInicial(body).enqueue(object : Callback<RegistroInicial_Result> {
            override fun onResponse(call: Call<RegistroInicial_Result>, response: Response<RegistroInicial_Result>) {
                /** Si el servicio responde correctamente, se tiene que validar la bandera [edoPet] para
                 * corroborar que haya sido exitoso */
                if (response.code() == HTTP_OK) {
                    if (response.body()!!.edoPet == 0) {
                        /** Se guarda en preferencias el Id del proyecto de Google que regresa cifrado de Banxico,
                         * y la bandera de [HAS_REGISTER_TO_RECEIVE_CODI] para realizar en sesiones posteriores el Registro Subsecuente */
                        App.getPreferences().saveData(CODI_GOOGLE_ID, response.body()!!.gId)
                        App.getPreferences().saveData(CODI_DV, Utils.validateDv(response.body()!!.dv.toString()))
                        presenter.onRegisterCodiSuccess()
                    } else {
                        Log.e("CODI", "Error en parámetros de entrada")
                        presenter.onErrorService("Error en registro CoDi")
                    }
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en registro CoDi")
                }
            }

            override fun onFailure(call: Call<RegistroInicial_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
                presenter.onErrorService("Error en registro CoDi")
            }
        })
    }

    override fun registerToPushService() {
        /** Obtener del Id del proyecto Google de la cadena cifrada que regresa Banxico en el Registro Inicial */
        val aes = App.getPreferences().loadData(CODI_KEYSOURCE).substring(0, 32)
        val iv_aes = App.getPreferences().loadData(CODI_KEYSOURCE).substring(32, 64)
        val googleId = Utils.Aes128CbcPkcs(aes, iv_aes, App.getPreferences().loadData(CODI_GOOGLE_ID),
                Cipher.DECRYPT_MODE)
        /** Se crea un objeto [FirebaseOptions] para acceder al proyecto de Notificaciones de Banxico */
        val options = FirebaseOptions.Builder().setApplicationId("1:$googleId:android:$ODIN_ID").build()
        /** Se crea un objecto [FirebaseApp] de donde se va a obtener el ID de Cloud Messaging asignado a la App */
        var firebaseApp: FirebaseApp?
        try {
            firebaseApp = FirebaseApp.initializeApp(App.getContext(), options, "codi")
            /** Por medio de una tarea se obtiene el token asgiando al dispositivo y se guarda en preferenias para uso posterior */
            FirebaseInstanceId.getInstance(firebaseApp).instanceId.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.token != "") {
                        App.getPreferences().saveData(CODI_NOTIFICATIONS_ID, task.result!!.token)
                        presenter.unregisterReceiver()
                        registerDeviceCodi()
                    }
                    Log.e(TAG_CODI, "Token Request Successful")
                } else {
                    Log.e(TAG_CODI, "Token Request Failed")
                    presenter.onErrorService("Error en registro Firebase")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onErrorService("Error en registro Firebase")
        }
    }

    override fun registerDeviceCodi() {
        /** Se obtiene el HMAC con la llave de descrifrado por medio del SHA256, y se cifra la concatenación del número
         * telefónico, el código de verificación que llegó vía SMS y el token asignado del proyecto de Google de Banxico */
        val hmac_keysource = App.getPreferences().loadData(CODI_KEYSOURCE).substring(64, 128)
        val hmac = Utils.HmacSha256(hmac_keysource, App.getPreferences().loadData(PHONE_NUMBER) +
                App.getPreferences().loadData(CODI_DV) + App.getPreferences().loadData(CODI_NOTIFICATIONS_ID))
        /** Creación del objeto request para el Registro Subsecuente del Dispostivo */
        val request = RegistroDispositivo_Request(App.getPreferences().loadData(PHONE_NUMBER),
                App.getPreferences().loadData(CODI_IDH), Aditional_Info_Data("android",
                Build.VERSION.SDK_INT.toString(), Build.MANUFACTURER, Build.MODEL),
                App.getPreferences().loadData(CODI_DV).toInt(), App.getPreferences().loadData(CODI_NOTIFICATIONS_ID),
                hmac)
        val text = "d=" + Gson().toJson(request)
        /** Generación de header para indicar que el body es un tipo text/plain */
        val body = RequestBody.create(MediaType.parse("text/plain"), text)
        /** Petición al Web Service: [API_Banxico.GetBanxicoService.getRegistroDispositivo] para verificar que el dispositivo
         * se ha registrado correctamente para recibir mensajes de Cobro Presencial y No Presencial */
        API_Banxico().getCustomService().getRegistroDispositivo(body).enqueue(object : Callback<RegistroDispositivo_Result> {
            override fun onResponse(call: Call<RegistroDispositivo_Result>, response: Response<RegistroDispositivo_Result>) {
                if (response.code() == HTTP_OK) {
                    if (response.body()!!.edoPet == 0) {
                        App.getPreferences().saveData(CODI_DV_OMISION, Utils.validateDv(response.body()!!.dvOmision.toString()))
                        App.getPreferences().saveDataBool(HAS_REGISTER_TO_RECEIVE_CODI, true)
                        /* Registrar App para que pueda recibir mensajes de Cobro No Presencial */
                        registerDeviceOmisionCodi()
                        /* Registrar cuenta para poder generar mensajes de Cobro */
                        if (!App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_SEND_CODI, false)) {
                            registerBankAccountCoDi()
                        }
                        presenter.onRegisterPhoneSuccess()
                    } else {
                        Log.e("CODI", "Error en parámetros de entrada")
                        presenter.onErrorService("Error en registro CoDi")
                    }
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en registro CoDi")
                }
            }

            override fun onFailure(call: Call<RegistroDispositivo_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
                presenter.onErrorService("Error en registro CoDi")
            }
        })
    }

    private fun registerDeviceOmisionCodi() {
        /** Generación de HMAC con los parámetros obtenidos en [API_Banxico.GetBanxicoService.getRegistroInicial] */
        val hmac = Utils.HmacSha256(App.getPreferences().loadData(CODI_KEYSOURCE).substring(64, 128),
                App.getPreferences().loadData(PHONE_NUMBER) + App.getPreferences().loadData(CODI_DV))
        /** Generación de request para realizar el Registro por omisión en CoDi para operación No Presencial */
        val request = RegistroDispositivoPorOmision_Request(App.getPreferences().loadData(PHONE_NUMBER),
                App.getPreferences().loadData(CODI_DV).toInt(), hmac)
        val text = "d=" + Gson().toJson(request)
        /** Generación de header para indicar que el body es un tipo text/plain */
        val body = RequestBody.create(MediaType.parse("text/plain"), text)
        /** Petición al Web Service: [API_Banxico.GetBanxicoService.getRegistroDispositivoPorOmision] para solicitar
         *  operación del dispositivo para aceptar Mensajes de Cobro No Presenciales */
        API_Banxico().getCustomService().getRegistroDispositivoPorOmision(body).enqueue(object : Callback<RegistroDispositivoPorOmision_Result> {
            override fun onResponse(call: Call<RegistroDispositivoPorOmision_Result>, response: Response<RegistroDispositivoPorOmision_Result>) {
                /** Si el servicio responde correctamente, se tiene que validar la bandera [edoPet] para
                 * corroborar que haya sido exitoso */
                if (response.code() == HTTP_OK) {
                    if (response.body()!!.edoPet == 0) {
                        Log.e("CODI", "Registro por Omisión Correcto")
                    } else {
                        Log.e("CODI", "Error en parámetros de entrada")
                    }
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                }
            }

            override fun onFailure(call: Call<RegistroDispositivoPorOmision_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
            }
        })
    }

    private fun registerBankAccountCoDi() {
        /** Se obtiene el HMAC con la llave de descrifrado por medio del SHA256, y se cifra la concatenación del  número de celular (nc),
         *  código de verificación de registro (dv) completado a 3 dígitos con ceros a la izquierda en caso de ser necesario, la cuenta
         *  configurada para recibir los fondos (cb), el tipo de cuenta (tc) y la clave de la institución bancaria del vendedor (ci) */
        val hmac_keysource = App.getPreferences().loadData(CODI_KEYSOURCE).substring(64, 128)
        val hmac = Utils.HmacSha256(hmac_keysource, App.getPreferences().loadData(PHONE_NUMBER) +
                App.getPreferences().loadData(CODI_DV) + App.getPreferences().loadData(CLABE_NUMBER).replace(" ", "") +
                CODI_CLABE_ID.toString() + CODI_BANK_ID)
        /** Creación del objeto request para el Registro Subsecuente del Dispostivo */
        val request = ValidacionCuenta_Request(App.getPreferences().loadData(CLABE_NUMBER).replace(" ", ""),
                CODI_CLABE_ID, CODI_BANK_ID.toInt(), hmac, Beneficiario_Ordenante_Data(App.getPreferences().loadData(PHONE_NUMBER), App.getPreferences().loadData(CODI_DV).toInt()))
        val text = "d=" + Gson().toJson(request)
        /** Generación de header para indicar que el body es un tipo text/plain */
        val body = RequestBody.create(MediaType.parse("text/plain"), text)
        /** Petición al Web Service: [API_Banxico.GetBanxicoService.getValidacionCuentasBeneficiarias] para
         * validar la cuenta con la cual se generaran los mensajes de cobro */
        API_Banxico().getCustomService().getValidacionCuentasBeneficiarias(body).enqueue(object : Callback<ValidacionCuentasBeneficiarias_Result> {
            override fun onResponse(call: Call<ValidacionCuentasBeneficiarias_Result>, response: Response<ValidacionCuentasBeneficiarias_Result>) {
                if (response.code() == HTTP_OK) {
                    if (response.body()!!.edoPet == 0) {
                        /* Con esta función, se genera un arreglo de 64 bytes, que se usará de la siguiente manera:
                            ◄ Bytes 0 al 15  Clave de 16 bytes para el algoritmo AES-128 CBC PKCS5 Padding.
                            ◄ Bytes 16 al 31  Arreglo de 16 bytes como vector de inicialización para el modo CBC del algoritmo AES-128 CBC PKCS5 Padding.  */
                        val keySourceAccountValidation = Utils.Sha512Hex(response.body()!!.claveRastreo + App.getPreferences().loadData(CODI_KEYSOURCE))
                        App.getPreferences().saveData(CODI_KEYSOURCE_VALIDATION_ACC, keySourceAccountValidation)
                        Log.e("CODI", "REGISTRO EXITOSO VALIDACION CUENTAS - Clave de Rastreo: ${response.body()!!.claveRastreo}")
                    } else {
                        Log.e("CODI", "Error en parámetros de entrada")
                        presenter.onErrorService("Error en registro CoDi")
                    }
                } else {
                    Log.e("CODI", "Error en servicio http: " + response.code())
                    presenter.onErrorService("Error en registro CoDi")
                }
            }

            override fun onFailure(call: Call<ValidacionCuentasBeneficiarias_Result>, t: Throwable) {
                Log.e("CODI", "Error en servicio http: " + t.message)
                presenter.onErrorService("Error en registro CoDi")
            }
        })
    }
}
