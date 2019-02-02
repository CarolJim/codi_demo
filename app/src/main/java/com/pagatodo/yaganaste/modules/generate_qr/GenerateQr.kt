package com.pagatodo.yaganaste.modules.generate_qr

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.ActivityGenerateQrBinding
import com.pagatodo.yaganaste.watchers.AmountTextWatcher

class GenerateQr : AppCompatActivity(), GenerateQrContracts.Presenter, View.OnClickListener {

    private lateinit var binding: ActivityGenerateQrBinding
    private val iterator = GenerateQrIterator(this)
    private val router = GenerateQrRouter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_qr)
        var textWatcher = AmountTextWatcher(binding.txtAmount.editText)
        binding.txtAmount.editText!!.addTextChangedListener(textWatcher)
        binding.txtAmount.editText!!.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                textWatcher.deleteText = true
            }
            false
        }
        binding.btnGenerateQr.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnGenerateQr.id -> {
                val amount = binding.txtAmount.editText!!.text.toString()
                if (amount.isNotEmpty() && amount.replace("$", "").toFloat() > 8000) {
                    UI().showErrorSnackBar(this, "Favor de verificar el monto", Snackbar.LENGTH_SHORT)
                } else if (binding.txtConcept.editText!!.text.isEmpty()) {
                    UI().showErrorSnackBar(this, "Favor de verificar el concepto", Snackbar.LENGTH_SHORT)
                } else {

                }
            }
        }
    }
}