package com.pagatodo.richardsproject.modules.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.network_manager.dtos.sender_yg.results.MovementsItemResult
import com.pagatodo.richardsproject.R
import com.pagatodo.richardsproject.commons.UI
import com.pagatodo.richardsproject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, MainContracts.Presenter {

    private lateinit var binding: ActivityMainBinding
    private lateinit var router: MainContracts.Router
    private lateinit var iteractor: MainContracts.Iteractor

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
        binding.rcvRecentMovements.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        iteractor.getBalance()
        iteractor.getMovements()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_money -> router.presentSendMoneyScreen()
        }
    }

    override fun updateMovements(movements: List<MovementsItemResult>) {
        if (movements.isNotEmpty())
            binding.rcvRecentMovements.adapter = MovementsAdapter(movements, this)
        else {
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
}