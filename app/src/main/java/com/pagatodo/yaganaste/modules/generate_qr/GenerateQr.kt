package com.pagatodo.yaganaste.modules.generate_qr

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.nfc.*
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
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private lateinit var writeTagFilters: Array<IntentFilter>
    private var writeMode = false
    private val iterator = GenerateQrIterator(this)
    private val router = GenerateQrRouter(this)
    private lateinit var myTag: Tag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_qr)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
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
        if (nfcAdapter == null) {
            binding.btnGenerateNfc.visibility = GONE
        }
        pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT)
        writeTagFilters = arrayOf(tagDetected)
    }

    override fun onQrGenerated(bitmap: Bitmap) {
        binding.imgQrGenerated.visibility = VISIBLE
        binding.imgQrGenerated.setImageBitmap(bitmap)
    }

    override fun onError(msg: String) {
        UI().showErrorSnackBar(this, msg, Snackbar.LENGTH_SHORT)
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        }
    }

    public override fun onPause() {
        super.onPause()
        WriteModeOff()
    }

    public override fun onResume() {
        super.onResume()
        WriteModeOn()
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
                    val amountDouble = if (amount.isEmpty()) 0.toDouble() else amount.replace("$", "").toDouble()
                    iterator.generateQr(amountDouble, binding.txtConcept.editText!!.text.toString(), binding.imgQrGenerated.height)
                }
            }
            binding.btnGenerateNfc.id -> {
                val amount = binding.txtAmount.editText!!.text.toString()
                if (amount.isNotEmpty() && amount.replace("$", "").toFloat() > 8000) {
                    UI().showErrorSnackBar(this, "Favor de verificar el monto", Snackbar.LENGTH_SHORT)
                } else if (binding.txtConcept.editText!!.text.isEmpty()) {
                    UI().showErrorSnackBar(this, "Favor de verificar el concepto", Snackbar.LENGTH_SHORT)
                } else {
                    val amountDouble = if (amount.isEmpty()) 0.toDouble() else amount.replace("$", "").toDouble()
                    if (nfcAdapter.isEnabled) {
                        Toast.makeText(this, "Compartiendo mensaje por NFC", Toast.LENGTH_LONG).show()
                        val nfc = iterator.generateNfc(amountDouble, binding.txtConcept.editText!!.text.toString())
                        write(nfc, myTag)
                    } else {
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

    /******************************************************************************
     * Enable Write********************************
     */
    private fun WriteModeOn() {
        writeMode = true
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null)
    }

    /******************************************************************************
     * Disable Write*******************************
     */
    private fun WriteModeOff() {
        writeMode = false
        nfcAdapter.disableForegroundDispatch(this)
    }
}