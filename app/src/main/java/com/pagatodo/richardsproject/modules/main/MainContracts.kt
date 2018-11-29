package com.pagatodo.richardsproject.modules.main

import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult

class MainContracts {

    interface Presenter {
        fun updateBalance(balance: String)
        fun updateMovements(movements: List<MovementsItemResult>)
        fun onErrorService(message: String)
        fun showLoader(message: String)
    }

    interface Iteractor {
        fun getBalance()
        fun getMovements()
    }

    interface Router {
        fun presentSendMoneyScreen()
    }
}