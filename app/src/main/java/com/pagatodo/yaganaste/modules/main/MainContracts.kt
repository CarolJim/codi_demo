package com.pagatodo.yaganaste.modules.main

import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult

class MainContracts {

    interface Presenter {
        fun updateBalance(balance: String)
        fun updateMovements(movements: List<MovementsItemResult>)
        fun onErrorService(message: String)
        fun showLoader(message: String)
        fun onRegisterCodiSuccess()
        fun onRegisterPhoneSuccess()
        fun onVerifyCode(code: String)
    }

    interface Iteractor {
        fun getBalance()
        fun getMovements()
        fun registerCodi()
        fun registerToPushService()
        fun registerDeviceCodi()
    }

    interface Router {
        fun presentSendMoneyScreen()
    }
}