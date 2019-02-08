package com.pagatodo.yaganaste.modules.money_notification

import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Cifrado_Data
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Decipher

class MoneyNotificationContracts {

    interface Presenter {
        fun onCoDiSent(msg: String)
        fun onErrorService(error: String)
    }

    interface Iterator {
        fun decypherMsjCbr(msjCobro: Mensaje_Cobro_Cifrado_Data): String

        fun sendMoney(objCbr: Mensaje_Cobro_Decipher)
    }
}