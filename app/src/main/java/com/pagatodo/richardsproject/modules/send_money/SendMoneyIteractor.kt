package com.pagatodo.richardsproject.modules.send_money

import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.SendMoneyRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.SendMoneyResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.richardsproject.App
import com.pagatodo.richardsproject.commons.StringUtils.createTicket
import java.lang.Exception

class SendMoneyIteractor : SendMoneyContracts.Iteractor, IRequestResult {

    private var presenter: SendMoneyContracts.Presenter

    constructor(presenter: SendMoneyContracts.Presenter) {
        this.presenter = presenter
    }

    override fun sendMoney(cardNumber: String, amout: String, name: String, idBank: Int) {
        presenter.showLoader("Enviando dinero")
        var request = SendMoneyRequest(3, cardNumber, amout.toDouble(), idBank, name, "Envío de dinero",
                "123456", createTicket())
        try {
            SenderApi.enviarDinero(App.getContext(), request, this, "http://189.201.137.21:8032/ServicioYaGanasteTrans.svc/EjecutarTransaccion")
        } catch (e: Exception) {
            presenter.onErrorSevice("Intente de nuevo más tarde")
        }
    }

    override fun onSuccess(data: Any?) {
        when (data) {
            is SendMoneyResult -> {
                if (data.codigoRespuesta == CODE_OK) {
                    presenter.onMoneySent()
                } else {
                    presenter.onErrorSevice(data.mensaje)
                }
            }
        }
    }

    override fun onFailed(error: Any?) {
        when (error) {
            is SenderGenericResult -> presenter.onErrorSevice(error.mensaje)
        }
    }
}