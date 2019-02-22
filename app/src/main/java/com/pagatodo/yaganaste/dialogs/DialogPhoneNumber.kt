package com.pagatodo.yaganaste.dialogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.PHONE_NUMBER
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.DialogEnterPhoneNumberBinding
import com.pagatodo.yaganaste.modules.main.MainContracts

class DialogPhoneNumber : DialogFragment(), View.OnClickListener {

    private lateinit var activity: Activity
    private lateinit var binding: DialogEnterPhoneNumberBinding
    lateinit var listener: MainContracts.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_enter_phone_number, container, false)
        binding.btnVerifyCode.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnVerifyCode.id -> {
                when {
                    binding.edtVerificationCode.text.toString().isEmpty() -> UI().showErrorSnackBar(activity, "Favor de ingresar el número de celular", Snackbar.LENGTH_SHORT)
                    binding.edtVerificationCode.text.toString().length < 10 -> UI().showErrorSnackBar(activity, "El número de celular debe tener 10 dígitos", Snackbar.LENGTH_SHORT)
                    else -> {
                        Toast.makeText(activity, "Solicitando código", Toast.LENGTH_SHORT).show()
                        App.getPreferences().saveData(PHONE_NUMBER, binding.edtVerificationCode.text.toString())
                        listener.onVerifyPhoneNumber()
                        dismiss()
                    }
                }
            }
        }
    }
}
