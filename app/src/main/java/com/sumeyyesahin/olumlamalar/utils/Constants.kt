package com.sumeyyesahin.olumlamalar.utils

import android.content.Context
import androidx.core.content.ContextCompat.getColor
import com.sumeyyesahin.olumlamalar.R

object Constants {
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
}