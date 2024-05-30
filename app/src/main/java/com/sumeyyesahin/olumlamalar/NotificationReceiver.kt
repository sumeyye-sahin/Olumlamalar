package com.sumeyyesahin.olumlamalar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "affirmation_notification_channel"
        const val CHANNEL_NAME = "Olumlama Bildirim Kanalı"
        const val TRANSIENT_CHANNEL_ID = "affirmation_transient_notification_channel"
        const val TRANSIENT_CHANNEL_NAME = "Olumlama Geçici Bildirim Kanalı"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"
        val originalCategory = intent.getStringExtra("category") ?: return
        val localizedCategory = getLocalizedCategoryName(context, originalCategory, language)

        val dbHelper = DBHelper(context)
        val affirmation = dbHelper.getRandomAffirmationByCategory(localizedCategory, language)
        sendPersistentNotification(context, localizedCategory, affirmation)
        sendTransientNotification(context, localizedCategory, affirmation)
    }

    private fun sendPersistentNotification(context: Context, title: String, message: String) {
        createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME)

        val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("affirmation_message", message) // Mesajı Intent'e ekleyin
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.favori_icon)
            .setContentTitle(title) // Basit başlık
            .setContentText(message) // Basit mesaj
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
           // .setCustomContentView(getCompactRemoteView(context, title, message))
            .setCustomBigContentView(getExpandedRemoteView(context, title, message))

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(1, notificationBuilder.build())
            } else {
                // İzin yoksa burada ne yapılması gerektiğini belirtebilirsiniz
                Toast.makeText(context, "Bildirim izni verilmemiş.", Toast.LENGTH_SHORT).show()
            }
        } else {
            notificationManager.notify(1, notificationBuilder.build())
        }
    }

    private fun sendTransientNotification(context: Context, title: String, message: String) {
        createNotificationChannel(context, TRANSIENT_CHANNEL_ID, TRANSIENT_CHANNEL_NAME)

        val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("affirmation_message", message) // Mesajı Intent'e ekleyin
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, TRANSIENT_CHANNEL_ID)
            .setSmallIcon(R.drawable.favori_icon)
            .setContentTitle(title) // Basit başlık
            .setContentText(message) // Basit mesaj
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(getExpandedRemoteView(context, title, message))
            .setCustomBigContentView(getExpandedRemoteView(context, title, message))
            .setTimeoutAfter(8000) // Bildirim ekranın üst kısmında 8 saniye boyunca görünecek



        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(2, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context, channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Olumlama bildirimleri için kullanılır"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getCompactRemoteView(context: Context, title: String, message: String): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        return remoteView
    }

    private fun getExpandedRemoteView(context: Context, title: String, message: String): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.image, R.drawable.favori_icon) // Resmi ekleme
        return remoteView
    }

    private fun getLocalizedCategoryName(context: Context, category: String, language: String): String {
        val resources = when (language) {
            "tr" -> LocaleHelper.setLocale(context, "tr").resources
            "en" -> LocaleHelper.setLocale(context, "en").resources
            else -> LocaleHelper.setLocale(context, "en").resources
        }
        return when (language) {
            "tr" -> when (category) {
                "General Affirmations" -> resources.getString(R.string.general_affirmations)
                "Body Affirmations" -> resources.getString(R.string.body_affirmations)
                "Faith Affirmations" -> resources.getString(R.string.faith_affirmations)
                "Tough Days Affirmations" -> resources.getString(R.string.bad_days_affirmations)
                "Love Affirmations" -> resources.getString(R.string.love_affirmations)
                "Self-Worth Affirmations" -> resources.getString(R.string.self_value_affirmations)
                "Stress and Anxiety Affirmations" -> resources.getString(R.string.stress_affirmations)
                "Positive Thought Affirmations" -> resources.getString(R.string.positive_thought_affirmations)
                "Success Affirmations" -> resources.getString(R.string.success_affirmations)
                "Personal Development Affirmations" -> resources.getString(R.string.personal_development_affirmations)
                "Time Management Affirmations" -> resources.getString(R.string.time_management_affirmations)
                "Relationship Affirmations" -> resources.getString(R.string.relationship_affirmations)
                "Prayer and Request" -> resources.getString(R.string.prayer_affirmations)
                else -> category
            }
            "en" -> when (category) {
                "Genel Olumlamalar" -> resources.getString(R.string.general_affirmations)
                "Beden Olumlamaları" -> resources.getString(R.string.body_affirmations)
                "İnanç Olumlamaları" -> resources.getString(R.string.faith_affirmations)
                "Zor Günler Olumlamaları" -> resources.getString(R.string.bad_days_affirmations)
                "Sevgi ve Aşk Olumlamaları" -> resources.getString(R.string.love_affirmations)
                "Öz Değer Olumlamaları" -> resources.getString(R.string.self_value_affirmations)
                "Stres ve Kaygı Olumlamaları" -> resources.getString(R.string.stress_affirmations)
                "Pozitif Düşünce Olumlamaları" -> resources.getString(R.string.positive_thought_affirmations)
                "Başarı Olumlamaları" -> resources.getString(R.string.success_affirmations)
                "Kişisel Gelişim Olumlamaları" -> resources.getString(R.string.personal_development_affirmations)
                "Zaman Yönetimi Olumlamaları" -> resources.getString(R.string.time_management_affirmations)
                "İlişki Olumlamaları" -> resources.getString(R.string.relationship_affirmations)
                "Dua ve İstek" -> resources.getString(R.string.prayer_affirmations)
                else -> category
            }
            else -> category
        }
    }
}
