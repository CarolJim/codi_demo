package com.pagatodo.yaganaste.modules.register

import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.CreateUserRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.CreateUserDataResult
import com.pagatodo.network_manager.dtos.sender_yg.results.CreateUserResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.network_manager.utils.RequestHeaders
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.commons.*

class RegisterIteractor : RegisterContracts.Iteractor, IRequestResult {

    private var presenter: RegisterContracts.Presenter

    constructor(presenter: RegisterContracts.Presenter) {
        this.presenter = presenter
    }

    override fun registerUserClient(name: String, surname: String, secondSurname: String, email: String,
                                    dateBirth: String, placeBirth: String, genre: String, claveBirthPlace: String) {
        presenter.showLoader("Creando usuario")
        var request = CreateUserRequest(email, Utils.cipherRSA("123456", RSA_KEY), name,
                surname, secondSurname, genre, dateBirth, "", "", "MX", placeBirth,
                email, "", "", "090150882", "Otra colonia 23",
                "06100", "Mandalas23", "39", "", 127)
        try {
            SenderApi.createUser(App.getContext(), request, this, "http://189.201.137.21:8031/ServicioYaGanasteAdtvo.svc/CrearUsuarioClientePrototipo")
        } catch (e: Exception) {
            presenter.onErrorService("Intente de nuevo más tarde")
        }

    }

    override fun onSuccess(data: Any?) {
        when (data) {
            is CreateUserResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    var result = data.data as CreateUserDataResult
                    saveDataInPrefs(result)
                    presenter.onUserCreated()
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

    private fun saveDataInPrefs(response: CreateUserDataResult) {
        val prefs = App.getPreferences()
        RequestHeaders.setTokensesion(response.usuario.tokenSesion)
        RequestHeaders.setTokenAdq(response.usuario.tokenSesion)
        RequestHeaders.setIdCuentaAdq(response.usuario.idUsuarioAdquirente)
        RequestHeaders.setIdCuenta(String.format("%s", response.emisor.cuentas[0].idCuenta))
        prefs.saveDataBool(HAS_SESSION, true)
        prefs.saveData(SIMPLE_NAME, StringUtils.getFirstName(response.cliente.nombre)
                .plus(SPACE).plus(response.cliente.primerApellido))

        prefs.saveData(NAME_USER, response.cliente.nombre)
        prefs.saveData(FULL_NAME_USER, response.cliente.nombre.plus(SPACE)
                .plus(response.cliente.primerApellido.plus(SPACE).plus(response.cliente.segundoApellido)))
        prefs.saveData(LAST_NAME, response.cliente.primerApellido.plus(SPACE))
        response.emisor.cuentas[0].tarjetas[0].numero = response.emisor.cuentas[0].tarjetas[0].numero.replace(" ", "")
        prefs.saveData(CARD_NUMBER, response.emisor.cuentas[0].tarjetas[0].numero)
        prefs.saveData(ID_CUENTA, response.emisor.cuentas[0].idCuenta.toString())
    }
}