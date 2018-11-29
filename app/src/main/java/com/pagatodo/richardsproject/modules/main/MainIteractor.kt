package com.pagatodo.richardsproject.modules.main

import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.requests.MovementsRequest
import com.pagatodo.network_manager.dtos.sender_yg.results.BalanceResult
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsResult
import com.pagatodo.network_manager.dtos.sender_yg.results.SenderGenericResult
import com.pagatodo.network_manager.interfaces.IRequestResult
import com.pagatodo.network_manager.utils.NetworkUtils.CODE_OK
import com.pagatodo.richardsproject.App
import com.pagatodo.richardsproject.commons.StringUtils
import java.lang.Exception
import java.util.*

class MainIteractor : MainContracts.Iteractor, IRequestResult {

    var presenter: MainContracts.Presenter

    constructor(presenter: MainContracts.Presenter) {
        this.presenter = presenter
    }

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
}