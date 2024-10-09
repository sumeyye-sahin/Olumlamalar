package com.sumeyyesahin.olumlamalar.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import java.util.Locale
import java.util.concurrent.TimeUnit
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

        val isFirstDescriptionSeen = sharedPreferences.getBoolean("first_description_seen", false) // "first_description_seen" anahtarını kullan
        val isIntroSeen = sharedPreferences.getBoolean("intro_seen", false) // "intro_seen" anahtarını kullan

        if (!isFirstDescriptionSeen) {
            // İlk defa açılıyorsa FirstDescriptionPage'i göster
            val intent = Intent(this, FirstDescriptionPage::class.java)
            startActivity(intent)
            finish()
            return
        } else if (!isIntroSeen) {
            // Intro daha önce görülmediyse IntroActivity'i göster
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        requestPermissions()

        // Bildirim kanalını oluştur
        createNotificationChannel(this)

        // Bildirimleri zamanla (saat 9:00 ve 18:00)
        scheduleNotifications(currentLanguage)



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
            val intent = Intent(this, IntroActivity::class.java)
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
        // Bildirimleri zamanla (saat 9:00 ve 18:00)
      //  scheduleNotification(9, 0)  // Sabah 9:00'da bildirim gönder
      //  scheduleNotification(15, 40) // Akşam 18:00'da bildirim gönder

    }


    /*
    private fun scheduleDailyNotification(hour: Int, minute: Int, tag: String) {
        val currentDate = Calendar.getInstance()

        // Belirtilen saat ve dakika için zaman ayarlaması
        val dueDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, hour)
        dueDate.set(Calendar.MINUTE, minute)
        dueDate.set(Calendar.SECOND, 0)

        // Eğer belirtilen saat geçmişse, bir sonraki gün için ayarla
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Gelecekteki işin zamanı
        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

        // Gecikmeli bir iş zamanlama
        val dailyWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(timeDiff, java.util.concurrent.TimeUnit.MILLISECONDS)
            .addTag(tag) // İşleri birbirinden ayırmak için her bildirim için farklı bir tag kullanıyoruz
            .build()

/*
        // PeriodicWorkRequest oluşturma, iş 24 saatlik periyotla tekrar edecek
        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS) // İlk bildirim için başlangıç gecikmesi
            .addTag(tag) // İşleri birbirinden ayırmak için her bildirim için farklı bir tag kullanıyoruz
            .build()
*/
        // Aynı etiketle olan işleri iptal et ve yeni işi sıraya al
        val workManager = WorkManager.getInstance(this)
        workManager.cancelAllWorkByTag(tag) // Yalnızca aynı etiketli işleri iptal et
        workManager.enqueue(dailyWorkRequest) // Yeni işi sıraya al
    }
   /* private fun scheduleDailyNotification(hour: Int, minute: Int) {
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
    }*/
*/
  /*  private fun scheduleNotification(hour: Int, minute: Int) {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            hour * 60 + minute,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }*/
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
/*
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
*/
private fun requestPermissions() {
    // Request notification permission for Android 13 and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }

    // Request battery optimization exclusion for Android 6.0 (Marshmallow) and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val packageName = packageName
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                .setData(Uri.parse("package:$packageName"))
            startActivity(intent)
        }
    }
}
    // Function to create the notification channel
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("CHANNEL_ID", "Random Notification", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Channel for random category notifications"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Function to schedule notifications
    private fun scheduleNotifications(language: String) {
        val inputData = Data.Builder()
            .putString("language", language)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyNotificationWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    private fun updateNotificationsWithNewLanguage(language: String) { //bunu en son koydum olmazsa sil
        // Cancel any existing notification work
        WorkManager.getInstance(this).cancelUniqueWork("DailyNotificationWork")

        // Schedule new notifications with the updated language
        scheduleNotifications(language)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val language = intent.getStringExtra("language") ?: sharedPreferences.getString("language", "en") ?: "en"
        if (language != currentLanguage) {
            currentLanguage = language
            setLocale(currentLanguage)
            updateNotificationsWithNewLanguage(currentLanguage) //bunu en son koydum olmazsa sil
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
