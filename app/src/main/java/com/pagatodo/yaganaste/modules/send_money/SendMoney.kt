package com.pagatodo.yaganaste.modules.send_money

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.richardsproject.R
import com.pagatodo.richardsproject.commons.Constants
import com.pagatodo.richardsproject.commons.UI
import com.pagatodo.richardsproject.databinding.ActivitySendMoneyBinding
import com.pagatodo.richardsproject.dtos.Banks
import com.pagatodo.richardsproject.watchers.AmountTextWatcher
import com.pagatodo.richardsproject.watchers.CardTextWatcher
import com.pagatodo.yaganaste.R
import com.pagatodo.yaganaste.commons.BANKS_JSON
import com.pagatodo.yaganaste.databinding.ActivitySendMoneyBinding
import com.pagatodo.yaganaste.dtos.Banks
import com.pagatodo.yaganaste.watchers.AmountTextWatcher
import com.pagatodo.yaganaste.watchers.CardTextWatcher
import org.json.JSONArray

class SendMoney : AppCompatActivity(), SendMoneyContracts.Presenter, View.OnClickListener {

    private lateinit var binding: ActivitySendMoneyBinding
    private lateinit var bankList: List<Banks>
    private lateinit var iteractor: SendMoneyContracts.Iteractor
    private lateinit var router: SendMoneyContracts.Router

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
    }

    override fun onClick(v: View?) {
        var cardNumber = binding.edtCardNumber.text.toString().replace(" ", "")
        var name = binding.edtNameBeneficiary.text.toString().trim()
        var amount = cleanAmount()
        var idBank = bankList[binding.spnBanks.selectedItemPosition].id
        when (v?.id) {
            binding.btnActionSendMoney.id -> iteractor.sendMoney(cardNumber, amount, name, idBank)
        }
    }

    override fun showLoader(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMoneySent() {
        router.presentMainScreen()
    }

    override fun onErrorSevice(message: String) {
        UI().showErrorSnackBar(this, message, Snackbar.LENGTH_SHORT)
    }

    private fun cleanAmount(): String {
        return binding.edtAmountSendMoney.text.toString().trim().replace("$", "")
                .replace(",", "")
    }
}
