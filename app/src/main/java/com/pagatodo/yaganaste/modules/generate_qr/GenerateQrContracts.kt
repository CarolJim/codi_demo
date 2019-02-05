package com.pagatodo.yaganaste.modules.generate_qr

import android.graphics.Bitmap

class GenerateQrContracts {

    interface Presenter {
        fun onQrGenerated(bitmap: Bitmap)
        fun onError(msg: String)
    }

    interface Iterator {
        fun generateQr(amount: Double, concept: String, dimen: Int)
    }

    interface Router {

    }
}