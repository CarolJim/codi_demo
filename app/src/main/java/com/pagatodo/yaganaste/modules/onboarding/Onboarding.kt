package com.pagatodo.yaganaste.modules.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.HAS_SESSION
import com.pagatodo.yaganaste.commons.INTENT_PUSH_NOTIFICATION
import com.pagatodo.yaganaste.databinding.ActivityOnboardingBinding
import com.pagatodo.yaganaste.dtos.Notification
import com.pagatodo.yaganaste.modules.money_notification.MoneyNotification

class Onboarding : AppCompatActivity(), View.OnClickListener {

    private var router = OnboardingRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bindView: ActivityOnboardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        bindView.btnLogIn.setOnClickListener(this)
        bindView.btnRegister.setOnClickListener(this)
        if (intent.extras != null && intent.extras.get(INTENT_PUSH_NOTIFICATION) is Notification) {
            val intent = Intent(this, MoneyNotification::class.java)
            val notification = this.intent.extras.getParcelable<Notification>(INTENT_PUSH_NOTIFICATION)
            intent.putExtra(INTENT_PUSH_NOTIFICATION, notification)
            startActivity(intent)
            finish()
        } else if (App.getPreferences().loadDataBoolean(HAS_SESSION, false)) {
            router.presentLogInScreen()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_log_in -> router.presentLogInScreen()
            R.id.btn_register -> router.presentRegisterScreen()
        }
    }
}
