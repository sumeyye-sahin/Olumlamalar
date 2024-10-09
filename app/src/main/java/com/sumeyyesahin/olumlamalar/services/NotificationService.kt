package com.sumeyyesahin.olumlamalar.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.Calendar
/*
class NotificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        setDailyNotifications()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setDailyNotifications() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val serializedData = sharedPreferences.getString("categories_and_times", "")
        if (!serializedData.isNullOrEmpty()) {
            val items = serializedData.split(";")
            items.forEach { item ->
                val parts = item.split(",")
                if (parts.size == 3) {
                    val category = parts[0]
                    val hour = parts[1].toIntOrNull()
                    val minute = parts[2].toIntOrNull()
                    if (hour != null && minute != null) {
                        setDailyNotification(hour, minute, category)
                    } else {
                        Log.e("NotificationService", "Invalid time format for $category: $item")
                    }
                } else {
                    Log.e("NotificationService", "Invalid data format: $item")
                }
            }
        }
    }

    private fun setDailyNotification(hour: Int, minute: Int, category: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("category", category)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, getRequestCode(category, hour, minute), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        Log.d("NotificationService", "Setting alarm for $category at $hour:$minute")
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun getRequestCode(category: String, hour: Int, minute: Int): Int {
        // Benzersiz bir requestCode için kategori, saat ve dakikayı hash ile dönüştürüyoruz
        return "$category,$hour,$minute".hashCode()
    }
}*/
