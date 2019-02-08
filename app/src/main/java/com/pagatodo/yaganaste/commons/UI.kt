package com.pagatodo.yaganaste.commons

import android.app.Activity
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.pagatodo.yaganaste.R

class UI {

    fun showErrorSnackBar(rootView: Activity, message: String, length: Int) {
        val snack = Snackbar.make(rootView.window.decorView, message, length)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        view.layoutParams = params
        view.setBackgroundColor(rootView.resources.getColor(R.color.colorRedTransparent))
        snack.show()
    }

    fun showSuccessSnackBar(rootView: Activity, message: String, length: Int) {
        val snack = Snackbar.make(rootView.window.decorView, message, length)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        view.layoutParams = params
        view.setBackgroundColor(rootView.resources.getColor(R.color.colorGreenTransparent))
        snack.show()
    }
}