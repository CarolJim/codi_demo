package com.pagatodo.richardsproject.modules.login

import android.app.Activity
import android.content.Intent
import com.pagatodo.richardsproject.modules.main.MainActivity
import com.pagatodo.richardsproject.modules.onboarding.Onboarding

class LogInRouter : LogInContracts.Router {

    var activity : Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentOnboarding() {
        activity.startActivity(Intent(activity, Onboarding::class.java))
        activity.finish()
    }

    override fun presentMainScreen() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }
}