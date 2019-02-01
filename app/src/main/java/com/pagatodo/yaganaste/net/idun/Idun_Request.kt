package com.pagatodo.yaganaste.net.idun

import com.google.gson.annotations.SerializedName

class CollectiveWireTranfer_Request(@SerializedName("account") val account: Long, @SerializedName("reference") val reference: String,
                                    @SerializedName("amount") val amount: Double, @SerializedName("concept") val concept: String,
                                    @SerializedName("bank") val bank: String, @SerializedName("beneficiary") val beneficiary: String,
                                    @SerializedName("numericReference") val numericReference: Long, @SerializedName("type") val type: Int,
                                    @SerializedName("typeReference") val typeReference: String, @SerializedName("phonePayer") val phonePayer: String,
                                    @SerializedName("digitPayer") val digitPayer: Long, @SerializedName("phoneBeneficiary") val phoneBeneficiary: String,
                                    @SerializedName("digitBeneficiary") val digitBeneficiary: Long, @SerializedName("numberCollectSpei") val numberCollectSpei: String,
                                    @SerializedName("certificateSerie") val certificateSerie: String)