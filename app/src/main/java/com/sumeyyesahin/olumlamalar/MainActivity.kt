package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentLanguage: String = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        currentLanguage = intent.getStringExtra("language") ?: sharedPreferences.getString("language", "en") ?: "en"
        setLocale(currentLanguage)

        // Activite yeniden başlatma kontrolü
        val savedInstanceStateLanguage = savedInstanceState?.getString("language")
        if (savedInstanceStateLanguage != null && savedInstanceStateLanguage != currentLanguage) {
            currentLanguage = savedInstanceStateLanguage
            setLocale(currentLanguage)
            recreate()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val introSeen = sharedPreferences.getBoolean("intro_seen", false)
        val notificationSetupDone = sharedPreferences.getBoolean("notification_setup_done", false)

        if (!introSeen) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        if (!notificationSetupDone) {
            val intent = Intent(this, IntroUserNotificationSelectActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val dbHelper = DBHelper(this)
        dbHelper.readableDatabase

        binding.buttonnefes.setOnClickListener {
            val breathingIntroSeen = sharedPreferences.getBoolean("breathing_intro_seen", false)

            if (breathingIntroSeen) {
                val intent = Intent(this, NefesActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, BreathingIntroActivity::class.java)
                startActivity(intent)
            }
        }

        binding.buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonkategori.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val language = intent.getStringExtra("language") ?: sharedPreferences.getString("language", "en") ?: "en"
        if (language != currentLanguage) {
            currentLanguage = language
            setLocale(currentLanguage)
            recreate() // Aktiviteyi yeniden başlat
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("language", currentLanguage)
    }
}
