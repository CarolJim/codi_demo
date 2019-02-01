package com.paypass.camera_source.tracker

import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode


class BarcodeTracker(var listener: BarcodeGraphicTrackerCallback) : Tracker<Barcode>() {

    interface BarcodeGraphicTrackerCallback {
        fun onDetectedQrCode(barcode: Barcode)
    }

    override fun onNewItem(id: Int, item: Barcode?) {
        if (item!!.displayValue != null) {
            listener.onDetectedQrCode(item)
        }
    }
}