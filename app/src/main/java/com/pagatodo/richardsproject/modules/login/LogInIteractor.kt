package com.pagatodo.richardsproject.modules.login

import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.LogInRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.UyuLogInResult
import com.pagatodo.network_manager.dtos.sender_yg.results.LogInResult
import com.pagatodo.network_manager.dtos.sender_yg.results.LogOutResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.network_manager.utils.RequestHeaders
import com.pagatodo.richardsproject.App
import com.pagatodo.richardsproject.commons.Constants
import com.pagatodo.richardsproject.commons.StringUtils
import com.pagatodo.richardsproject.commons.Utils
import java.lang.Exception

class LogInIteractor : LogInContracts.Iteractor, IRequestResult {

    private var presenter: LogInContracts.Presenter

    constructor(presenter: LogInContracts.Presenter) {
        this.presenter = presenter
    }

    override fun startSession(user: String, pss: String) {
        presenter.showLoader("Iniciando sesión")
        var request = LogInRequest(user, Utils.cipherRSA(pss, Constants().RSA_KEY))
        RequestHeaders.setUsername(user)
        RequestHeaders.setTokendevice(Utils.getTokenDevice(App.getContext()))
        try {
            SenderApi.logIn(App.getContext(), request, this, "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc/IniciarSesionSimpleUYU")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }
    }

    override fun closeSession() {
        try {
            SenderApi.logOut(App.getContext(), null, this, "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc/CerrarSesion")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }
    }

    override fun onSuccess(data: Any?) {
        when (data) {
            is LogOutResult -> presenter.onLogOut()
            is LogInResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    var response = data.data as UyuLogInResult
                    saveDataInPrefs(response)
                    presenter.onLogIn()
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

    private fun saveDataInPrefs(response: UyuLogInResult) {
        val prefs = App.getPreferences()
        RequestHeaders.setTokensesion(response.usuario.tokenSesion)
        RequestHeaders.setTokenAdq(response.usuario.tokenSesion)
        RequestHeaders.setIdCuentaAdq(response.usuario.idUsuarioAdquirente)
        RequestHeaders.setIdCuenta(String.format("%s", response.emisor.cuentas[0].idCuenta))
        prefs.saveDataBool(Constants().HAS_SESSION, true)
        prefs.saveData(Constants().SIMPLE_NAME, StringUtils.getFirstName(response.cliente.nombre)
                .plus(Constants().SPACE).plus(response.cliente.primerApellido))

        prefs.saveData(Constants().NAME_USER, response.cliente.nombre)
        prefs.saveData(Constants().FULL_NAME_USER, response.cliente.nombre.plus(Constants().SPACE)
                .plus(response.cliente.primerApellido.plus(Constants().SPACE).plus(response.cliente.segundoApellido)))
        prefs.saveData(Constants().LAST_NAME, response.cliente.primerApellido.plus(Constants().SPACE))
        response.emisor.cuentas[0].tarjetas[0].numero=response.emisor.cuentas[0].tarjetas[0].numero.replace(" ", "")
        prefs.saveData(Constants().CARD_NUMBER, response.emisor.cuentas[0].tarjetas[0].numero)
        prefs.saveData(Constants().ID_CUENTA, response.emisor.cuentas[0].idCuenta.toString())
    }
}