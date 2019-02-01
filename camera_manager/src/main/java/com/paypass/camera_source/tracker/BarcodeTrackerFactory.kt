package com.paypass.camera_source.tracker

import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode

class BarcodeTrackerFactory(var context: BarcodeTracker.BarcodeGraphicTrackerCallback) : MultiProcessor.Factory<Barcode> {

    override fun create(barcode: Barcode): Tracker<Barcode> {
        return BarcodeTracker(context)
    }
}