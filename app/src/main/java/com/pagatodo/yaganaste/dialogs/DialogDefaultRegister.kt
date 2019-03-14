package com.pagatodo.yaganaste.dialogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.databinding.DialogDefaultRegisterBinding
import com.pagatodo.yaganaste.dtos.CoDi_Decypher
import com.pagatodo.yaganaste.modules.main.MainContracts

class DialogDefaultRegister : DialogFragment(), View.OnClickListener {

    private lateinit var activity: Activity
    private lateinit var binding: DialogDefaultRegisterBinding
    lateinit var listener: MainContracts.Presenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_default_register, container, false)
        binding.btnAcceptDefaultRegister.setOnClickListener(this)
        binding.btnCancelDefault.setOnClickListener(this)
        this.isCancelable = false
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnAcceptDefaultRegister.id -> {
                listener.onAcceptDefaultRegister()
                dismiss()
            }
            binding.btnCancelDefault.id -> {
                listener.onCancelDefaultRegster()
                listener.onRegisterPhoneSuccess(false)
                dismiss()
            }
        }
    }
}
