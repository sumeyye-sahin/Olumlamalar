package com.sumeyyesahin.olumlamalar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kullanıcının uygulamayı ilk kez açıp açmadığını kontrol et
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val introSeen = sharedPreferences.getBoolean("intro_seen", false)
        val language = getUserLanguage(this)
        setUserLanguage(this, language)
        if (!introSeen) {
            // Kullanıcı tanıtım sayfasını görmemiş, tanıtım aktivitesine git
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DBHelper(this)
        dbHelper.readableDatabase // Bu satır, veritabanının oluşturulmasını ve JSON verilerinin yüklenmesini sağlar.


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

    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", null)
        val deviceLanguage = Locale.getDefault().language
        return when {
            language != null -> language
            deviceLanguage == "tr" -> "tr"
            deviceLanguage == "en" -> "en"
            else -> "en"
        }
    }

    fun setUserLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language).apply()
    }
}
