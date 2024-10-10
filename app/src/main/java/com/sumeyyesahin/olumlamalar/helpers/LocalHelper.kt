package com.sumeyyesahin.olumlamalar.helpers

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Button
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, language: String): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            updateResourcesLegacy(context, language)
        }
    }
    fun clickedButton(button: Button){
        button.alpha = 0.5f
        button.postDelayed({
            button.alpha = 1f
        }, 300)
    }

    fun clickedButton(view: View){
        view.alpha = 0.5f
        view.postDelayed({
            view.alpha = 1f
        }, 300)
    }
    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

}
