package com.pagatodo.yaganaste.net.banxico

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * 6 Conexión con CoDi

6.1 Registo Inicial de Dispositivos AMBIENTE URL
PRUEBAS   https://www.banxico.org.mx/pagospei-beta/registroInicial
PRODUCCIÓN https://www.banxico.org.mx/codi/registroInicial

6.2 Registro Subsecuente de Dispositivos AMBIENTE URL
PRUEBAS  https://www.banxico.org.mx/pagospei-beta/registroSubsecuente
PRODUCCIÓN https://www.banxico.org.mx/codi/registroSubsecuente

6.3 Registo de App CoDi o App bancaria por omisión para operación No Presencial AMBIENTE URL
PRUEBAS  https://www.banxico.org.mx/pagospei-beta/registraAppPorOmision
PRODUCCIÓN https://www.banxico.org.mx/codi/registraAppPorOmision

6.4 Baja de dispositivos AMBIENTE URL
BETA  https://dspappbeta:9090/pagoSPEI
PRODUCCIÓN https://dspapp:9090/codi/bajaDispositivos

6.5 Catálogo de Instituciones participantes en SPEI AMBIENTE URL
PRUEBAS  https://www.banxico.org.mx/pagospei-beta/instituciones
PRODUCCIÓN https://www.banxico.org.mx/codi/instituciones

6.6 Solicitud de Validación de Cuentas Beneficiarias AMBIENTE URL
PRUEBAS https://www.banxico.org.mx/pagospei-beta/validacionCuenta
PRODUCCIÓN https://www.banxico.org.mx/codi/validacionCuenta

6.7 Consulta del estado de la Validación de Cuentas Beneficiarias AMBIENTE URL
PRUEBAS https://www.banxico.org.mx/pagospei-beta/consultaValidacionCuenta
PRODUCCIÓN https://www.banxico.org.mx/codi/consultaValidacionCuenta

6.8 Consulta de Mensajes de Cobro AMBIENTE URL
PRUEBAS https://www.banxico.org.mx/pagospei-beta/consulta
PRODUCCIÓN https://www.banxico.org.mx/codi/consulta

6.9 Servicio de consulta de claves de descifrado AMBIENTE URL
PRUEBAS https://www.banxico.org.mx/pagospei-beta/solicitaClaveDescifradoMC
PRODUCCIÓN https://www.banxico.org.mx/codi/solicitaClaveDescifradoMC

6.11  Envío de Mensajes de Cobro AMBIENTE URL
PRUEBAS  https://www.banxico.org.mx/pagospei-beta/solicitudCobro
PRODUCCIÓN https://www.banxico.org.mx/codi/solicitudCobro


6.12  Consulta de Mensajes de Cobro AMBIENTE URL
PRUEBAS  https://www.banxico.org.mx/pagospei-beta/consultaEstadoOperacion
PRODUCCIÓN https://www.banxico.org.mx/codi/consultaEstadoOperacion
 */

open class API_Banxico {

    fun getCustomService(): GetBanxicoService {
        val builder = Retrofit.Builder().baseUrl("https://www.banxico.org.mx/pagospei-beta/")
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .build()
        return builder.create(GetBanxicoService::class.java)
    }

    interface GetBanxicoService {
        @POST("registroInicial")
        fun getRegistroInicial(@Body d: RequestBody): Call<RegistroInicial_Result>

        @POST("registroSubsecuente")
        fun getRegistroDispositivo(@Body d: RequestBody): Call<RegistroDispositivo_Result>

        @POST("registraAppPorOmision")
        fun getRegistroDispositivoPorOmision(@Body d: RequestBody): Call<RegistroDispositivoPorOmision_Result>

        @POST("bajaDispositivos")
        fun getBajaDispotivo(@Body request: RequestBody): Call<BajaDispositivos_Result>

        @POST("consulta")
        fun getConsultaMensajeDeCobro(@Body d: RequestBody): Call<Consulta_Result>

        @POST("solicitaClaveDescifradoMC")
        fun getClaveDescifrado(@Body d: RequestBody): Call<SolicitudClaveDescifrado_Result>

        @POST("validacionCuenta")
        fun getValidacionCuentasBeneficiarias(@Body d: RequestBody): Call<ValidacionCuentasBeneficiarias_Result>

        @POST("consultaValidacionCuenta")
        fun getConsultaValidacionCuentasBeneficiarias(@Body d: RequestBody): Call<ConsultaValidacionCuentasBeneficiarias_Result>

        @POST("consultaEstadoOperacion")
        fun consultaEstadoOperacion(@Body d: RequestBody): Call<consultaEstadoOperacion_Result>

    }
}