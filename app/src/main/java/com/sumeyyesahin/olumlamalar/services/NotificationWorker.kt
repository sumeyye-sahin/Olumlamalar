package com.sumeyyesahin.olumlamalar.services

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
            // Get the random context or category from the database
            createNotificationChannel(applicationContext)

            val dbHelper = DBHelper(applicationContext)
            val language = inputData.getString("language") ?: "en" // Use the language from inputData or default to "en"
            val randomCategory = dbHelper.getRandomCategory(language)
            val affirmation = dbHelper.getRandomAffirmationByCategory(randomCategory, language)

            // Create and show the notification with the random category
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = createNotification(applicationContext, "Reminder", affirmation)
            notificationManager.notify(System.currentTimeMillis().toInt(), notification)

            // Schedule the next notification
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

/*
    companion object {
        const val CHANNEL_ID = "daily_notification_channel"
        const val CHANNEL_NAME = "Daily Notification"
    }

    override fun doWork(): Result {
        // Kullanıcının dil tercihini al
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"

        // Rastgele bir kategori ve olumlama seç
        val dbHelper = DBHelper(context)
        val randomCategory = dbHelper.getRandomCategory(language)
        val affirmation = dbHelper.getRandomAffirmationByCategory(randomCategory, language)

        Log.d("NotificationWorker", "Sending notification for $randomCategory")


        // Rastgele seçilen olumlamayı bildirim olarak gönder
        sendNotification(context, affirmation)
// Yeni bir iş planla (bir sonraki gün için)
        scheduleNextNotification()
        // İşin başarılı olduğunu belirt
        return Result.success()
    }
    private fun scheduleNextNotification() {
        // Bir sonraki işin 24 saat sonra çalışması için planla
        val nextWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(1, TimeUnit.DAYS) // Bir gün sonra çalışacak
            .build()

        WorkManager.getInstance(applicationContext).enqueue(nextWorkRequest)
    }
    private fun sendNotification(context: Context, affirmation: String) {
        // Bildirim kanalını oluştur
        createNotificationChannel(context)
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"
        val localizedContext = LocaleHelper.setLocale(context, language)
        val title = localizedContext.getString(R.string.note_to_myself)
        // Bildirime tıklandığında açılacak olan Activity'yi belirle
        val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("affirmation_message", affirmation)
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Bildirim stilini özelleştirin
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.favori_icon) // Bildirim ikonu
            .setContentTitle(title) // Bildirim başlığı
            .setContentText(affirmation) // Bildirim metni
            .setAutoCancel(true) // Bildirime tıklayınca otomatik olarak silinir
            .setSound(defaultSoundUri) // Bildirim sesi
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Bildirim önceliği
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Varsayılan bildirim ayarları
            .setContentIntent(pendingIntent) // Bildirime tıklanınca açılacak içerik
            .setStyle(NotificationCompat.BigTextStyle()) // Bildirim stilini özelleştir

        // Bildirimi göster
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Olumlama bildirimleri için kullanılır"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}*/
    /*

    companion object {
        const val CHANNEL_ID = "daily_notification_channel"
        const val CHANNEL_NAME = "Daily Notification"
    }

    override fun doWork(): Result {
        // Bildirim kategorisi ve diğer bilgileri al
        val category = inputData.getString("category") ?: return Result.failure()
        val hour = inputData.getInt("hour", -1)
        val minute = inputData.getInt("minute", -1)
        if (hour == -1 || minute == -1) return Result.failure()

        Log.d("NotificationWorker", "Sending notification for $category at $hour:$minute")

        sendNotification(context, category)

        // İşin başarılı olduğunu belirt
        return Result.success()
    }

    private fun sendNotification(context: Context, category: String) {
        // SharedPreferences'den dili al
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"

        val localizedContext = LocaleHelper.setLocale(context, language)
        val title = localizedContext.getString(R.string.note_to_myself)
        val localizedCategory = LocaleHelper.getLocalizedCategoryName(localizedContext, category, language)

        val dbHelper = DBHelper(context)
        val affirmation = dbHelper.getRandomAffirmationByCategory(localizedCategory, language)

        createNotificationChannel(context)
        val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("affirmation_message", affirmation)
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Bildirim stilini özelleştirin
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.favori_icon) // Açık renkli bir ikon seçin
            .setContentTitle(title)
            .setContentText(affirmation)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }


    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Olumlama bildirimleri için kullanılır"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}*/








/*package com.sumeyyesahin.olumlamalar
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sumeyyesahin.olumlamalar.utils.Utils.loadOlumlamalar

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {



    override fun doWork(): Result {
        // Olumlamaları yükle
        val olumlamalar = loadOlumlamalar(applicationContext)

        // Kullanıcının dil tercihini al
        val sharedPreferences = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val preferredLanguage = sharedPreferences.getString("language", "en") ?: "en"

        // Dil tercihiyle uyumlu olumlamaları filtrele
        val filteredOlumlamalar = olumlamalar.filter { it.language == preferredLanguage }

        // Rastgele bir olumlama seç
        if (filteredOlumlamalar.isNotEmpty()) {
            val randomOlumlama = filteredOlumlamalar.random()
            sendNotification(randomOlumlama.affirmation)
        } else {
            sendNotification("Olumlama bulunamadı!")
        }

        return Result.success()
    }

    private fun sendNotification(content: String) {
        val channelId = "affirmation_channel"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Bildirim Kanalını Oluştur (Android O ve üzeri için)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Olumlama Bildirimi", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Bildirimi oluştur ve gönder
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.favori_icon)
            .setContentTitle(R.string.note_to_myself.toString()) // burada hataa çıkabilir
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}*/
