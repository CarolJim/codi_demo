package com.pagatodo.yaganaste.modules.money_notification

import com.pagatodo.yaganaste.dtos.Notif_Info_Cif
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Cifrado_Data
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Decipher

class MoneyNotificationContracts {

    interface Presenter {
        fun onCoDiSent(msg: String)
        fun onErrorService(error: String)
        fun onDecypherCharge19(msgDesc: String)
    }

    interface Iterator {
        fun decypherMsjCbr(msjCobro: Mensaje_Cobro_Cifrado_Data): String
        fun decypherMsjCbrPresencial(msjCobro: Notif_Info_Cif): String
        fun sendMoney(objCbr: Mensaje_Cobro_Decipher)
    }
}