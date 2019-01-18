package com.pagatodo.yaganaste.dialogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.DialogEnterCodiVerificationCodeBinding
import com.pagatodo.yaganaste.modules.main.MainContracts

class DialogVerificationCode : DialogFragment(), View.OnClickListener {

    private lateinit var activity: Activity
    private lateinit var binding: DialogEnterCodiVerificationCodeBinding
    lateinit var listener: MainContracts.Presenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_enter_codi_verification_code, container, false)
        binding.btnVerifyCode.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnVerifyCode.id -> {
                when {
                    binding.edtVerificationCode.text.toString().isEmpty() -> UI().showErrorSnackBar(activity, "Favor de ingresar el código de verificación", Snackbar.LENGTH_SHORT)
                    binding.edtVerificationCode.text.toString().length<6 -> UI().showErrorSnackBar(activity, "El código de verificación debe tener 6 dígitos", Snackbar.LENGTH_SHORT)
                    else -> listener.onVerifyCode(binding.edtVerificationCode.text.toString())
                }
            }
        }
    }
}
