package com.pagatodo.yaganaste.modules.money_notification

import android.os.Bundle
import android.view.View.GONE
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.databinding.ActivityMoneyNotificationBinding
import com.pagatodo.yaganaste.dtos.Notification

class MoneyNotification : AppCompatActivity() {

    private lateinit var binding: ActivityMoneyNotificationBinding
    private lateinit var notification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_money_notification)
        if (intent.extras != null) {
            notification = intent.getParcelableExtra(INTENT_PUSH_NOTIFICATION)
            setContentInfo()
        }
    }

    private fun setContentInfo() {
        if (notification.notifInfo != null) {
            binding.txtTitleMoneyNotification.text = notification.notifInfo!!.vendedor.nb
            binding.txtBodyMoneyNotification.text = notification.body
            binding.txtAmountMoneyNotification.text = "$${notification.notifInfo!!.monto}"
            binding.txtCrMoneyNotification.text = "Clave Rastreo: ${notification.notifInfo!!.claveRastreo}"
            binding.btnGenericMoneyNotification.text = "Cerrar"
            binding.btnGenericMoneyNotification.setOnClickListener { finish() }
            when (notification.notifInfo!!.estadoOperacion) {
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
        } else if (notification.infoCuenta != null) {
            binding.txtTitleMoneyNotification.text = notification.title
            binding.txtBodyMoneyNotification.text = Utils.processAccountValidation(notification.infoCuenta!!)
            binding.txtAmountMoneyNotification.visibility = INVISIBLE
            binding.ctlMoneyNotification.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrayTransparent))
            binding.txtCrMoneyNotification.visibility = GONE
            binding.btnGenericMoneyNotification.text = "Cerrar"
            binding.btnGenericMoneyNotification.setOnClickListener { finish() }
        }
    }
}
