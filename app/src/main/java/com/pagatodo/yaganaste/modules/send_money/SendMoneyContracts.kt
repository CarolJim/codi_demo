package com.pagatodo.yaganaste.modules.send_money

import com.pagatodo.yaganaste.dtos.CoDi

class SendMoneyContracts {

    interface Presenter {
        fun initViews()
        fun showLoader(message: String)
        fun onMoneySent()
        fun onCoDiSent(message: String)
        fun onErrorService(message: String)
    }

    interface Iteractor {
        fun proccessQrRead(qrRead: CoDi)
        fun sendMoney(cardNumber: String, amout: String, name: String, idBank: Int)
    }

    interface Router {
        fun presentMainScreen()
    }
}