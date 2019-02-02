package com.pagatodo.yaganaste.modules.generate_qr

class GenerateQrContracts {

    interface Presenter {

    }

    interface Iterator {
        fun generateQr(amount: Double, concept: String, folio: String)
    }

    interface Router {

    }
}