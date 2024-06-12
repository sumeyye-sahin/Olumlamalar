package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        checkBatteryOptimization()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val introSeen = sharedPreferences.getBoolean("intro_seen", false)

        if (!introSeen) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Ana aktivite içeriği burada kalacak
        val dbHelper = DBHelper(this)
        dbHelper.readableDatabase

        binding.buttonnefes.setOnClickListener {
            val breathingIntroSeen = sharedPreferences.getBoolean("breathing_intro_seen", false)

            if (breathingIntroSeen) {
                val intent = Intent(this, NefesActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, BreathingIntroActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonkategori.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("language", currentLanguage)
            startActivity(intent)
            finish()
        }
    }

    private fun checkBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                AlertDialog.Builder(this)
                    .setTitle("Battery Optimization")
                    .setMessage("To ensure notifications are delivered on time, please disable battery optimization for this app.")
                    .setPositiveButton("OK") { dialog, _ ->
                        intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                        intent.data = android.net.Uri.parse("package:$packageName")
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        Toast.makeText(
                            this,
                            "Battery optimization not disabled",
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.dismiss()
                    }
                    .show()
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
