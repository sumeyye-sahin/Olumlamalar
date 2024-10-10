package com.sumeyyesahin.olumlamalar.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.activities.ShowAffirmationActivity
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.helpers.LocaleHelper
import java.util.concurrent.TimeUnit

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

        override fun doWork(): Result {

            createNotificationChannel(applicationContext)

            val dbHelper = DBHelper(applicationContext)
            val language = inputData.getString("language") ?: "en"
            val randomCategory = dbHelper.getRandomCategory(language)
            val affirmation = dbHelper.getRandomAffirmationByCategory(randomCategory, language)

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = createNotification(applicationContext, "Reminder", affirmation)
            notificationManager.notify(System.currentTimeMillis().toInt(), notification)

            scheduleNextNotification()

            // Indicate that the work was successful
            return Result.success()
        }

        // Function to create the notification with dynamic content
        private fun createNotification(context: Context, title: String, message: String): Notification {

            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val language = sharedPreferences.getString("language", "en") ?: "en"
            val localizedContext = LocaleHelper.setLocale(context, language)
            val title = localizedContext.getString(R.string.note_to_myself)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            // Bildirime tıklandığında açılacak olan Activity'yi belirle
            val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra("affirmation_message", message)
            }
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val vibrationPattern = longArrayOf(0, 500, 200, 500)
            val builder = NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.favori_icon) // Bildirim ikonu
                .setContentTitle(title) // Bildirim başlığı
                .setContentText(message) // Bildirim metni
                .setAutoCancel(true) // Bildirime tıklayınca otomatik olarak silinir
                .setSound(defaultSoundUri) // Bildirim sesi
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Bildirim önceliği
                .setDefaults(NotificationCompat.DEFAULT_ALL) // Varsayılan bildirim ayarları
                .setContentIntent(pendingIntent) // Bildirime tıklanınca açılacak içerik
                .setStyle(NotificationCompat.BigTextStyle()) // Bildirim stilini özelleştir
                .setVibrate(vibrationPattern) // Bildirim titreşimi
            return builder.build()
        }

        // Schedule the next notification to trigger after 24 hours
        private fun scheduleNextNotification() {
            val nextWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(24, TimeUnit.HOURS) // Set delay for the next day
                .build()

            WorkManager.getInstance(applicationContext).enqueue(nextWorkRequest)
        }

        // Function to create the notification channel (Android 8.0 and above)
        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "CHANNEL_ID",
                    "Daily Notification",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Channel for daily reminders"
                }
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
