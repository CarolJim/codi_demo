package com.pagatodo.yaganaste.modules.generate_qr

import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.BuildConfig.CODI_BANK_ID
import com.pagatodo.yaganaste.commons.*
import com.pagatodo.yaganaste.dtos.*
import javax.crypto.Cipher

class GenerateQrIterator(private val presenter: GenerateQrContracts.Presenter) : GenerateQrContracts.Iterator {

    override fun generateQr(amount: Double, concept: String, dimen: Int) {
        /* Identificador del mensaje de cobro generado conforme al Anexo C “Generación de identificadores para los mensajes de cobro presenciales” */
        val IDC = Utils.getCodiNewId("")
        val codiDecypher = CoDi_Decypher(IDC, concept, amount, 1, System.currentTimeMillis(), 19,
                V_Decypher(App.getPreferences().loadData(FULL_NAME_USER), App.getPreferences().loadData(CLABE_NUMBER).replace(" ", ""),
                        CODI_BANK_ID.toLong(), 0, App.getPreferences().loadData(PHONE_NUMBER) + "/" + App.getPreferences().loadData(CODI_DV).toInt()))
        val cspv = Utils.Sha512Hex(IDC + App.getPreferences().loadData(CODI_KEYSOURCE) + App.getPreferences().loadData(CODI_SER))
        val cobroStr = Gson().toJson(codiDecypher)
        val jsonCipher = Utils.Aes128CbcPkcs(cspv.substring(0, 32), cspv.substring(32, 64), cobroStr, Cipher.ENCRYPT_MODE)
        val CRY = Utils.HmacSha256(cspv.substring(64, 128), App.getPreferences().loadData(PHONE_NUMBER) + App.getPreferences().loadData(CODI_DV) +
                App.getPreferences().loadData(CODI_SER) + cobroStr)
        /* Se genera el último objeto correspondiente al mensaje de cobro para después ser visualizado en un código QR */
        val codi = CoDi(19, V(App.getPreferences().loadData(PHONE_NUMBER) + "/" + App.getPreferences().loadData(CODI_DV).toInt()),
                IC(IDC, App.getPreferences().loadData(CODI_SER).toInt(), jsonCipher!!), CRY)
        val qrCodeEncoder = QrcodeGenerator(Gson().toJson(codi), null, BarcodeFormat.QR_CODE.toString(), dimen)
        try {
            presenter.onQrGenerated(qrCodeEncoder.encodeAsBitmap())
        } catch (e: Exception) {
            e.printStackTrace()
            presenter.onError("No se pudo crear el mensaje de cobro")
        }
    }
}