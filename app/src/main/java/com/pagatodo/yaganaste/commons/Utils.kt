package com.pagatodo.yaganaste.commons

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.pagatodo.yaganaste.App
import com.pagatodo.yaganaste.dtos.Info_Cuenta
import com.pagatodo.yaganaste.net.banxico.ValidacionCuentasDecryp_Data
import org.apache.commons.codec.binary.Hex
import java.math.BigInteger
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.xor


class Utils {

    companion object {

        fun isValidCoDi(content: String): Boolean {
            return (content.contains("TYP") && content.contains("IDC") && content.contains("DEV") && content.contains("ic")
                    && content.contains("SER") && content.contains("ENC") && content.contains("CRY"))
        }

        fun getTokenDevice(context: Context): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun cipherRSA(text: String, rsaKey: String): String? {
            var result: String?
            try {
                val expBytes = Base64.decode("AQAB".toByteArray(charset("UTF-8")), Base64.DEFAULT)
                val modBytes = Base64.decode(rsaKey.toByteArray(charset("UTF-8")), Base64.DEFAULT)

                val modules = BigInteger(1, modBytes)
                val exponent = BigInteger(1, expBytes)

                val factory = KeyFactory.getInstance("RSA")
                val cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING")

                val pubSpec = RSAPublicKeySpec(modules, exponent)

                val pubKey = factory.generatePublic(pubSpec)
                cipher.init(Cipher.ENCRYPT_MODE, pubKey)
                val encrypted = cipher.doFinal(text.toByteArray())
                result = Base64.encodeToString(encrypted, Base64.DEFAULT)
            } catch (e: Exception) {
                result = null
            }

            return result
        }

        fun Aes128CbcPkcs(key: String, initVector: String, value: String, mode: Int): String? {
            var result: String?
            try {
                val iv = IvParameterSpec(Hex.decodeHex(initVector.toCharArray()))
                val skeySpec = SecretKeySpec(Hex.decodeHex(key.toCharArray()), "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(mode, skeySpec, iv)
                if (mode == Cipher.ENCRYPT_MODE) {
                    val encrypted = cipher.doFinal(value.toByteArray())
                    result = Base64.encodeToString(encrypted, Base64.DEFAULT)
                } else {
                    val original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT))
                    result = String(original)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                result = null
            }

            return result
        }

        fun Sha512Hex(input: String): String {
            try {
                val md = MessageDigest.getInstance("SHA-512")
                val messageDigest = md.digest(input.toByteArray())
                val no = BigInteger(1, messageDigest)
                var hashtext = no.toString(16)
                while (hashtext.length < 32) {
                    hashtext = "0$hashtext"
                }
                return hashtext
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }

        }

        @Throws(Exception::class)
        fun HmacSha256(key: String, data: String): String {
            val sha256_HMAC = Mac.getInstance("HmacSHA256")
            val secret_key = SecretKeySpec(Hex.decodeHex(key.toCharArray()), "HmacSHA256")
            sha256_HMAC.init(secret_key)
            val encrypted = sha256_HMAC.doFinal(data.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT).trim()
        }

        fun validateDv(dv: String): String {
            return when (dv.length) {
                1 -> "00".plus(dv)
                2 -> "0".plus(dv)
                else -> dv
            }
        }

        fun getCodiNewId(readQrId: String): String {
            val cal = Calendar.getInstance()
            cal.firstDayOfWeek = Calendar.MONDAY
            cal.timeInMillis = System.currentTimeMillis()
            val year = cal.get(Calendar.YEAR)
            val yearTwoDigits = cal.get(Calendar.YEAR) % 100
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minutes = cal.get(Calendar.MINUTE)
            val seconds = cal.get(Calendar.SECOND)
            val millisencond = cal.get(Calendar.MILLISECOND)
            var newId = 0
            newId = (newId shl 7) + yearTwoDigits
            newId = (newId shl 4) + month
            newId = (newId shl 5) + day
            var newIdHex = Integer.toHexString(newId)
            while (newIdHex.length < 4) {
                newIdHex = "0$newIdHex"
            }
            newId = 0
            newId = (newId shl 5) + hour
            newId = (newId shl 6) + minutes
            newId = (newId shl 6) + seconds
            newId = (newId shl 7) + millisencond
            var secondIdHex = Integer.toHexString(newId)
            while (secondIdHex.length < 6) {
                secondIdHex = "0$secondIdHex"
            }
            return readQrId + newIdHex + secondIdHex
        }

        fun XOR(objOne: String, objTwo: String): String {
            var finalString = ""
            val arrayOne = Hex.decodeHex(objOne.toCharArray())
            val arrayTwo = Hex.decodeHex(objTwo.toCharArray())
            var byteArray = ByteArray(arrayOne.size)
            var i = 0
            while (i < arrayOne.size) {
                byteArray[i] = arrayOne[i] xor arrayTwo[i]
                i++
            }
            val charArray = Hex.encodeHex(byteArray)
            var j = 0
            while (j < charArray.size) {
                finalString += charArray[j]
                j++
            }
            return finalString
        }

        fun getNumericReference(): Long {
            val cal = Calendar.getInstance()
            cal.firstDayOfWeek = Calendar.MONDAY
            cal.timeInMillis = System.currentTimeMillis()
            val yearTwoDigits = cal.get(Calendar.YEAR) % 100
            val month = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DAY_OF_MONTH)
            return "$yearTwoDigits$month$day".toLong()
        }

        fun isAppIsInBackground(context: Context): Boolean {
            Log.e(this.javaClass.simpleName, "isAppIsInBackground: " + "show")
            var isInBackground = true
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                val runningProcesses = am.runningAppProcesses
                for (processInfo in runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && processInfo.processName == context.packageName) {
                        for (activeProcess in processInfo.pkgList) {
                            if (activeProcess == context.packageName) {
                                isInBackground = false
                            }
                        }
                    }
                }
            } else {
                val taskInfo = am.getRunningTasks(1)
                val componentInfo = taskInfo[0].topActivity
                if (componentInfo.packageName == context.packageName) {
                    isInBackground = false
                }
            }

            return isInBackground
        }

        /**
         * Método empleado para procesar el cifrado traido desde la notificación por Banxico
         */
        fun processAccountValidation(infoCuenta: Info_Cuenta): String {
            val dechyperPush = Aes128CbcPkcs(App.getPreferences().loadData(CODI_KEYSOURCE_VALIDATION_ACC).substring(0, 32),
                    App.getPreferences().loadData(CODI_KEYSOURCE_VALIDATION_ACC).substring(32, 64), infoCuenta.infCif, Cipher.DECRYPT_MODE)
            val data = Gson().fromJson(dechyperPush, ValidacionCuentasDecryp_Data::class.java)
            return when (data.rv) {
                0 -> "Cuenta pendiente de verificar"
                1 -> {
                    App.getPreferences().saveDataBool(HAS_REGISTER_TO_SEND_CODI, true)
                    "Cuenta verificada correctamente"
                }
                3 -> "No fue posible realizar la verificación"
                else -> ""
            }
        }
    }
}