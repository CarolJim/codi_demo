package com.pagatodo.richardsproject.modules.send_money

import android.app.Activity
import android.app.Activity.RESULT_OK

class SendMoneyRouter : SendMoneyContracts.Router {

    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentMainScreen() {
        activity.setResult(RESULT_OK)
        activity.finish()
    }
}