package com.pagatodo.yaganaste.modules.onboarding

import android.app.Activity
import android.content.Intent
import com.pagatodo.yaganaste.modules.login.LogIn
import com.pagatodo.yaganaste.modules.register.Register

class OnboardingRouter : OnboardingContracts.Router {

    val activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentRegisterScreen() {
        activity.startActivity(Intent(activity, Register::class.java))
        activity.finish()
    }

    override fun presentLogInScreen() {
        activity.startActivity(Intent(activity, LogIn::class.java))
        activity.finish()
    }
}