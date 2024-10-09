package com.sumeyyesahin.olumlamalar.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Cihaz yeniden başlatıldığında tekrarlayan işi planla
         //  scheduleDailyNotifications(context)
        }
    }

    private fun scheduleDailyNotifications(context: Context) {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .addTag("dailyNotification")
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag("dailyNotification")
        workManager.enqueue(periodicWorkRequest)
    }
}



