package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class BreathingIntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breathing_intro)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.setOnClickListener {
            // Kullanıcı nefes egzersizi tanıtım sayfasını gördü, SharedPreferences'e kaydet
            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("breathing_intro_seen", true).apply()

            // Nefes egzersizi aktivitesine geç
            val intent = Intent(this, NefesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

