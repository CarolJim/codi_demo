package com.pagatodo.yaganaste.modules.send_money

import com.pagatodo.yaganaste.dtos.CoDi
import com.pagatodo.yaganaste.dtos.CoDi_Decypher

class SendMoneyContracts {

    interface Presenter {
        fun initViews()
        fun showLoader(message: String)
        fun onMoneySent()
        fun onCoDiSent(message: String)
        fun onErrorService(message: String)
        fun onAcceptCodiTransfer(codiDecypher: CoDi_Decypher?)
        fun onCodiDescipher(codiDecypher: CoDi_Decypher?)
    }

    interface Iteractor {
        fun proccessQrRead(qrRead: CoDi)
        fun sendMoney(cardNumber: String, amout: String, name: String, idBank: Int)
        fun sendCodiPayment(codiDecypher: CoDi_Decypher?)
    }

    interface Router {
        fun presentMainScreen()
    }
}