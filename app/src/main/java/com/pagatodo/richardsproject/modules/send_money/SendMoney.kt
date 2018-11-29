package com.pagatodo.richardsproject.modules.send_money

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.pagatodo.richardsproject.R
import com.pagatodo.richardsproject.commons.Constants
import com.pagatodo.richardsproject.databinding.ActivitySendMoneyBinding
import com.pagatodo.richardsproject.dtos.Banks
import com.pagatodo.richardsproject.watchers.CardTextWatcher
import com.pagatodo.richardsproject.watchers.AmountTextWatcher
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SendMoney : AppCompatActivity(), SendMoneyContracts.Presenter {

    lateinit var binding: ActivitySendMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_money)
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
        val jsonArray = JSONArray(Constants().BANKS_JSON)
        val list = ArrayList<Banks>()
        val strings = ArrayList<String>()
        for (i in 0..jsonArray.length() step 1) {
            var bank = Banks()
            bank.id = jsonArray.getJSONObject(i).getInt("id")
            bank.banco = jsonArray.getJSONObject(i).getString("banco")
            strings.add(bank.banco)
            list.add(bank)
        }
    }
}
