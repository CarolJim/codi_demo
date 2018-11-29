package com.pagatodo.richardsproject.modules.main

import android.app.Activity

class MainRouter : MainContracts.Router {

    var activity : Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentSendMoneyScreen() {
        //activity.
    }
}