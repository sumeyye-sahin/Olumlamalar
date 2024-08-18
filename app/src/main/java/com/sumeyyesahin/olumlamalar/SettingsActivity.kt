package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivitySettingsBinding
import kotlin.random.Random

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: CategoryTimeAdapter
    private val categoriesAndTimes = mutableListOf<Pair<String, Pair<Int, Int>>>()
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
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActionBar'ı etkinleştirme
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Spinner verilerini ayarla
        val languages = arrayOf("English", "Türkçe")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Dil seçimini yükle
        val currentLanguage = sharedPreferences.getString("language", "en")
        
        adapter = CategoryTimeAdapter(this, categoriesAndTimes, currentLanguage ?: "en", sharedPreferences)


        // loadCategoriesAndTimes(currentLanguage ?: "en")

        binding.selectLanguageBtn.setOnClickListener {
            changeButtonBackgroundColor(it)
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnEditNotifications.setOnClickListener {
            changeButtonBackgroundColor(it)
            val intent = Intent(this, IntroUserNotificationSelectActivity::class.java)
            startActivity(intent)
            finish()
        }

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

        val currentLanguage = sharedPreferences.getString("language", "en")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("language", currentLanguage)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
    override fun onResume() {
        super.onResume()
        val currentLanguage = sharedPreferences.getString("language", "en")

        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        // Ana sayfaya geri dön
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()  // Bu satır isteğe bağlı; ana aktiviteyi başlattıktan sonra mevcut aktiviteyi bitirir
        return true
    }
}