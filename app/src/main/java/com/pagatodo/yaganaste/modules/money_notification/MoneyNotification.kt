package com.pagatodo.yaganaste.modules.money_notification

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.databinding.ActivityMoneyNotificationBinding
import com.pagatodo.yaganaste.dtos.Notif_Info_Dec
import com.pagatodo.yaganaste.dtos.Notification
import com.pagatodo.yaganaste.net.banxico.Mensaje_Cobro_Decipher

class MoneyNotification : AppCompatActivity(), MoneyNotificationContracts.Presenter {
    override fun onDecypherCharge19(msgDesc: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private lateinit var binding: ActivityMoneyNotificationBinding
    private lateinit var notification: Notification
    private val iterator = MoneyNotificationIterator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_money_notification)
        if (intent.extras != null) {
            notification = intent.getParcelableExtra(INTENT_PUSH_NOTIFICATION)
            setContentInfo()
        }
    }

    override fun onCoDiSent(msg: String) {
        UI().showSuccessSnackBar(this, msg, Snackbar.LENGTH_LONG)
        Handler().postDelayed({ finish() }, 1500)
    }

    override fun onErrorService(error: String) {
        UI().showErrorSnackBar(this, error, Snackbar.LENGTH_SHORT)
    }

    private fun setContentInfo() {
        Log.e(TAG_CODI, "Notification: $notification")
        when {
            notification.info != null -> {
                val msgDec = iterator.decypherMsjCbrPresencial(notification.info!!.infoCif)

                val infodec = Gson().fromJson(msgDec, Notif_Info_Dec::class.java)

                binding.txtTitleMoneyNotification.text = infodec.vendedor.nb//.vendedor.nb
                binding.txtBodyMoneyNotification.text = notification.body
                binding.txtAmountMoneyNotification.text = "$${infodec.monto}"//"$${notification.info!!.monto}"
                binding.txtCrMoneyNotification.text = "Clave Rastreo: ${infodec.claveRastreo}"//"Clave Rastreo: ${notification.info!!.claveRastreo}"
                binding.btnGenericMoneyNotification.text = "Cerrar"
                binding.btnGenericMoneyNotification.setOnClickListener { finish() }
                when (infodec.estadoOperacion) {
                    PENDIENTE -> {
                        binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayTransparent))
                        binding.txtCrMoneyNotification.text.toString().plus("\nEstatus: PENDIENTE")
                    }
                    ACREDITADA -> {
                        binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreenTransparent))
                        binding.txtCrMoneyNotification.text.toString().plus("\nEstatus: ACREDITADA")
                    }
                    RECHAZADA -> {
                        binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRedTransparent))
                        binding.txtCrMoneyNotification.text.toString().plus("\nEstatus: RECHAZADA")
                    }
                    DEVUELTA -> {
                        binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorYellowTransparent))
                        binding.txtCrMoneyNotification.text.toString().plus("\nEstatus: DEVUELTA")
                    }
                }

            }
            notification.infoCuenta != null -> {
                binding.txtTitleMoneyNotification.text = notification.title
                binding.txtBodyMoneyNotification.text = Utils.processAccountValidation(notification.infoCuenta!!)
                binding.txtAmountMoneyNotification.visibility = INVISIBLE
                binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayTransparent))
                binding.txtCrMoneyNotification.visibility = GONE
                binding.btnGenericMoneyNotification.text = "Cerrar"
                binding.btnGenericMoneyNotification.setOnClickListener { finish() }
            }
            notification.payreq != null -> {
                val msjCbr = iterator.decypherMsjCbr(notification.payreq!!.infoCif)
                val objCobroNoPresencial = Gson().fromJson(msjCbr, Mensaje_Cobro_Decipher::class.java)
                binding.txtTitleMoneyNotification.text = objCobroNoPresencial.v.nb
                binding.txtBodyMoneyNotification.text = notification.body
                binding.txtAmountMoneyNotification.text = "$${objCobroNoPresencial.mt}"
                binding.txtCrMoneyNotification.text = "Concepto: ${objCobroNoPresencial.cc}"
                binding.btnGenericMoneyNotification.text = "Pagar"
                binding.btnGenericMoneyNotification.setOnClickListener {
                    iterator.sendMoney(objCobroNoPresencial)
                    Toast.makeText(this, "Enviando dinero", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
