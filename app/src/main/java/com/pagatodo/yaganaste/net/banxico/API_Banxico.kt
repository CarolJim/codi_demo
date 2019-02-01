package com.pagatodo.yaganaste.net.banxico

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


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
    }
}