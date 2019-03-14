package com.pagatodo.yaganaste.modules.main

import android.app.Activity
import android.content.Intent
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.commons.RC_SEND_MONEY
import com.pagatodo.yaganaste.modules.generate_qr.GenerateQr
import com.pagatodo.yaganaste.modules.send_money.SendMoney

class MainRouter : MainContracts.Router {
    override fun presentConsultValidationScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentSendMoneyScreen() {
        activity.startActivityForResult(Intent(App.getContext(), SendMoney::class.java), RC_SEND_MONEY)
    }

    override fun presentGenerateQrScreen() {
        activity.startActivity(Intent(App.getContext(), GenerateQr::class.java))
    }
}
