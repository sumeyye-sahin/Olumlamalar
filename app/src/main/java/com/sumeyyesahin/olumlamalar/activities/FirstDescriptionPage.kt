package com.sumeyyesahin.olumlamalar.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityFirstDescriptionPageBinding
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class FirstDescriptionPage : AppCompatActivity() {
    private lateinit var binding: ActivityFirstDescriptionPageBinding
    private val softColors = arrayOf(
        Color.parseColor("#FFB3BA"),  // Light Pink
        Color.parseColor("#FFDFBA"),  // Light Peach
        Color.parseColor("#FFFFBA"),  // Light Yellow
        Color.parseColor("#BAFFC9"),  // Light Mint Green
        Color.parseColor("#BAE1FF"),  // Light Sky Blue
        Color.parseColor("#D4A5A5"),  // Light Coral
        Color.parseColor("#C1C1E1"),  // Light Lavender
        Color.parseColor("#A7BED3")   // Soft Blue-Gray
    )

    private val originalButtonColors = mutableMapOf<View, Int>()

    override fun attachBaseContext(newBase: Context) {
        val languageCode = Locale.getDefault().language
        val context = LocaleHelper.setLocale(newBase, languageCode)
        super.attachBaseContext(context)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstDescriptionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyLocale()

        setContentView(R.layout.activity_first_description_page)

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.setOnClickListener {

            changeButtonBackgroundColor(it)

            editor.putBoolean("first_description_seen", true)

            editor.apply()


            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        applySystemLocale()
    }

    private fun applySystemLocale() {
        val languageCode = Locale.getDefault().language
        setLocale(languageCode)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeButtonBackgroundColor(button: View) {
        val randomColor = getRandomSoftColor()
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
        }
    }

    private fun getRandomSoftColor(): Int {
        val randomIndex = Random.nextInt(softColors.size)
        return softColors[randomIndex]
    }


    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("first_description_seen", true) // Kullanıcı `FirstDescriptionPage`'i gördü
        editor.apply()

        finish()
    }
    private fun applyLocale() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("language", null)

        if (selectedLanguage != null) {
            setLocale(selectedLanguage)
        } else {
            val systemLanguage = Locale.getDefault().language
            setLocale(systemLanguage)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

}
