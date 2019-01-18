package com.pagatodo.yaganaste.net

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


open class API_Banxico {

    fun getCustomService(): GetAPIService {
        val builder = Retrofit.Builder().baseUrl("https://www.banxico.org.mx/pagospei-beta/")
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .build()
        return builder.create(GetAPIService::class.java)
    }

    interface GetAPIService {
        @POST("registroInicial")
        fun getRegistroInicial(@Body d: RequestBody): Call<RegistroInicial_Result>

        @POST("registroSubsecuente")
        fun getRegistroDispositivo(@Body d: RequestBody): Call<RegistroDispositivo_Result>

        @POST("registraAppPorOmision")
        fun getRegistroDispositivoPorOmision(@Body d: RequestBody): Call<RegistroDispositivoPorOmision_Result>

        @POST("bajaDispositivos")
        fun getBajaDispotivo(@Body request: RequestBody): Callback<BajaDispositivos_Result>

        @POST("consulta")
        fun getConsultaMensajeDeCobro(@Body d: RequestBody): Callback<Consulta_Result>
    }
}