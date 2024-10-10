package com.sumeyyesahin.olumlamalar.utils

import android.widget.Button

object ClikedButton {
    fun clickedButton(button: Button){
        button.alpha = 0.5f
        button.postDelayed({
            button.alpha = 1f
        }, 300)
    }
}