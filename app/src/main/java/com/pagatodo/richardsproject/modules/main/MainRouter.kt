package com.pagatodo.richardsproject.modules.main

import android.app.Activity
import android.content.Intent
import com.pagatodo.richardsproject.App
import com.pagatodo.richardsproject.commons.Constants
import com.pagatodo.richardsproject.modules.send_money.SendMoney

class MainRouter : MainContracts.Router {

    var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentSendMoneyScreen() {
        activity.startActivityForResult(Intent(App.getContext(), SendMoney::class.java), Constants().RC_SEND_MONEY )
    }
}