package com.pagatodo.yaganaste.modules.send_money

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.BANKS_JSON
import com.pagatodo.yaganaste.commons.UI
import com.pagatodo.yaganaste.commons.Utils
import com.pagatodo.yaganaste.databinding.ActivitySendMoneyBinding
import com.pagatodo.yaganaste.dtos.Banks
import com.pagatodo.yaganaste.dtos.CoDi
import com.pagatodo.yaganaste.watchers.AmountTextWatcher
import com.pagatodo.yaganaste.watchers.CardTextWatcher
import com.paypass.camera_source.camera.CameraSource
import com.paypass.camera_source.tracker.BarcodeTracker
import com.paypass.camera_source.tracker.BarcodeTrackerFactory
import org.json.JSONArray
import java.io.IOException
import java.io.UnsupportedEncodingException

class SendMoney : AppCompatActivity(), SendMoneyContracts.Presenter, View.OnClickListener, BarcodeTracker.BarcodeGraphicTrackerCallback {

    // Permission request codes need to be < 256
    private val RC_HANDLE_CAMERA_PERM = 2
    private val RC_HANDLE_GMS = 9001

    private lateinit var binding: ActivitySendMoneyBinding
    private lateinit var bankList: List<Banks>
    private lateinit var iteractor: SendMoneyContracts.Iteractor
    private lateinit var router: SendMoneyContracts.Router

    private val autoFocus = true
    private var useFlash = false
    private var qrScanned = false
    private var mCameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_money)
        iteractor = SendMoneyIteractor(this)
        router = SendMoneyRouter(this)
        initViews()
    }

    override fun initViews() {
        var textWatcher = AmountTextWatcher(binding.edtAmountSendMoney)
        binding.edtAmountSendMoney.addTextChangedListener(textWatcher)
        binding.edtAmountSendMoney.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                textWatcher.deleteText = true
            }
            false
        }
        var cardTextWatcher = CardTextWatcher(binding.edtCardNumber, 19)
        binding.edtCardNumber.addTextChangedListener(cardTextWatcher)
        /* Bank json to object */
        val jsonArray = JSONArray(BANKS_JSON)
        val list = ArrayList<Banks>()
        val strings = ArrayList<String>()
        for (i in 0 until jsonArray.length() - 1 step 1) {
            var bank = Banks()
            bank.id = jsonArray.getJSONObject(i).getInt("id")
            bank.banco = jsonArray.getJSONObject(i).getString("banco")
            strings.add(bank.banco)
            list.add(bank)
        }
        bankList = list.sortedWith(compareBy { it.banco })
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, strings.sorted())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnBanks.adapter = adapter
        binding.btnActionSendMoney.setOnClickListener(this)
        binding.imgQrCode.setOnClickListener(this)
        binding.imgNfcCode.setOnClickListener(this)
        binding.imgNfcCode.visibility = GONE
        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }
    }

    override fun onClick(v: View?) {
        var cardNumber = binding.edtCardNumber.text.toString().replace(" ", "")
        var name = binding.edtNameBeneficiary.text.toString().trim()
        var amount = cleanAmount()
        var idBank = bankList[binding.spnBanks.selectedItemPosition].id
        when (v?.id) {
            binding.btnActionSendMoney.id -> iteractor.sendMoney(cardNumber, amount, name, idBank)
            binding.imgQrCode.id -> {
                binding.txtDescScanQr.visibility = GONE
                binding.imgQrCode.visibility = GONE
                binding.cameraScanQr.visibility = VISIBLE
            }
            binding.imgNfcCode.id -> {
                Toast.makeText(this, "Favor de prender su NFC", Toast.LENGTH_LONG).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
                } else {
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }
            }
        }
    }

    override fun showLoader(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDetectedQrCode(barcode: Barcode) {
        if (barcode != null && !qrScanned) {
            val content = barcode.displayValue
            if (Utils.isValidCoDi(content)) {
                qrScanned = true
                val qr = Gson().fromJson(content, CoDi::class.java)
                iteractor.proccessQrRead(qr)
            } else {
                UI().showErrorSnackBar(this, getString(R.string.invalid_qr_code), Snackbar.LENGTH_SHORT)
            }
        }
    }

    override fun onMoneySent() {
        router.presentMainScreen()
    }

    override fun onErrorService(message: String) {
        qrScanned = false
        UI().showErrorSnackBar(this, message, Snackbar.LENGTH_SHORT)
    }

    private fun cleanAmount(): String {
        return binding.edtAmountSendMoney.text.toString().trim().replace("$", "")
                .replace(",", "")
    }

    override fun onCoDiSent(message: String) {
        UI().showSuccessSnackBar(this, "Track Key: $message", Snackbar.LENGTH_SHORT)
        Handler().postDelayed({ finish() }, 1500)
    }

    // Handles the requesting of the camera permission.
    private fun requestCameraPermission() {
        Log.w(this.javaClass.simpleName, "Camera permission is not granted. Requesting permission")
        val permissions = arrayOf(Manifest.permission.CAMERA)
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
        }
    }

    /**
     * Creates and starts the camera.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private fun createCameraSource(autoFocus: Boolean, useFlash: Boolean) {
        val context = applicationContext
        val barcodeDetector = BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
        val barcodeFactory = BarcodeTrackerFactory(this)
        barcodeDetector.setProcessor(MultiProcessor.Builder(barcodeFactory).build())

        if (!barcodeDetector.isOperational) {
            Log.w(this.javaClass.simpleName, "Detector dependencies are not yet available.")
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null
            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show()
                Log.w(this.javaClass.simpleName, getString(R.string.low_storage_error))
            }
        }
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var builder = CameraSource.Builder(applicationContext, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null)
        }
        mCameraSource = builder.setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_ON else Camera.Parameters.FLASH_MODE_OFF)
                .build()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(this.javaClass.simpleName, "Got unexpected permission result: $requestCode")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(this.javaClass.simpleName, "Camera permission granted - initialize the camera source")
            // we have permission, so create the camerasource
            createCameraSource(autoFocus, useFlash)
            return
        }

        Log.e(this.javaClass.simpleName, "Permission not granted: results len = " + grantResults.size +
                " Result qid = " + if (grantResults.isNotEmpty()) grantResults[0] else "(empty)")
        val listener = DialogInterface.OnClickListener { dialog, id -> finish() }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escanear Usuario").setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show()
    }

    // Restarts the camera
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    // Stops the camera
    override fun onPause() {
        super.onPause()
        if (binding.cameraScanQr != null) {
            binding.cameraScanQr!!.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding.cameraScanQr != null) {
            binding.cameraScanQr!!.release()
        }
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() {
        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }
        if (mCameraSource != null) {
            try {
                binding.cameraScanQr!!.start(mCameraSource)
            } catch (e: IOException) {
                Log.e(this.javaClass.simpleName, "Unable to start camera source.", e)
                mCameraSource!!.release()
                mCameraSource = null
            }
        }
    }

    private fun readFromIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action
                || NfcAdapter.ACTION_TECH_DISCOVERED == action
                || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var msgs = arrayOf<NdefMessage>()
            if (rawMsgs != null) {
                for (i in rawMsgs.indices) {
                    msgs.plus(rawMsgs[i] as NdefMessage)
                }
            }
            buildTagViews(msgs)
        }
    }

    private fun buildTagViews(msgs: Array<NdefMessage>?) {
        if (msgs == null || msgs.isEmpty()) return
        var text = ""
        val payload = msgs[0].records[0].payload
        try {
            // Get the Text
            text = payload.toString(Charsets.UTF_8)
        } catch (e: UnsupportedEncodingException) {
            Log.e("UnsupportedEncoding", e.toString())
        }
        Log.e(this.javaClass.simpleName, "NFC TEXT: $text")
    }
}
