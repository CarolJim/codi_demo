package com.pagatodo.yaganaste.modules.generate_qr

import android.content.Intent
import android.graphics.Bitmap
import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.databinding.ActivityGenerateQrBinding
import com.pagatodo.yaganaste.watchers.AmountTextWatcher
import java.io.IOException
import java.io.UnsupportedEncodingException

class GenerateQr : AppCompatActivity(), GenerateQrContracts.Presenter, View.OnClickListener {

    private lateinit var binding: ActivityGenerateQrBinding
    private var writeMode = false
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
        binding.btnGenerateNfc.setOnClickListener(this)
        binding.btnGenerateNfc.visibility = GONE
    }

    override fun onQrGenerated(bitmap: Bitmap) {
        binding.imgQrGenerated.visibility = VISIBLE
        binding.imgQrGenerated.setImageBitmap(bitmap)
    }

    override fun onError(msg: String) {
        UI().showErrorSnackBar(this, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnGenerateQr.id -> {
                val amount = binding.txtAmount.editText!!.text.toString()
                if (amount.isNotEmpty() && amount.replace("$", "").replace(",", "").toFloat() > 8000) {
                    UI().showErrorSnackBar(this, "Favor de verificar el monto", Snackbar.LENGTH_SHORT)
                } else if (binding.txtConcept.editText!!.text.isEmpty()) {
                    UI().showErrorSnackBar(this, "Favor de verificar el concepto", Snackbar.LENGTH_SHORT)
                } else {
                    val amountDouble = if (amount.isEmpty()) 0.toDouble() else amount.replace("$", "")
                            .replace(",", "").toDouble()
                    iterator.generateQr(amountDouble, binding.txtConcept.editText!!.text.toString(), binding.imgQrGenerated.height)
                }
            }
            binding.btnGenerateNfc.id -> {
                val amount = binding.txtAmount.editText!!.text.toString()
                if (amount.isNotEmpty() && amount.replace("$", "").replace(",", "").toFloat() > 8000) {
                    UI().showErrorSnackBar(this, "Favor de verificar el monto", Snackbar.LENGTH_SHORT)
                } else if (binding.txtConcept.editText!!.text.isEmpty()) {
                    UI().showErrorSnackBar(this, "Favor de verificar el concepto", Snackbar.LENGTH_SHORT)
                } else {
                    val amountDouble = if (amount.isEmpty()) 0.toDouble() else amount.replace("$", "")
                            .replace(",", "").toDouble()
                    Toast.makeText(this, "Favor de prender su NFC", Toast.LENGTH_LONG).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                    } else {
                        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                    }
                }
            }
        }
    }

    @Throws(IOException::class, FormatException::class)
    private fun write(text: String, tag: Tag) {
        val records = arrayOf(createRecord(text))
        val message = NdefMessage(records)
        // Get an instance of Ndef for the tag.
        val ndef = Ndef.get(tag)
        // Enable I/O
        ndef.connect()
        // Write the message
        ndef.writeNdefMessage(message)
        // Close the connection
        ndef.close()
    }

    @Throws(UnsupportedEncodingException::class)
    private fun createRecord(text: String): NdefRecord {
        val lang = "en"
        val textBytes = text.toByteArray()
        val langBytes = lang.toByteArray(charset("US-ASCII"))
        val langLength = langBytes.size
        val textLength = textBytes.size
        val payload = ByteArray(1 + langLength + textLength)
        // set status byte (see NDEF spec for actual bits)
        payload[0] = langLength.toByte()
        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength)
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength)
        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload)
    }
}