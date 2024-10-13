package com.sumeyyesahin.olumlamalar.viewmodel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.view.*
import com.sumeyyesahin.olumlamalar.worker.NotificationWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _language = MutableLiveData<String>()
    val language: LiveData<String> get() = _language

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> get() = _currentIndex

    val flowerImages = arrayOf(
        R.drawable.mainflower,
        R.drawable.mainflowerlavanta,
        R.drawable.mainflowerpink,
        R.drawable.mainflowerturuncu,
        R.drawable.mainflowerturkuaz,
        R.drawable.mainfloweryellow,
        R.drawable.mainflowerkirmizi
    )

    init {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        _language.value = sharedPreferences.getString("language", "en") ?: "en"
    }

    fun initializeActivity(activity: MainActivity) {
        setLocale(_language.value!!)
        createNotificationChannel(activity)
        requestPermissions(activity)
        scheduleNotifications(_language.value!!)
    }

    fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = getApplication<Application>().resources.configuration
        config.setLocale(locale)
        getApplication<Application>().resources.updateConfiguration(config, getApplication<Application>().resources.displayMetrics)
    }

    fun requestPermissions(activity: MainActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(activity.packageName)) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    .setData(Uri.parse("package:${activity.packageName}"))
                activity.startActivity(intent)
            }
        }
    }

    fun checkInitialViews(activity: MainActivity) {
        val sharedPreferences = activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean("first_description_seen", false)) {
            activity.startActivity(Intent(activity, FirstDescriptionPage::class.java))
            activity.finish()
        } else if (!sharedPreferences.getBoolean("intro_seen", false)) {
            activity.startActivity(Intent(activity, IntroActivity::class.java))
            activity.finish()
        }
    }

    fun isBreathingIntroSeen(activity: MainActivity): Boolean {
        val sharedPreferences = activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("breathing_intro_seen", false)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("CHANNEL_ID", "Random Notification", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Channel for random category notifications"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotifications(language: String) {
        val inputData = Data.Builder().putString("language", language).build()
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(inputData)
            .build()
        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork("DailyNotificationWork", ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    fun updateLanguage(language: String) {
        _language.value = language
        setLocale(language)
        WorkManager.getInstance(getApplication()).cancelUniqueWork("DailyNotificationWork")
        scheduleNotifications(language)
    }

    fun updateFlowerIndex(index: Int = 0) {
        _currentIndex.value = if (index >= 0) index else (_currentIndex.value?.plus(1) ?: 0) % flowerImages.size
    }

    fun onResume(context: Context) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"
        if (language != _language.value) {
            updateLanguage(language)
        }
    }
}
