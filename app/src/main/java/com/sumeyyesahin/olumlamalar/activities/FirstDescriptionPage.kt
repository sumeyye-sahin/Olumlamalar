package com.sumeyyesahin.olumlamalar.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityFirstDescriptionPageBinding
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class FirstDescriptionPage : AppCompatActivity() {
    private lateinit var binding: ActivityFirstDescriptionPageBinding
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

    override fun attachBaseContext(newBase: Context) {
        // Sistem dilini al ve uygula
        val languageCode = Locale.getDefault().language // Sistem dilini kullan
        val context = LocaleHelper.setLocale(newBase, languageCode)
        super.attachBaseContext(context)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstDescriptionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sistem dilini uygula
        applyLocale()

        // Splash ekranı layout'unu ayarla
        setContentView(R.layout.activity_first_description_page)
        // Bildirimleri butona basıldığında zamanla
        //scheduleDailyNotification(5, 15) // Saat 14:24'te
       //scheduleDailyNotification(5, 18) // Saat 14:27'de
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // ActionBar'ı etkinleştirmeme
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Buton tıklama işlemleri
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.setOnClickListener {
            // Buton rengini değiştir
            changeButtonBackgroundColor(it)


            // Kullanıcı FirstDescriptionPage'i gördü
            editor.putBoolean("first_description_seen", true)

            editor.apply()

            // IntroActivity'e geçiş yap
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // onResume'da sistem diline göre güncelleme yap
        applySystemLocale()
    }

    private fun applySystemLocale() {
        // Sistem dilini al ve uygula
        val languageCode = Locale.getDefault().language
        setLocale(languageCode)
    }

/*
    private fun scheduleDailyNotification(hour: Int, minute: Int) {
        val currentDate = Calendar.getInstance()

        // Belirtilen saat ve dakika için zaman ayarlaması
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, hour)
        dueDate.set(Calendar.MINUTE, minute)
        dueDate.set(Calendar.SECOND, 0)

        // Eğer belirtilen saat geçmişse, bir sonraki gün için ayarla
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        // Gelecekteki işin zamanı
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        // Gecikmeli ve düzenli bir iş zamanlama
        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
            .addTag("dailyNotification")
            .build()

        WorkManager.getInstance(this).enqueue(periodicWorkRequest)
    }
*/

    private fun scheduleDailyNotification(hour: Int, minute: Int) {
        val currentDate = Calendar.getInstance()

        // Belirtilen saat ve dakika için zaman ayarlaması
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, hour)
        dueDate.set(Calendar.MINUTE, minute)
        dueDate.set(Calendar.SECOND, 0)

        // Eğer belirtilen saat geçmişse, bir sonraki gün için ayarla
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }

        // Gelecekteki işin zamanı
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        // Gecikmeli bir iş zamanlama
        val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDiff, java.util.concurrent.TimeUnit.MILLISECONDS)
            .addTag("dailyNotification")
            .build()

        WorkManager.getInstance(this).enqueue(dailyWorkRequest)
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
        }
    }

    private fun getRandomSoftColor(): Int {
        val randomIndex = Random.nextInt(softColors.size)
        return softColors[randomIndex]
    }


    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("first_description_seen", true) // Kullanıcı `FirstDescriptionPage`'i gördü
        editor.apply()

        // `IntroActivity`'ye yönlendirme
       // val intent = Intent(this, IntroActivity::class.java)
        //startActivity(intent)
        finish()
    }
    private fun applyLocale() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("language", null)

        // Eğer bir dil seçilmişse, o dili uygula. Aksi halde sistem dilini kullan.
        if (selectedLanguage != null) {
            setLocale(selectedLanguage)
        } else {
            // Sistem dilini kullan
            val systemLanguage = Locale.getDefault().language
            setLocale(systemLanguage)
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
