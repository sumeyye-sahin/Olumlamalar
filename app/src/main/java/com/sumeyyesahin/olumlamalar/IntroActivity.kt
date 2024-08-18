package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale
import kotlin.random.Random

class IntroActivity : AppCompatActivity() {

    private lateinit var spinnerLanguage: Spinner
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Locale ayarlarını ilk açılışta uygulamak
        applyLocale()

        setContentView(R.layout.activity_intro)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val introVisitCount = sharedPreferences.getInt("intro_visit_count", 0)

        // İlk açılışta ActionBar'ı gizle, sonraki girişlerde göster
        if (introVisitCount == 0) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }

        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        val languages = resources.getStringArray(R.array.languagesSelect)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter
        spinnerLanguage.setSelection(0)

        btnContinue.setOnClickListener {
            changeButtonBackgroundColor(it)
            val selectedPosition = spinnerLanguage.selectedItemPosition
            if (selectedPosition == 0) {
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            } else {
                val newVisitCount = introVisitCount + 1
                sharedPreferences.edit().putInt("intro_visit_count", newVisitCount).apply()

                val selectedLanguage = when (selectedPosition) {
                    1 -> "tr"
                    2 -> "en"
                    else -> "en"
                }

                // Seçilen dili hemen uygula
                setLocale(selectedLanguage)

                // Seçilen dili kaydet
                sharedPreferences.edit().putString("language", selectedLanguage).apply()
                sharedPreferences.edit().putBoolean("intro_seen", true).apply()

                // ActionBar'ı göster
                supportActionBar?.show()

                val intent = if (newVisitCount == 1) {
                    Intent(this, MainActivity::class.java)
                } else {
                    Intent(this, MainActivity::class.java)
                }
                intent.putExtra("language", selectedLanguage)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun applyLocale() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val languageCode = sharedPreferences.getString("language", Locale.getDefault().language) ?: Locale.getDefault().language
        setLocale(languageCode)
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Ayarlar sayfasına geri dön
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()  // Bu satır isteğe bağlı; ana aktiviteyi başlattıktan sonra mevcut aktiviteyi bitirir
        return true
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
            Log.e("MainActivity", "GradientDrawable bulunamadı.")
        } else {
            Log.e("MainActivity", "LayerDrawable değil: ${background?.javaClass?.name}")
        }
    }

    private fun getRandomSoftColor(): Int {
        val randomIndex = Random.nextInt(softColors.size)
        return softColors[randomIndex]
    }

    override fun onBackPressed() {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
