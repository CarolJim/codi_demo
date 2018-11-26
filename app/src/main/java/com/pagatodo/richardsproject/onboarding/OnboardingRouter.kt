package com.pagatodo.richardsproject.onboarding

import android.app.Activity
import android.content.Intent
import com.pagatodo.richardsproject.login.LogIn
import com.pagatodo.richardsproject.register.Register

class OnboardingRouter : OnboardingContracts.Router {

    val activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun presentRegisterScreen() {
        activity.startActivity(Intent(activity, Register::class.java))
    }

    override fun presentLogInScreen() {
        activity.startActivity(Intent(activity, LogIn::class.java))
    }
}