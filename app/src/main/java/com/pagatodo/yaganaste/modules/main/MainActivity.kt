package com.pagatodo.yaganaste.modules.main

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.pagatodo.network_manager.WsConsumer
import com.pagatodo.network_manager.apis.GenericApi
import com.pagatodo.network_manager.apis.SenderApi
import com.pagatodo.network_manager.dtos.sender_yg.results.LogOutResult
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult
import com.pagatodo.network_manager.utils.RequestHeaders
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.databinding.ActivityMainBinding
import com.pagatodo.yaganaste.dialogs.DialogDefaultRegister
import com.pagatodo.yaganaste.dialogs.DialogPhoneNumber
import com.pagatodo.yaganaste.dialogs.DialogVerificationCode
import com.pagatodo.yaganaste.modules.login.LogIn
import com.pagatodo.yaganaste.modules.login.LogInIteractor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener, MainContracts.Presenter {

    private lateinit var binding: ActivityMainBinding

    private lateinit var router: MainContracts.Router
    private lateinit var iteractor: MainContracts.Iteractor
    private lateinit var dialogVerification: DialogVerificationCode

    override fun onLogOut() {
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);

        /*example RSASSA-PKCS1-v1_5*/
        //Utils.firm_RSASSA_PKCS1_v1_5()

        /* Initialize objects */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        router = MainRouter(this)
        iteractor = MainIteractor(this)
        /* Toolbar */
        setSupportActionBar(binding.toolbarMain)
        supportActionBar!!.title = ""
        /* Init views */
        binding.btnSendMoney.setOnClickListener(this)
        binding.btnGenerateQr.setOnClickListener(this)
        binding.btnConsultValidation.setOnClickListener(this)
        binding.btnValidateAccount.setOnClickListener(this)
        binding.rcvRecentMovements.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        /* Se registra un broadcast para esperar la obtención de Token de Firebase */
        registerReceiver(myBroadcastReceiver, IntentFilter(INTENT_TOKEN_FIREBASE))
        /* Verify Codi values */
        Log.e(TAG_CODI, HAS_REGISTER_TO_RECEIVE_CODI + ": " + App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_RECEIVE_CODI, false))
        if (!App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_RECEIVE_CODI, false)) {
            val dialogPhoneNumber = DialogPhoneNumber()
            dialogPhoneNumber.listener = this
            dialogPhoneNumber.show(supportFragmentManager, DialogPhoneNumber::class.java.simpleName)
        } else {
            iteractor.registerToPushService()
        }
    }

    override fun onResume() {
        super.onResume()
        iteractor.getBalance()
        iteractor.getMovements()
        if (App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_SEND_CODI, false)) {
            binding.btnGenerateQr.visibility = VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_clean_data -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(null)
                builder.setMessage("¿Desea borrar los datos de sesión y CoDi?")
                        .setPositiveButton("Aceptar") { dialog, id ->
                            /*if (App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_RECEIVE_CODI, false)) {
                                iteractor.unsubscribeCodi()
                            } else {*/
                            App.getPreferences().clearPreferences()
                            finish()
                            //}
                        }
                        .setNegativeButton("Cancelar") { dialog, id ->
                            // User cancelled the dialog
                        }
                builder.create()

            }

            R.id.consult_codi_charges -> {
                iteractor.consultStatusCoDiCharges(1)
            }

            R.id.consult_codi_statusaccount -> {
                iteractor.consultRegisterBankAccountCoDi()
            }

            R.id.logOut -> {
               iteractor.closeSession()

            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnSendMoney.id -> router.presentSendMoneyScreen()
            binding.btnGenerateQr.id -> router.presentGenerateQrScreen()
            binding.btnConsultValidation.id -> iteractor.consultRegisterBankAccountCoDi()
            binding.btnValidateAccount.id -> {
                ///* Registrar cuenta para poder generar mensajes de Cobro si aún no se ha realizado la validación de cuentas beneficiarias */
                if (!App.getPreferences().loadDataBoolean(HAS_REGISTER_TO_SEND_CODI, false)) {
                    iteractor.registerBankAccountCoDi()
                    Log.e(TAG_CODI, "Iniciando proceso para validar cuenta beneficiaria")
                }else{
                    UI().showSuccessSnackBar(this, "Ya se ha validado esta cuenta",Snackbar.LENGTH_SHORT)
                    binding.btnValidateAccount.visibility = GONE
                }
            }


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

    override fun onVerifyPhoneNumber() {
        iteractor.registerCodi()
    }

    override fun onValidationSucces(mensaje: String, showBtn: Boolean) {
        UI().showSuccessSnackBar(this, mensaje, Snackbar.LENGTH_LONG)
        if (showBtn) {
            binding.btnConsultValidation.visibility = VISIBLE
            binding.btnValidateAccount.visibility = GONE
            binding.btnGenerateQr.visibility = GONE

        }
        else {
            binding.btnConsultValidation.visibility = GONE
            binding.btnValidateAccount.visibility = VISIBLE
            binding.btnGenerateQr.visibility = VISIBLE
        }
    }

    override fun onVerifyCode(code: String) {
        /** Se genera un SHA 512 con el código de verificación recibido SMS, el id Hardware, el nombre del paquete de
         * la App y el número de teléfono */
        val sha512 = Utils.Sha512Hex(Utils.Sha512Hex(code) + Utils.getTokenDevice(App.getContext()) + "-" +
                BuildConfig.APPLICATION_ID + App.getPreferences().loadData(PHONE_NUMBER))
        /** Se guarda en preferencias los arreglos de bytes correspondientes a la llave AES, el vector de inicialización
         * de AES y la llave para el cifrado HMAC */
        App.getPreferences().saveDataInt(CODI_VERIFICATION_CODE, code.toInt())
        App.getPreferences().saveData(CODI_KEYSOURCE, sha512)
        /** Se procede a registrar el Dispositivo con el Web Service Registro Subsecuente */
        iteractor.registerToPushService()
    }


    override fun onRegisterPhoneSuccess(nopresential: Boolean) {
        if (nopresential){
            UI().showSuccessSnackBar(this, "Registrado correctamente para recepción de CoDi", Snackbar.LENGTH_LONG)
        }else {
            UI().showSuccessSnackBar(this, "Registrado correctamente para recepción de CoDi (Esquema presencial)", Snackbar.LENGTH_LONG)
        }

        binding.btnValidateAccount.visibility= VISIBLE
    }

    override fun onRegisterOmitionSuccess(){
        UI().showSuccessSnackBar(this, "Registrado correctamente para recepción de CoDi (Registro por omisión)", Snackbar.LENGTH_LONG)
    }

    override fun unregisterReceiver() {
        unregisterReceiver(myBroadcastReceiver)
    }

    override fun onRequiredOmitionRegister() {
        val dialog = DialogDefaultRegister()
        dialog.listener = this;
        dialog.show(supportFragmentManager, this::class.java.simpleName)
        //UI().showSuccessSnackBar(this, "Registro por omisión", Snackbar.LENGTH_LONG)
        Log.e(TAG_CODI, "Dialogo Registro por omisión")
        //iteractor.registerDeviceOmisionCodi()

    }

    override fun onCancelDefaultRegster() {
        App.getPreferences().saveDataBool(CODI_DEFAULT_DEVICE, false)
        this.onErrorService("Para cambiar la configuración dirigete al menú")
    }

    override fun onAcceptDefaultRegister() {
        iteractor.registerDeviceOmisionCodi()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SEND_MONEY && resultCode == Activity.RESULT_OK) {
            UI().showSuccessSnackBar(this, "Envío realizado", Snackbar.LENGTH_SHORT)
        }
    }

    override fun onDestroy() {
        try {
            unregisterReceiver(myBroadcastReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

    private val myBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent!!.action) {
                INTENT_TOKEN_FIREBASE -> {
                    unregisterReceiver(this)
                    iteractor.registerDeviceCodi()
                }
            }
        }
    }
}