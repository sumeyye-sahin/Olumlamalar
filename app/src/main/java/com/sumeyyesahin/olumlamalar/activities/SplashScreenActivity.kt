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
        setContentView(binding.root) // Sadece bunu kullanın

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        applyLocale()

        // Splash ekranı layout'unu ayarla
        setContentView(R.layout.activity_splash_screen)


        binding.lottieAnimationView.setSpeed(1.5f) // Animasyonu 1.5 kat hızlandır

        // Lottie animasyonunu başlat
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

        // Eğer bir dil seçilmişse, o dili uygula. Aksi halde sistem dilini kullan.
        if (selectedLanguage != null) {
            setLocale(selectedLanguage)
        } else {
            // Sistem dilini kullan
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