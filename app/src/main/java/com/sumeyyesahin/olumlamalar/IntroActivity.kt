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
    // Soft (Pastel) Renkler Dizisi
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

    // Eski renkleri saklamak için bir map
    private val originalButtonColors = mutableMapOf<View, Int>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // ActionBar'ı etkinleştirme
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        spinnerLanguage = findViewById(R.id.spinnerLanguage)
        val btnContinue = findViewById<Button>(R.id.btnContinue)



        // Varsayılan sistem dilini ayarla
        val defaultLanguage = Locale.getDefault().language
       // setLocale(defaultLanguage)
        // introya kaç kere giriş yapıldığını kontrol et
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)

        val introVisitCount = sharedPreferences.getInt("intro_visit_count", 0)
        if (introVisitCount == 0) {
            setLocale(defaultLanguage)
        }
        // Spinner'ı güncelle
        val languages = resources.getStringArray(R.array.languagesSelect)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter

        // Varsayılan dilin Spinner'da seçili olmasını sağla
        spinnerLanguage.setSelection(0)


        btnContinue.setOnClickListener {
            changeButtonBackgroundColor(it)
            val selectedPosition = spinnerLanguage.selectedItemPosition
            if (selectedPosition == 0) {
                // Kullanıcı dil seçmedi, uyarı göster
                Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
            } else {
                // introya giriş sayısını artır
                val newVisitCount = introVisitCount + 1
                sharedPreferences.edit().putInt("intro_visit_count", newVisitCount).apply()

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

                // intro_seen değerini güncelle
                sharedPreferences.edit().putBoolean("intro_seen", true).apply()

                val intent = if (newVisitCount == 1) {
                    Intent(this, IntroUserNotificationSelectActivity::class.java)
                } else {
                    Intent(this, MainActivity::class.java)
                }
                intent.putExtra("language", selectedLanguage)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        // Ana sayfaya geri dön
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()  // Bu satır isteğe bağlı; ana aktiviteyi başlattıktan sonra mevcut aktiviteyi bitirir
        return true
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeButtonBackgroundColor(button: View) {
        // Rastgele bir soft renk seç
        val randomColor = getRandomSoftColor()

        // Button'un arka planı olarak kullanılan LayerDrawable'ı al
        val background = button.background

        if (background is LayerDrawable) {
            for (i in 0 until background.numberOfLayers) {
                val layer = background.getDrawable(i)
                if (layer is GradientDrawable) {
                    // Eğer button için eski rengi kaydetmediysek, kaydet
                    if (!originalButtonColors.containsKey(button)) {
                        originalButtonColors[button] = (layer.color?.defaultColor ?: Color.TRANSPARENT)
                    }
                    // Rengi değiştir
                    layer.setColor(randomColor)

                    // 1 saniye sonra eski rengi geri yükle
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

    // Rastgele bir soft renk seçen fonksiyon
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
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
