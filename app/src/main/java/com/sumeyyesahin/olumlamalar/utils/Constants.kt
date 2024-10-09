package com.sumeyyesahin.olumlamalar.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import com.sumeyyesahin.olumlamalar.R

object Constants {
    private val originalButtonColors = mutableMapOf<View, Int>()
    fun getColors(context:Context):List<Int>{
        return listOf(
            getColor(context, R.color.soft_light_coral),
            getColor(context, R.color.soft_light_lavender),
            getColor(context, R.color.soft_light_mint_green),
            getColor(context, R.color.soft_light_peach),
            getColor(context, R.color.soft_light_pink),
            getColor(context, R.color.soft_light_sky_blue),
            getColor(context, R.color.soft_light_yellow)
        )
    }

    fun getRandomSoftColor(context: Context):Int{
        val colors = getColors(context)
        return colors.random()
    }

    @RequiresApi(Build.VERSION_CODES.N)
     fun changeButtonBackgroundColor(button: View) {
        val randomColor = Constants.getRandomSoftColor(button.context)
        val background = button.background

        if (background is LayerDrawable) {
            for (i in 0 until background.numberOfLayers) {
                val layer = background.getDrawable(i)
                if (layer is GradientDrawable) {
                    if (!originalButtonColors.containsKey(button)) {
                        originalButtonColors[button] = (layer.color?.defaultColor ?: Color.TRANSPARENT)
                    }
                    layer.setColor(randomColor)
                    Handler(Looper.getMainLooper()).postDelayed({
                        layer.setColor(originalButtonColors[button] ?: randomColor)
                    }, 1000)
                    return
                }
            }
            Log.e("MainActivity", "GradientDrawable bulunamadı.")
        } else {
            Log.e("MainActivity", "LayerDrawable değil: ${background?.javaClass?.name}")
        }
    }


}