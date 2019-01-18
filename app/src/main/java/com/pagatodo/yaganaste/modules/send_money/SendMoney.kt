package com.pagatodo.yaganaste.modules.send_money

import android.os.Bundle
import android.view.KeyEvent
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.BANKS_JSON
import com.pagatodo.yaganaste.databinding.ActivitySendMoneyBinding
import com.pagatodo.yaganaste.dtos.Banks
import com.pagatodo.yaganaste.watchers.AmountTextWatcher
import com.pagatodo.yaganaste.watchers.CardTextWatcher
import org.json.JSONArray

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
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, strings.sorted())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnBanks.adapter = adapter
    }
}
