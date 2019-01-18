package com.pagatodo.yaganaste.modules.register

import android.app.Activity
import android.content.Intent
import com.pagatodo.yaganaste.modules.main.MainActivity
import com.pagatodo.yaganaste.modules.onboarding.Onboarding

class RegisterRouter : RegisterContracts.Router {

    private var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentMainScreen() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()
    }

    override fun presentOnboardingScreen() {
        activity.startActivity(Intent(activity, Onboarding::class.java))
        activity.finish()
    }
}