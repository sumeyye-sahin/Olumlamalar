package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kullanıcının uygulamayı ilk kez açıp açmadığını kontrol et
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val introSeen = sharedPreferences.getBoolean("intro_seen", false)

        if (!introSeen) {
            // Kullanıcı tanıtım sayfasını görmemiş, tanıtım aktivitesine git
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonnefes.setOnClickListener {
            val breathingIntroSeen = sharedPreferences.getBoolean("breathing_intro_seen", false)

            if (breathingIntroSeen) {
                // Kullanıcı nefes egzersizi tanıtımını görmüş, doğrudan nefes egzersizi aktivitesine git
                val intent = Intent(this, NefesActivity::class.java)
                startActivity(intent)
            } else {
                // Kullanıcı nefes egzersizi tanıtımını görmemiş, tanıtım aktivitesine git
                val intent = Intent(this, BreathingIntroActivity::class.java)
                startActivity(intent)
            }
        }

        binding.buttonkategori.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }
}
