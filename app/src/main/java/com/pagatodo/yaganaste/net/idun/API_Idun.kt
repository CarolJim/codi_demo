package com.pagatodo.yaganaste.net.idun

import com.pagatodo.yaganaste.BuildConfig
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

open class API_Idun {

    fun getCustomService(): GetIdunService {
        val builder = Retrofit.Builder().baseUrl(BuildConfig.IDUN_URL)
                .addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create())
                .build()
        return builder.create(GetIdunService::class.java)
    }

    interface GetIdunService {
        @POST("CollectWireTransfer")
        fun getCollectiveWireTransfer(@Body requestBody: RequestBody): Call<CollectWireTransfer_Result>
    }
}