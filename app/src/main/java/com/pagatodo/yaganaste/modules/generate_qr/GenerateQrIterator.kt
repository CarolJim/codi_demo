package com.pagatodo.yaganaste.modules.generate_qr

import com.google.gson.Gson
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.dtos.CoDi_Decypher
import com.pagatodo.yaganaste.dtos.V_Decypher

class GenerateQrIterator(private val presenter: GenerateQrContracts.Presenter) : GenerateQrContracts.Iterator {

    override fun generateQr(amount: Double, concept: String, folio: String) {
        /* Identificador del mensaje de cobro generado conforme al Anexo C “Generación de identificadores para los mensajes de cobro presenciales” */
        val IDC = Utils.getCodiNewId("")
        val codiDecypher = CoDi_Decypher(IDC, concept, amount, 1, System.currentTimeMillis(), 19,
                V_Decypher(App.getPreferences().loadData(FULL_NAME_USER), App.getPreferences().loadData(CLABE_NUMBER).replace(" ", ""),
                        CODI_BANK_ID.toLong(), 0, App.getPreferences().loadData(PHONE_NUMBER) + "/" + App.getPreferences().loadData(CODI_DV).toInt()))
        val cspv = Utils.Sha512Hex(IDC + App.getPreferences().loadData(CODI_KEYSOURCE) + App.getPreferences().loadData(CODI_SER))
        val jsonCoDi = Gson().toJson(codiDecypher)
    }
}