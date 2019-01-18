package com.pagatodo.yaganaste.modules.onboarding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.databinding.ActivityOnboardingBinding

class Onboarding : AppCompatActivity(), View.OnClickListener {

    private var router = OnboardingRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bindView: ActivityOnboardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        bindView.btnLogIn.setOnClickListener(this)
        bindView.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_log_in -> router.presentLogInScreen()
            R.id.btn_register -> router.presentRegisterScreen()
        }
    }
}
