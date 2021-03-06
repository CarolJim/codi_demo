package com.pagatodo.yaganaste.modules.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.network_manager.utils.RequestHeaders
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.HAS_SESSION
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.ActivityLogInBinding

class LogIn : AppCompatActivity(), LogInContracts.Presenter, View.OnClickListener {

    private lateinit var pswd: String
    private lateinit var user: String
    private lateinit var bindView: ActivityLogInBinding
    private lateinit var iteractor: LogInContracts.Iteractor
    private lateinit var router: LogInContracts.Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        iteractor = LogInIteractor(this)
        router = LogInRouter(this)
        bindView.btnContinue.setOnClickListener(this)
        val hasSession = App.getPreferences().loadDataBoolean(HAS_SESSION, false)
        val userName = RequestHeaders.getUsername()
        if (hasSession && userName.isNotEmpty()) {
            bindView.edtEmail.setText(userName)
            bindView.edtEmail.isEnabled = false
            bindView.edtPassword.requestFocus()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_continue -> validateFields()
        }
    }

    private fun validateFields() {
        user = bindView.edtEmail.text.toString()
        pswd = bindView.edtPassword.text.toString()

        if (user.isEmpty()) {
            onErrorService(getString(R.string.datos_usuario_correo))
        } else if (pswd.isEmpty()) {
            onErrorService(getString(R.string.password_required))
        } else if (!RequestHeaders.getTokensesion().isEmpty()) {
            iteractor.closeSession()
        } else {
            iteractor.startSession(user, pswd)
        }
    }

    override fun onLogIn() {
        router.presentMainScreen()
    }

    override fun onLogOut() {
        iteractor.startSession(user, pswd)
    }

    override fun onErrorService(message: String) {
        UI().showErrorSnackBar(this, message, Snackbar.LENGTH_LONG)
    }

    override fun showLoader(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.presentOnboarding()
    }
}
