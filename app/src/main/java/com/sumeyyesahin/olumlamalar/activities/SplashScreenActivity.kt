package com.sumeyyesahin.olumlamalar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivitySplashScreenBinding
import java.util.Locale

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_SCREEN_DURATION: Long = 2500 // 2 saniye
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        applyLocale()

        setContentView(R.layout.activity_splash_screen)


        binding.lottieAnimationView.setSpeed(1.5f) // Animasyonu 1.5 kat hızlandırır

        binding.lottieAnimationView.playAnimation()

        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DURATION)
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