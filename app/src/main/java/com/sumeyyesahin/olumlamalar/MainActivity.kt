package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentLanguage: String = "en"
    private var currentIndex = 0
    private val flowerImages = arrayOf(
        R.drawable.mainflowerlavanta,
        R.drawable.mainflowerpink,
        R.drawable.mainflowerturuncu,
        R.drawable.mainflowerturkuaz,
        R.drawable.mainfloweryellow,
        R.drawable.mainflowerkirmizi,
        R.drawable.mainflower // Son resim siyah olacak
    )

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
    private var batteryOptimizationDialog: AlertDialog? = null

    @RequiresApi(Build.VERSION_CODES.N)
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

        if (!introSeen) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        checkBatteryOptimization()

        binding.imageView2.setOnClickListener {
            // Resmi değiştir
            binding.imageView2.setImageResource(flowerImages[currentIndex])
            // Sıradaki indexe geç
            currentIndex++
            // Resimlerin sonuna ulaşıldığında en başa dön
            if (currentIndex >= flowerImages.size) {
                currentIndex = 0
            }
        }

        // Ana aktivite içeriği burada kalacak
        val dbHelper = DBHelper(this)
        dbHelper.readableDatabase

        binding.buttonnefes.setOnClickListener {
            changeButtonBackgroundColor(it)
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
            changeButtonBackgroundColor(it)
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonkategori.setOnClickListener {
            changeButtonBackgroundColor(it)
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("language", currentLanguage)
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

    private fun checkBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(Context.POWER_SERVICE) as android.os.PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                Handler(Looper.getMainLooper()).post {
                    if (!isFinishing && !isDestroyed) {
                        batteryOptimizationDialog = AlertDialog.Builder(this)
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
                            .create()

                        batteryOptimizationDialog?.show()
                    }
                }
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

    override fun onDestroy() {
        super.onDestroy()
        batteryOptimizationDialog?.dismiss()
    }
}
