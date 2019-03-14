package com.pagatodo.yaganaste.modules.main

import android.graphics.pdf.PdfDocument
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult

class MainContracts {

    interface Presenter {
        fun updateBalance(balance: String)
        fun updateMovements(movements: List<MovementsItemResult>)
        fun onErrorService(message: String)
        fun showLoader(message: String)
        fun onRegisterCodiSuccess()
        fun onRegisterPhoneSuccess(noPresential: Boolean)
        fun onRegisterOmitionSuccess()
        fun onVerifyCode(code: String)
        fun onVerifyPhoneNumber()
        fun onRequiredOmitionRegister()
        fun unregisterReceiver()
        fun onValidationSucces(mensaje : String, showBtn : Boolean)
        fun onLogOut()
        fun onAcceptDefaultRegister()
        fun onCancelDefaultRegster()
    }

    interface Iteractor {
        fun getBalance()
        fun getMovements()
        fun registerCodi()
        fun registerToPushService()
        fun registerDeviceCodi()
        fun registerDeviceOmisionCodi()
        fun unsubscribeCodi()
        fun consultRegisterBankAccountCoDi()
        fun registerBankAccountCoDi()
        fun consultStatusCoDiCharges(page:Int)
        fun closeSession()
    }

    interface Router {
        fun presentSendMoneyScreen()
        fun presentGenerateQrScreen()
        fun presentConsultValidationScreen()
    }
}