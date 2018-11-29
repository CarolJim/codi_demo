package com.pagatodo.richardsproject.modules.send_money

class SendMoneyContracts {

    interface Presenter {
        fun initViews()
        fun showLoader(message: String)
        fun onMoneySent()
        fun onErrorSevice(message: String)
    }

    interface Iteractor {
        fun sendMoney(cardNumber: String, amout: String, name: String, idBank: Int)
    }

    interface Router {
        fun presentMainScreen()
    }
}