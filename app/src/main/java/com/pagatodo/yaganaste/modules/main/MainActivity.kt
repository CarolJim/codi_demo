package com.pagatodo.yaganaste.modules.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.databinding.ActivityMainBinding
import com.pagatodo.yaganaste.dialogs.DialogVerificationCode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, MainContracts.Presenter {

    private lateinit var binding: ActivityMainBinding
    private lateinit var router: MainContracts.Router
    private lateinit var iteractor: MainContracts.Iteractor
    private lateinit var dialogVerification: DialogVerificationCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Initialize objects */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        router = MainRouter(this)
        iteractor = MainIteractor(this)
        /* Toolbar */
        setSupportActionBar(binding.toolbarMain)
        supportActionBar!!.title = ""
        /* Init views */
        binding.btnSendMoney.setOnClickListener(this)
        binding.rcvRecentMovements.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        /* Se registra un broadcast para esperar la obtención de Token de Firebase */
        registerReceiver(myBroadcastReceiver, IntentFilter(INTENT_TOKEN_FIREBASE))
        /* Verify Codi values */
        Log.e(TAG_CODI, HAS_REGISTER_CODI + ": " + App.getPreferences().loadDataBoolean(HAS_REGISTER_CODI, false))
        if (!App.getPreferences().loadDataBoolean(HAS_REGISTER_CODI, false)) {
            iteractor.registerCodi()
        } else {

        }
    }

    override fun onResume() {
        super.onResume()
        iteractor.getBalance()
        iteractor.getMovements()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnSendMoney.id -> router.presentSendMoneyScreen()
        }
    }

    override fun updateMovements(movements: List<MovementsItemResult>) {
        if (movements.isNotEmpty()) {
            binding.textView3.text = "Últimos movimientos"
            binding.rcvRecentMovements.adapter = MovementsAdapter(movements, this)
        } else {
            rcv_recent_movements.visibility = View.GONE
            binding.textView3.text = "No se encontraron movimientos recientes"
        }
    }

    override fun updateBalance(balance: String) {
        binding.txtBalance.text = balance
    }

    override fun onErrorService(message: String) {
        UI().showErrorSnackBar(this, message, Snackbar.LENGTH_SHORT)
    }

    override fun showLoader(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterCodiSuccess() {
        dialogVerification = DialogVerificationCode()
        dialogVerification.listener = this
        dialogVerification.show(supportFragmentManager, DialogVerificationCode::class.java.simpleName)
    }

    override fun onVerifyCode(code: String) {
        /** Se genera un SHA 512 con el código de verificación recibido SMS, el id Hardware, el nombre del paquete de
         * la App y el número de teléfono */
        val sha512 = Utils.Sha512Hex(Utils.Sha512Hex(code) + Utils.getTokenDevice(App.getContext()) + "-" +
                BuildConfig.APPLICATION_ID + App.getPreferences().loadData(PHONE_NUMBER))
        /** Se guarda en preferencias los arreglos de bytes correspondientes a la llave AES, el vector de inicialización
         * de AES y la llave para el cifrado HMAC */
        App.getPreferences().saveDataInt(CODI_VERIFICATION_CODE, code.toInt())
        App.getPreferences().saveData(CODI_KEY_AES, sha512.substring(0, 32))
        App.getPreferences().saveData(CODI_IV_AES, sha512.substring(32, 64))
        App.getPreferences().saveData(CODI_KEY_HMAC, sha512.substring(64, 128))
        /** Se procede a registrar el Dispositivo con el Web Service Registro Subsecuente */
        iteractor.registerToPushService()
    }

    override fun onRegisterPhoneSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SEND_MONEY && resultCode == Activity.RESULT_OK) {
            UI().showSuccessSnackBar(this, "Envío realizado", Snackbar.LENGTH_SHORT)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(myBroadcastReceiver)
        super.onDestroy()
    }

    private val myBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                INTENT_TOKEN_FIREBASE -> iteractor.registerDeviceCodi()
            }
        }
    }
}