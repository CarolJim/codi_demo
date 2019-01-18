package com.pagatodo.yaganaste.modules.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.richardsproject.R
import com.pagatodo.richardsproject.commons.Constants
import com.pagatodo.richardsproject.commons.DateUtil
import com.pagatodo.richardsproject.commons.UI
import com.pagatodo.richardsproject.databinding.ActivityRegisterBinding
import com.pagatodo.richardsproject.dtos.Counties
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

class Register : AppCompatActivity(), RegisterContracts.Presenter, View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var router: RegisterContracts.Router
    private lateinit var iteractor: RegisterContracts.Iteractor
    private lateinit var countiesList: List<Counties>
    private lateinit var newDate: Calendar
    private lateinit var actualDate: Calendar
    private var fechaNacimiento: String? = ""
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        iteractor = RegisterIteractor(this)
        router = RegisterRouter(this)
        initViews()
    }

    override fun initViews() {
        actualDate = Calendar.getInstance(Locale("es"))
        if (fechaNacimiento != null && !fechaNacimiento.equals("")) {
            newDate = Calendar.getInstance(Locale("es"))
            try {
                val format = SimpleDateFormat(DateUtil.simpleDateFormatFirstYear, Locale("es"))
                newDate.time = format.parse(fechaNacimiento)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        /* Bank json to object */
        val jsonArray = JSONArray(Constants().COUNTIES_JSON)
        val list = ArrayList<Counties>()
        val strings = ArrayList<String>()
        for (i in 0 until jsonArray.length() - 1 step 1) {
            var county = Counties()
            county.ID_EntidadNacimiento = jsonArray.getJSONObject(i).getString("ID_EntidadNacimiento")
            county.Nombre = jsonArray.getJSONObject(i).getString("Nombre")
            county.clave = jsonArray.getJSONObject(i).getString("clave")
            strings.add(county.Nombre)
            list.add(county)
        }
        countiesList = list.sortedWith(compareBy { it.Nombre })
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, strings.sorted())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnBirthPlace.adapter = adapter
        binding.textInputLayout6.setOnClickListener(this)
        binding.edtBirthDate.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun showLoader(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onUserCreated() {
        UI().showSuccessSnackBar(this, "Usuario creado", Snackbar.LENGTH_SHORT)
        /* Equivalente al Handler().postDelay() en java */
        Timer("GoToMainScreen", false).schedule(1500) {
            router.presentMainScreen()
        }
    }

    override fun onErrorService(message: String) {
        UI().showErrorSnackBar(this, message, Snackbar.LENGTH_SHORT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        router.presentOnboardingScreen()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.edtBirthDate.id -> {
                val newCalendar = Calendar.getInstance()
                year = 0
                month = 0
                day = 0
                if (fechaNacimiento != null && !fechaNacimiento.equals("")) {
                    year = newDate.get(Calendar.YEAR)
                    month = newDate.get(Calendar.MONTH)
                    day = newDate.get(Calendar.DAY_OF_MONTH)
                } else {
                    year = newCalendar.get(Calendar.YEAR)
                    month = newCalendar.get(Calendar.MONTH)
                    day = newCalendar.get(Calendar.DAY_OF_MONTH)
                }
                openCalendarDialog()
            }
            binding.btnRegister.id -> {
                var name = binding.edtName.text.toString().trim().toUpperCase()
                var surname = binding.edtSurname.text.toString().trim().toUpperCase()
                var secondSurname = binding.edtSecondSurname.text.toString().trim().toUpperCase()
                var email = binding.edtEmailRegister.text.toString().trim()
                var countyId = countiesList[binding.spnBirthPlace.selectedItemPosition].ID_EntidadNacimiento
                var claveBirth = countiesList[binding.spnBirthPlace.selectedItemPosition].clave
                var genre = if (binding.rdbMale.isChecked) "H" else if (binding.rdbMale.isChecked) "M" else ""
                iteractor.registerUserClient(name, surname, secondSurname, email, fechaNacimiento!!, countyId, genre, claveBirth)
            }
        }
    }

    private fun openCalendarDialog() {
        val datePickerDialog = DatePickerDialog(this, AlertDialog.THEME_TRADITIONAL, DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
            newDate = Calendar.getInstance(Locale("es"))
            newDate.set(year, month, date)

            fechaNacimiento = DateUtil.getDateStringFirstYear(newDate)

            val mCalendar = Calendar.getInstance()
            mCalendar.set(actualDate.get(Calendar.YEAR) - 18, actualDate.get(Calendar.MONTH), actualDate.get(Calendar.DAY_OF_MONTH))

            if (newDate.timeInMillis > mCalendar.timeInMillis) {
                //  editBirthDay.setIsInvalid();
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
                binding.edtBirthDate.setText("")
                UI().showErrorSnackBar(this, "Debes ser mayor de edad para continuar", Snackbar.LENGTH_SHORT)
                return@OnDateSetListener
            } else {
                binding.edtBirthDate.setText(DateUtil.getBirthDateSpecialCustom(year, month, date))
            }
        }, year, month, day)
        datePickerDialog.show()
    }
}
