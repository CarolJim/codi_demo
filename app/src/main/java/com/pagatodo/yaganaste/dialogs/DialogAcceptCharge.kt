package com.pagatodo.yaganaste.dialogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.PHONE_NUMBER
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.DialogAcceptChargeBinding
import com.pagatodo.yaganaste.dtos.CoDi
import com.pagatodo.yaganaste.dtos.CoDi_Decypher
import com.pagatodo.yaganaste.modules.send_money.SendMoneyContracts

class DialogAcceptCharge : DialogFragment(), View.OnClickListener {

    private lateinit var activity: Activity
    private lateinit var binding: DialogAcceptChargeBinding
    lateinit var listener: SendMoneyContracts.Presenter
    lateinit var codiDecypher: CoDi_Decypher
    private var setAmount = false

    fun initializeInfo(){
        val vendedor = codiDecypher.v.nam
        val concepto = codiDecypher.des
        val monto = codiDecypher.amo

        binding.textView.text ="Realizar pago"
        binding.txtBeneficiary.text = "$vendedor"
        binding.txtConcept.text = "$concepto"
        binding.txtAmount.text = "$$monto"

        if (monto == 0.0 || monto == null){
            setAmount = true
            binding.editTxtAmount.visibility = VISIBLE
            binding.txtAmount.visibility = GONE
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_accept_charge, container, false)
        binding.btnAcceptCharge.setOnClickListener(this)
        binding.btnCancelCharge.setOnClickListener(this)
        initializeInfo()
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnAcceptCharge.id -> {
                //Toast.makeText(activity, "Aceptar Pago", Toast.LENGTH_SHORT).show()
                if (setAmount){
                    if (binding.editTxtAmount.text.isEmpty()) {
                        listener.onErrorService("Debes introducir un monto")
                       // dismiss()
                    }else{
                        val stringAmount = binding.editTxtAmount.text.toString()
                        codiDecypher.amo= stringAmount.toDouble()
                        listener.onAcceptCodiTransfer(codiDecypher)
                        dismiss()
                    }
                }else {
                    listener.onAcceptCodiTransfer(codiDecypher)
                    dismiss()
                }
            }
            binding.btnCancelCharge.id -> {
                listener.onErrorService("Haz rechazado la operación")
                dismiss()
            }
        }
    }
}
