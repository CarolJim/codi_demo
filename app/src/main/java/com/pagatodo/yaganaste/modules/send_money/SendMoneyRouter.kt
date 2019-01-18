package com.pagatodo.yaganaste.modules.send_money

import android.app.Activity

class SendMoneyRouter : SendMoneyContracts.Router {

    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentMainScreen() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }
}