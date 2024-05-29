package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.*

class IntroActivity : AppCompatActivity() {

    private lateinit var spinnerLanguage: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        // Varsayılan sistem dilini ayarla
        val defaultLanguage = Locale.getDefault().language
        setLocale(defaultLanguage)

        // Spinner'ı güncelle
        val languages = resources.getStringArray(R.array.languagesSelect)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter

        // Varsayılan dilin Spinner'da seçili olmasını sağla
        val defaultPosition = when (defaultLanguage) {
            "tr" -> 1
            "en" -> 2
            else -> 0
        }
        spinnerLanguage.setSelection(defaultPosition)

        //introya birden fazla giriş yapılması durumu
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val introSeen = sharedPreferences.getBoolean("intro_deneme", false)

        btnContinue.setOnClickListener {
            val selectedPosition = spinnerLanguage.selectedItemPosition
            if (selectedPosition == 0) {
                // Kullanıcı dil seçmedi, uyarı göster
                Toast.makeText(this, "Lütfen bir dil seçiniz", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("intro_deneme", true).apply()

                // Kullanıcının seçtiği dili alın
                val selectedLanguage = when (selectedPosition) {
                    1 -> "tr"
                    2 -> "en"
                    else -> "en"
                }

                // Dili kaydedin
                sharedPreferences.edit().putString("language", selectedLanguage).apply()

                // Dili ayarlayın
                setLocale(selectedLanguage)

                val intent = if (introSeen) {
                    Intent(this, SettingsActivity::class.java)
                } else {
                    Intent(this, IntroUserNotificationSelectActivity::class.java)
                }
                intent.putExtra("language", selectedLanguage)
                startActivity(intent)
                finish()
            }
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
